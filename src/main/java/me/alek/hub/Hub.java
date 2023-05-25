package me.alek.hub;

import me.alek.exceptions.AlreadyExistingUnit;
import me.alek.exceptions.NoSuchProfile;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.tracker.TrackerWrapper;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Hub {

    private final UUID owner;
    private final int id;

    private final Set<UUID> members = new HashSet<>();
    private final Set<Player> onlinePlayers = new HashSet<>();

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

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, String name, boolean buildStructure) throws NoSuchProfile, AlreadyExistingUnit {
        final UnitProfile<U> profile = UnitLibrary.getProfileByName(name);
        return createUnit(location, direction, profile, buildStructure);
    }

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, int id, boolean buildStructure) throws NoSuchProfile, AlreadyExistingUnit {
        final UnitProfile<U> profile = UnitLibrary.getProfileById(id);
        return createUnit(location, direction, profile, buildStructure);
    }

    public <U extends Unit> Unit createUnit(Location location, BlockFace direction, UnitProfile<U> profile, boolean buildStructure) throws AlreadyExistingUnit {
        for (Tracker<?> unitTracker : trackers.values()) {
            if (unitTracker.hasTrackerAtLocation(location)) {
                throw new AlreadyExistingUnit();
            }
        }
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
        location = location.getBlock().getLocation();

        final Unit unit = UnitFactory.createUnit(this, location, direction, profile, tracker);
        if (buildStructure) {
            unit.getProfile().getStructure().load(location, direction);
        }
        return unit;
    }

    public int getId() {
        return id;
    }

    public UUID getOwner() {
        return owner;
    }
}
