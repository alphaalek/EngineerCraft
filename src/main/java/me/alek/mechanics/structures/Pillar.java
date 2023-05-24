package me.alek.mechanics.structures;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.material.Directional;
import org.bukkit.material.Lever;
import org.bukkit.material.MaterialData;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Pillar {

    private final Map<Integer, BlockData> blocks = new TreeMap<>();
    private final Map<Integer, BlockData> vulnerableBlocks = new TreeMap<>();
    private int currentYOffset = 0;

    public Map<Integer, BlockData> getVulnerableBlocks() {
        return vulnerableBlocks;
    }

    public Map<Integer, BlockData> getBlocks() {
        return blocks;
    }

    private static Directional cast(Class<?> caster, Block block, Class<?> originClass) {
        Class<?> clazz = originClass;
        while (clazz != null) {

            if (caster.isAssignableFrom(clazz)) {
                return (Directional) block.getState().getData();
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static void forcePhysicsUpdate(Block block) {
        final net.minecraft.server.v1_8_R3.World world = ((CraftWorld)block.getWorld()).getHandle();
        final BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        final net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(block);

        for (EnumDirection enumDirection : EnumDirection.values()) {
            world.applyPhysics(blockPosition.shift(enumDirection), nmsBlock);
        }
     }

     private static byte getLeverBlockFaceData(BlockFace blockFace) {
        switch (blockFace) {
            case EAST:
                return 1;
            case WEST:
                return 2;
            case SOUTH:
                return 3;
            default:
                return 4;
        }
     }

    private static class BlockDataBuilder {

        private static BlockDataBuilder of(Material material) {
            return new BlockDataBuilder(material);
        }

        private final BlockData blockData;

        private BlockDataBuilder(Material material) {
            blockData = new BlockData(material);
        }

        private BlockDataBuilder setData(byte data) {
            this.blockData.setData(data);
            return this;
        }

        private BlockDataBuilder setDirectionConsumer(BlockFace direction, boolean callback, Supplier<Directional> supplier) {
            blockData.setDirectionConsumer(block -> {
                if (direction == null) {
                    return;
                }
                final Directional directional;
                if (callback) {
                    directional = supplier.get();
                    if (directional instanceof Lever) {
                        block.setData(getLeverBlockFaceData(direction));
                        block.getState().update();
                        return;
                    }
                } else {
                    directional = cast(Directional.class, block, block.getType().getData());
                }
                if (directional == null) {
                    return;
                }
                directional.setFacingDirection(direction);

                block.setData(((MaterialData) directional).getData());
                block.getState().update();

                if (block.getType() == Material.REDSTONE_TORCH_OFF) {
                    forcePhysicsUpdate(block);
                }
            });
            return this;
        }

        private BlockDataBuilder setDirectionVulnerable(boolean directionVulnerable) {
            this.blockData.setDirectionVulnerable(directionVulnerable);
            return this;
        }

        private BlockData build() {
            return blockData;
        }
    }

    public Pillar add(int delta, BlockData blockData) {
        currentYOffset += delta;

        if (blockData.isDirectionVulnerable()) {
            vulnerableBlocks.put(currentYOffset, blockData);
        } else {
            blocks.put(currentYOffset, blockData);
        }
        return this;
    }

    public Pillar clearYOffset() {
        this.currentYOffset = 0;
        return this;
    }

    public Map<Integer, BlockData> loadBlocks(final Location location) {
        loadBlocks(location, blocks);

        return vulnerableBlocks;
    }

    public static void loadBlocks(final Location location, Map<Integer, BlockData> blocks) {
        final World world = location.getWorld();
        if (world == null) {
            return;
        }
        for (Map.Entry<Integer, BlockData> entry : blocks.entrySet()) {

            final Block block = world.getBlockAt(
                    location.getBlockX(), // x
                    location.getBlockY() + entry.getKey(), // y
                    location.getBlockZ() // z
            );
            final Pillar.BlockData blockData = entry.getValue();

            block.setType(entry.getValue().getMaterial());
            if (blockData.getDirectionConsumer() != null) {
                blockData.getDirectionConsumer().accept(block);
            }

            if (blockData.getData() != 0) {
                block.setData(blockData.getData());
                block.getState().update();
            }
        }
    }

    public Pillar add(int delta, Material material) {
        return add(delta, BlockDataBuilder.of(material).build());
    }

    public Pillar add(int delta, Material material, byte data) {
        return add(delta, BlockDataBuilder.of(material).setData(data).build());
    }

    public Pillar add(int delta, Material material, BlockFace direction) {
        return add(delta, BlockDataBuilder.of(material).setDirectionConsumer(direction, false, null).build());
    }

    public Pillar addCallback(int delta, Material material) {
        return add(delta, BlockDataBuilder.of(material).setDirectionVulnerable(true).build());
    }

    public Pillar addCallback(int delta, Material material, byte data) {
        return add(delta, BlockDataBuilder.of(material).setData(data).setDirectionVulnerable(true).build());
    }

    public Pillar addCallback(int delta, Material material, BlockFace direction, Supplier<Directional> directionalSupplier) {
        return add(delta, BlockDataBuilder.of(material).setDirectionConsumer(direction, true, directionalSupplier).setDirectionVulnerable(true).build());
    }

    public static class BlockData {

        private final Material material;

        private byte data = (byte) 0;
        private Consumer<Block> directionConsumer;
        private boolean directionVulnerable;

        public BlockData(Material material) {
            this.material = material;
        }

        public Material getMaterial() {
            return material;
        }

        public byte getData() {
            return data;
        }

        public void setData(byte data) {
            this.data = data;
        }

        public void setDirectionConsumer(Consumer<Block> directionConsumer) {
            this.directionConsumer = directionConsumer;
        }

        public Consumer<Block> getDirectionConsumer() {
            return directionConsumer;
        }

        public void setDirectionVulnerable(boolean directionVulnerable) {
            this.directionVulnerable = directionVulnerable;
        }

        public boolean isDirectionVulnerable() {
            return directionVulnerable;
        }
    }
}
