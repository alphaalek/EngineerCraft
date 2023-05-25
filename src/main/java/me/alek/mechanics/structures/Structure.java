package me.alek.mechanics.structures;

import me.alek.EngineerCraft;
import me.alek.utils.FacingUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Structure implements IStructure {

    private final HashMap<Vector, Pillar> pillars = new HashMap<>();
    private final HashMap<Integer, Set<Integer>> knownOffsets = new HashMap<>();

    @Override
    public HashMap<Vector, Pillar> getPillars() {
        return pillars;
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

    @Override
    public void load(Location location, BlockFace direction) {
        new StructureLoader(this, location.getBlock().getLocation(), direction).load();
    }

    public void combineStructure(IStructure structure) {
        synchronized (pillars) {
            for (Map.Entry<Vector, Pillar> entry : structure.getPillars().entrySet()) {
                pillars.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
    }

    public static class StructureLoader {

        private final Location location;
        private final Structure structure;

        private final Function<Vector, Vector> vectorRotation;
        private final Function<BlockFace, BlockFace> blockFaceRotation;

        private HashMap<Vector, Map<Integer, Pillar.BlockData>> callbackBlocks = new HashMap<>();

        public StructureLoader(Structure structure, final Location location, BlockFace direction) {
            this.location = location;
            this.structure = structure;

            this.vectorRotation = FacingUtils.getRotateVectorFunction(direction);
            this.blockFaceRotation = FacingUtils.getRotateBlockFaceFunction(BlockFace.SOUTH, direction);
        }

        private Location getLocation(Vector vector) {
            final Vector reversedVector = vectorRotation.apply(vector);
            return location.getWorld().getBlockAt(location.clone().add(reversedVector)).getLocation();
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
            new BukkitRunnable() {

                @Override
                public void run() {
                    for (Map.Entry<Vector, Map<Integer, Pillar.BlockData>> entry : callbackBlocks.entrySet()) {
                        Pillar.loadBlocks(blockFaceRotation, getLocation(entry.getKey()), entry.getValue());
                    }
                }
            }.runTaskLater(EngineerCraft.getInstance(), 5L);
        }
    }

}
