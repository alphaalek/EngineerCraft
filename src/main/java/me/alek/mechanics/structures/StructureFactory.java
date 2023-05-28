package me.alek.mechanics.structures;

import me.alek.mechanics.structures.api.IStructureParser;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Lever;
import org.bukkit.material.Rails;
import org.bukkit.material.RedstoneTorch;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class StructureFactory {

    private static void parseStructure(Structure structure) {
        if (structure instanceof IStructureParser) {
            ((IStructureParser)structure).parse();
        }
    }

    private static final MechanicStructure defaultMechanicElements = new MechanicStructure(){{
        set(0, 1, new Pillar()
                .add(2, Material.IRON_BARDING));
        set(1, 0, new Pillar()
                .addCallback(2, Material.REDSTONE_TORCH_OFF, BlockFace.EAST, RedstoneTorch::new));
        set(-1, 0, new Pillar()
                .addCallback(2, Material.REDSTONE_TORCH_OFF, BlockFace.SOUTH, RedstoneTorch::new));
        set(0, -1, new Pillar()
                .addCallback(1, Material.LEVER, BlockFace.NORTH, Lever::new));
        setSign(0, 2, -1, BlockFace.SOUTH);
        parseStructure(this);
    }};

    @Contract(" -> new")
    public static @NotNull MechanicStructure createMiner() {
        return new MechanicStructure(){{
            set(0, 0, new Pillar()
                    .add(0, Material.HOPPER)
                    .add(1, Material.STAINED_CLAY, DyeColor.CYAN.getData())
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
                    .add(1, Material.CHEST, BlockFace.NORTH)
            );
            combineStructure(defaultMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull MechanicStructure createSmelter() {
        return new MechanicStructure(){{
            set(0, 0, new Pillar()
                    .add(0, Material.CAULDRON)
                    .add(1, Material.FURNACE, BlockFace.EAST)
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
            );
            combineStructure(defaultMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull MechanicStructure createConstructor() {
        return new MechanicStructure(){{
            set(0, 0, new Pillar()
                    .add(0, Material.CAULDRON)
                    .add(1, Material.WORKBENCH)
                    .add(1, Material.STAINED_CLAY, DyeColor.YELLOW.getData())
            );
            combineStructure(defaultMechanicElements);
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createConveyor() {
        return new Structure(){{
            set(0, 0, new Pillar()
                    .add(0, Material.COBBLESTONE)
                    .addCallback(1, Material.RAILS, BlockFace.EAST, Rails::new)
            );
        }};
    }

    @Contract(" -> new")
    public static @NotNull Structure createConveyorPole() {
        return new Structure(){{
            set(0, 0, new Pillar()
                    .add(0, Material.STONE, (byte) 5)
                    .addCallback(1, Material.RAILS, BlockFace.EAST, Rails::new)
            );
        }};
    }
}
