package me.alek.hub;

import me.alek.exceptions.NoSuchProfile;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.structures.Plane;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.tracker.TrackerWrapper;
import me.alek.utils.FacingUtils;
import me.alek.utils.Handshake;
import me.alek.utils.Tuple2;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.function.Function;

public class Hub {

    private final UUID owner;
    private final int id;

    private final Set<UUID> members = new HashSet<>();
    private final Set<Player> onlinePlayers = new HashSet<>();

    private final HashMap<Chunk, Plane> structurePlanes = new HashMap<>();
    private final HashMap<Integer, Tracker<? extends Unit>> trackers = new HashMap<>();

    public Hub(int id, UUID owner) {
        this.owner = owner;
        this.id = id;
        addMember(owner);
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public Set<UUID> getMembers() {
        return members;
    }

    public Set<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void addOnlinePlayer(Player player) {
        onlinePlayers.add(player);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }

    public void removeOnlinePlayer(Player player) {
        onlinePlayers.remove(player);
    }

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, String name, boolean buildStructure) throws NoSuchProfile {
        final UnitProfile<U> profile = UnitLibrary.getProfileByName(name);
        return createUnit(location, direction, profile, buildStructure);
    }

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, int id, boolean buildStructure) throws NoSuchProfile {
        final UnitProfile<U> profile = UnitLibrary.getProfileById(id);
        return createUnit(location, direction, profile, buildStructure);
    }

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, UnitProfile<U> profile, boolean buildStructure) {
        final Handshake doneLoading = new Handshake();
        final Tracker<? extends Unit> tracker;

        if (!trackers.containsKey(profile.getId())) {
            if (profile.isWorker()) {
                tracker = new Tracker<>(this, TrackerWrapper.Wrappers.WORKER_MECHANIC.getWrapper());
            }
            else if (profile.isMechanic()) {
                tracker = new Tracker<>(this, TrackerWrapper.Wrappers.MECHANIC.getWrapper());
            }
            else {
                tracker = new Tracker<>(this, TrackerWrapper.Wrappers.UNIT.getWrapper());
            }
            tracker.setup();
            trackers.put(profile.getId(), tracker);
        } else {
            tracker = trackers.get(profile.getId());
        }

        final Unit unit = UnitFactory.createUnitObject(this, location, direction, profile, tracker, 1, doneLoading);
        if (unit == null) {
            return null;
        }

        if (buildStructure) {
            profile.getStructure().load(location, direction, doneLoading);
        }
        return unit;
    }

    public <U extends Unit> boolean allowBuild(Location location, BlockFace direction, UnitProfile<U> profile) {
        // param 1 = chunk wrapper, param 2 = plane der bliver bygget i den chunk
        final HashMap<Plane, Plane> intersectedChunkWrappers = new HashMap<>();
        final HashMap<Plane, List<Tuple2<Integer, Integer>>> addPoints = new HashMap<>();

        if (!structurePlanes.containsKey(location.getChunk())) {
            structurePlanes.put(location.getChunk(), new Plane());
        }
        final Plane sameChunkWrapper = structurePlanes.get(location.getChunk());
        intersectedChunkWrappers.put(sameChunkWrapper, new Plane());

        final Function<Vector, Vector> rotationVector = FacingUtils.getRotateVectorFunction(direction);
        for (Vector relativeVector : profile.getStructure().getPillars().keySet()) {

            final Vector rotatedVector = rotationVector.apply(relativeVector);
            final Location vectorLocation = location.clone().add(rotatedVector);

            final Plane chunkWrapper;
            if (vectorLocation.getChunk() != location.getChunk()) {
                if (!structurePlanes.containsKey(vectorLocation.getChunk())) {
                    structurePlanes.put(vectorLocation.getChunk(), new Plane());
                }
                chunkWrapper = structurePlanes.get(vectorLocation.getChunk());
                if (!intersectedChunkWrappers.containsKey(chunkWrapper)) {
                    intersectedChunkWrappers.put(chunkWrapper, new Plane());
                }
            } else {
                chunkWrapper = sameChunkWrapper;
            }
            intersectedChunkWrappers.get(chunkWrapper).addPoint(getChunkOffset(vectorLocation));

            if (!addPoints.containsKey(chunkWrapper)) {
                addPoints.put(chunkWrapper, new ArrayList<>());
            }
            final List<Tuple2<Integer, Integer>> addPointsList = addPoints.get(chunkWrapper);
            addPointsList.add(getChunkOffset(vectorLocation));
        }
        for (Map.Entry<Plane, Plane> intersectEntry : intersectedChunkWrappers.entrySet()) {
            if (intersectEntry.getValue().intersect(intersectEntry.getKey())) {
                return false;
            }
        }
        for (Map.Entry<Plane, List<Tuple2<Integer, Integer>>> addPoint : addPoints.entrySet()) {
            addPoint.getKey().addAll(addPoint.getValue());
        }
        return true;
    }

    private Tuple2<Integer, Integer> getChunkOffset(Location location) {
        final int XChunkCoord = location.getBlockX() / 16;
        final int ZChunkCoord = location.getBlockZ() / 16;

        return modifyNegativeOffsets(new Tuple2<>(location.getBlockX() - (XChunkCoord * 16), location.getBlockZ() - (ZChunkCoord * 16)));
    }

    private Tuple2<Integer, Integer> modifyNegativeOffsets(Tuple2<Integer, Integer> offset) {
        if (offset.getParam1() < 0) {
            offset.setParam1(offset.getParam1() + 16);
        }
        if (offset.getParam2() < 0) {
            offset.setParam2(offset.getParam2() + 16);
        }
        return offset;
    }

    public int getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }
}
