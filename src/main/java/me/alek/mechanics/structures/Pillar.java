package me.alek.mechanics.structures;

import me.alek.utils.FacingUtils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import org.bukkit.Bukkit;
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
import org.bukkit.material.Rails;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Pillar {

    private final Map<Integer, BlockData> blocks = new TreeMap<>();
    private final Map<Integer, BlockData> callbackBlocks = new TreeMap<>();
    private int currentYOffset = 0;

    public Map<Integer, BlockData> getCallbackBlocks() {
        return callbackBlocks;
    }

    public Map<Integer, BlockData> getBlocks() {
        return blocks;
    }

    private static @Nullable Directional cast(Class<?> caster, Block block, Class<?> originClass) {
        Class<?> clazz = originClass;
        while (clazz != null) {

            if (caster.isAssignableFrom(clazz)) {
                return (Directional) block.getState().getData();
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static void forcePhysicsUpdate(@NotNull Block block) {
        final net.minecraft.server.v1_8_R3.World world = ((CraftWorld)block.getWorld()).getHandle();
        final BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());
        final net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(block);

        for (EnumDirection enumDirection : EnumDirection.values()) {
            world.applyPhysics(blockPosition.shift(enumDirection), nmsBlock);
        }
     }

    private static class BlockDataBuilder {

        @Contract(value = "_ -> new", pure = true)
        private static @NotNull BlockDataBuilder of(Material material) {
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

        private void updateDirectional(
                Block block,
                boolean callback,
                Function<BlockFace, BlockFace> blockFaceRotation,
                BlockFace direction,
                Supplier<?> supplier) {
            Directional directional;
            if (callback) {
                directional = (Directional) supplier.get();
                if (directional instanceof Lever) {
                    block.setData(FacingUtils.getLeverBlockFaceData(blockFaceRotation.apply(direction)));
                    block.getState().update();
                    return;
                }
                if (directional instanceof org.bukkit.material.Sign) {
                    block.setData(FacingUtils.getSignBlockFaceData(blockFaceRotation.apply(direction)));
                    block.getState().update();
                    return;
                }
            } else {
                directional = cast(Directional.class, block, block.getType().getData());
            }
            if (directional == null) {
                return;
            }
            directional.setFacingDirection(blockFaceRotation.apply(direction));
            block.setData(((MaterialData) directional).getData());
        }

        private void updateOther(
                Block block,
                Object object,
                Function<BlockFace, BlockFace> blockFaceRotation,
                BlockFace direction) {
            Method method;
            try {
                if (object instanceof Rails) {
                    method = object.getClass().getMethod("setDirection", BlockFace.class, boolean.class);
                    method.invoke(object, blockFaceRotation.apply(direction), false);
                } else {
                    method = object.getClass().getDeclaredMethod("setFacingDirection", BlockFace.class);
                    method.invoke(object, blockFaceRotation.apply(direction));
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
                return;
            }
            block.setData(((MaterialData)object).getData());
        }

        private BlockDataBuilder setDirectionConsumer(BlockFace direction, boolean callback, Supplier<?> supplier) {
            blockData.setDirectionConsumer((blockFaceRotation, block) -> {
                if (direction == null) {
                    return;
                }
                final Object object = supplier.get();
                if (object instanceof Directional) {
                    updateDirectional(block, callback, blockFaceRotation, direction, supplier);
                } else if (object instanceof MaterialData) {
                    updateOther(block, object, blockFaceRotation, direction);
                } else {
                    return;
                }
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

    public Pillar add(int delta, @NotNull BlockData blockData) {
        currentYOffset += delta;

        if (blockData.isDirectionVulnerable()) {
            callbackBlocks.put(currentYOffset, blockData);
        }
        else
        {
            blocks.put(currentYOffset, blockData);
        }
        return this;
    }

    public Pillar addAtIndex(int key, @NotNull BlockData blockData) {
        if (blockData.isDirectionVulnerable()) {
            callbackBlocks.put(key, blockData);
        }
        else {
            blocks.put(key, blockData);
        }
        return this;
    }

    public Pillar clearYOffset() {
        this.currentYOffset = 0;
        return this;
    }

    public Map<Integer, BlockData> loadBlocks(final Location location) {
        return loadBlocks((blockFace) -> blockFace, location);
    }

    public Map<Integer, BlockData> loadBlocks(Function<BlockFace, BlockFace> blockFaceRotation, final Location location) {
        loadBlocks(blockFaceRotation, location, blocks);

        return callbackBlocks;
    }

    public static void loadBlocks(final Location location, Map<Integer, BlockData> blocks) {
        loadBlocks((blockFace) -> blockFace, location, blocks);
    }

    public static void loadBlocks(Function<BlockFace, BlockFace> blockFaceRotation, final @NotNull Location location, Map<Integer, BlockData> blocks) {
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
                blockData.getDirectionConsumer().accept(blockFaceRotation, block);
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

    public Pillar add(int delta, Material material, BlockFace direction, Supplier<?> directionalSupplier) {
        return add(delta, BlockDataBuilder.of(material).setDirectionConsumer(direction, false, directionalSupplier).build());
    }

    public Pillar addCallback(int delta, Material material) {
        return add(delta, BlockDataBuilder.of(material).setDirectionVulnerable(true).build());
    }

    public Pillar addCallback(int delta, Material material, byte data) {
        return add(delta, BlockDataBuilder.of(material).setData(data).setDirectionVulnerable(true).build());
    }

    public Pillar addCallback(int delta, Material material, BlockFace direction, Supplier<?> directionalSupplier) {
        return add(delta, BlockDataBuilder.of(material).setDirectionConsumer(direction, true, directionalSupplier).setDirectionVulnerable(true).build());
    }

    public static class BlockData {

        private final Material material;

        private byte data = (byte) 0;
        private BiConsumer<Function<BlockFace, BlockFace>, Block> directionConsumer;
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

        public void setDirectionConsumer(BiConsumer<Function<BlockFace, BlockFace>, Block> directionConsumer) {
            this.directionConsumer = directionConsumer;
        }

        public BiConsumer<Function<BlockFace, BlockFace>, Block> getDirectionConsumer() {
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
