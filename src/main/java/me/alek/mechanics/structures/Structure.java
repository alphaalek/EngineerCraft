package me.alek.mechanics.structures;

import me.alek.EngineerCraft;
import me.alek.mechanics.transporter.TransferLocation;
import me.alek.utils.FacingUtils;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public class Structure implements IStructure {

    private boolean isTransporter;
    private boolean isMechanic;

    private Sign sign = new Sign(new Vector(0, 0, 0), BlockFace.NORTH);
    private List<TransferLocation> inputLocations = new ArrayList<>();
    private List<TransferLocation> outputLocations = new ArrayList<>();

    private final HashMap<Vector, Pillar> pillars = new HashMap<>();
    private final HashMap<Integer, Set<Integer>> knownOffsets = new HashMap<>();

    @Override
    public HashMap<Vector, Pillar> getPillars() {
        return pillars;
    }

    @Override
    public void load(@NotNull Location location, BlockFace direction, Handshake doneLoading) {
        new StructureLoader(this, location.getBlock().getLocation(), direction, doneLoading).load();
    }

    public void set(int x, int y, Pillar pillar) {
        if (!knownOffsets.containsKey(x)) {
            knownOffsets.put(x, new HashSet<>());
        }
        if (knownOffsets.get(x).contains(y)) {
            return;
        }
        knownOffsets.get(x).add(y);
        pillars.put(new Vector(x, 0, y), pillar);
    }

    private boolean compareVector(@NotNull Vector vector1, @NotNull Vector vector2) {
        return vector1.getBlockX() == vector2.getBlockX() && vector1.getBlockZ() == vector2.getBlockZ() && vector1.getBlockY() == vector2.getBlockY();
    }

    public void combineStructure(@NotNull IStructure structure) {

        synchronized (pillars) {
            synchronized (structure.getPillars()) {

                for (Map.Entry<Vector, Pillar> entry : structure.getPillars().entrySet()) {
                    boolean hasSameVector = false;
                    for (Map.Entry<Vector, Pillar> samePillarEntry : pillars.entrySet()) {
                        if (!compareVector(entry.getKey(), samePillarEntry.getKey())) {
                            continue;
                        }
                        hasSameVector = true;
                        for (Map.Entry<Integer, Pillar.BlockData> blockEntry : entry.getValue().getBlocks().entrySet()) {
                            samePillarEntry.getValue().addAtIndex(blockEntry.getKey(), blockEntry.getValue());
                        }

                        for (Map.Entry<Integer, Pillar.BlockData> callbackBlockEntry : entry.getValue().getCallbackBlocks().entrySet()) {
                            samePillarEntry.getValue().addAtIndex(callbackBlockEntry.getKey(), callbackBlockEntry.getValue());
                        }
                    }
                    if (!hasSameVector) {
                        pillars.putIfAbsent(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        if (isMechanic) {
            if (structure.isMechanic()) {
                sign = structure.getSign();
                changePillars();
            }
        }
    }


    public static class StructureLoader {

        private final Location location;
        private final Structure structure;
        private final Handshake doneLoading;

        private final Function<Vector, Vector> vectorRotation;
        private final Function<BlockFace, BlockFace> blockFaceRotation;

        private final HashMap<Vector, Map<Integer, Pillar.BlockData>> callbackBlocks = new HashMap<>();

        public StructureLoader(Structure structure, final Location location, BlockFace direction, Handshake doneLoading) {
            this.location = location;
            this.structure = structure;
            this.doneLoading = doneLoading;

            this.vectorRotation = FacingUtils.getRotateVectorFunction(direction);
            this.blockFaceRotation = FacingUtils.getRotateBlockFaceFunction(BlockFace.SOUTH, direction);
        }

        private Location getLocation(Vector vector) {
            return FacingUtils.reverseVectorForLocation(location, vector, vectorRotation);
        }

        public void putCallback(Vector vector, Map<Integer, Pillar.BlockData> blocks) {
            callbackBlocks.put(vector, blocks);
        }

        public void load() {
            for (Map.Entry<Vector, Pillar> entry : structure.getPillars().entrySet()) {
                final Map<Integer, Pillar.BlockData> callbackBlocks = entry.getValue().loadBlocks(blockFaceRotation, getLocation(entry.getKey()));
                if (!callbackBlocks.isEmpty()) {
                    this.callbackBlocks.put(entry.getKey(), callbackBlocks);
                }
            }
            new BukkitRunnable() { // synchronously

                @Override
                public void run() {
                    for (Map.Entry<Vector, Map<Integer, Pillar.BlockData>> entry : callbackBlocks.entrySet()) {
                        Pillar.loadBlocks(blockFaceRotation, getLocation(entry.getKey()), entry.getValue());
                    }
                    doneLoading.onResponse();
                }
            }.runTaskLater(EngineerCraft.getInstance(), 10L);
        }
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(int x, int y, int z, BlockFace blockFace, boolean changePillars) {
        sign = new Sign(new Vector(x, y, z), blockFace);
        if (changePillars) {
            changePillars();
        }
    }

    public void changePillars() {
        final Vector vector = sign.getVector();
        Vector sameVector = null;

        for (Vector mapVector : getPillars().keySet()) {
            if (compareVector(vector, mapVector)) {
                sameVector = mapVector;
                break;
            }
        }
        if (sameVector != null) {
            getPillars().get(sameVector).clearYOffset().addCallback(vector.getBlockY(), Material.WALL_SIGN, sign.getBlockFace(), org.bukkit.material.Sign::new);
        } else {
            final Pillar signPillar = new Pillar().addCallback(vector.getBlockY(), Material.WALL_SIGN, sign.getBlockFace(), org.bukkit.material.Sign::new);
            set(vector.getBlockX(), vector.getBlockZ(), signPillar);
        }
    }

    public void setTransporter(boolean isTransporter) {
        this.isTransporter = isTransporter;
    }

    public void setMechanic(boolean isMechanic) {
        this.isMechanic = isMechanic;
    }

    public boolean isTransporter() {
        return isTransporter;
    }

    public boolean isMechanic() {
        return isMechanic;
    }

}
