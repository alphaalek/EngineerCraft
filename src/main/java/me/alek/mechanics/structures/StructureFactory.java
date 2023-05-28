package me.alek.mechanics.structures;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StructureFactory {

    private static final Structure commonWorkerMechanicElements = new Structure(){{
        setMechanic(true);
        set(0, 1, new Pillar()
                .add(2, Material.IRON_BARDING));
        set(1, 0, new Pillar()
                .addCallback(2, Material.REDSTONE_TORCH_OFF, BlockFace.EAST, RedstoneTorch::new));
        set(-1, 0, new Pillar()
                .addCallback(2, Material.REDSTONE_TORCH_OFF, BlockFace.WEST, RedstoneTorch::new));
        set(0, -1, new Pillar()
                .addCallback(1, Material.LEVER, BlockFace.NORTH, Lever::new));
        setSign(0, 2, -1, BlockFace.SOUTH, true);
    }};

    @Contract(" -> new")
    public static @NotNull Structure createMiner() {
        return new Structure(){{
            setMechanic(true);
            setTransporter(true);
            set(0, 0, new Pillar()
                    .add(0, Material.HOPPER)
                    .add(1, Material.STAINED_CLAY, DyeColor.CYAN.getData())
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
                    .add(1, Material.CHEST, BlockFace.NORTH, Chest::new)
            );
            combineStructure(commonWorkerMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createSmelter() {
        return new Structure(){{
            setMechanic(true);
            setTransporter(true);
            set(0, 0, new Pillar()
                    .add(0, Material.CAULDRON)
                    .add(1, Material.FURNACE, BlockFace.EAST, Furnace::new)
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
            );
            combineStructure(commonWorkerMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createConstructor() {
        return new Structure(){{
            setMechanic(true);
            setTransporter(true);
            set(0, 0, new Pillar()
                    .add(0, Material.CAULDRON)
                    .add(1, Material.WORKBENCH)
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
            );
            combineStructure(commonWorkerMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createConveyor() {
        return new Structure(){{
            setTransporter(true);
            set(0, 0, new Pillar()
                    .add(0, Material.COBBLESTONE)
                    .addCallback(1, Material.RAILS, BlockFace.EAST, Rails::new)
            );
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createConveyorPole() {
        return new Structure(){{
            setTransporter(true);
            set(0, 0, new Pillar()
                    .add(0, Material.STONE, (byte) 5)
                    .addCallback(1, Material.RAILS, BlockFace.EAST, Rails::new)
            );
        }};
    }
}
