package me.alek.mechanics.structures;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Ladder;
import org.bukkit.material.Lever;
import org.bukkit.material.RedstoneTorch;

public class StructureFactory {

    private static void parseStructure(Structure structure) {
        if (structure instanceof IStructureParser) {
            ((IStructureParser)structure).parse();
        }
    }

    private static final MechanicStructure defaultMechanicElements = new MechanicStructure(){{
        set(0, 1, new Pillar()
                .add(1, Material.COBBLE_WALL)
                .add(1, Material.COBBLE_WALL)
                .add(1, Material.IRON_BLOCK)
                .add(1, Material.WOOL, DyeColor.YELLOW.getData()));
        set(1, 1, new Pillar()
                .addCallback(3, Material.REDSTONE_TORCH_OFF, BlockFace.EAST, RedstoneTorch::new));
        set(0, 2, new Pillar()
                .addCallback(3, Material.REDSTONE_TORCH_OFF, BlockFace.SOUTH, RedstoneTorch::new));
        set(-1, 1, new Pillar()
                .addCallback(3, Material.REDSTONE_TORCH_OFF, BlockFace.WEST, RedstoneTorch::new));
        set(0, -1, new Pillar()
                .addCallback(1, Material.LEVER, BlockFace.NORTH, Lever::new));
        setSign(0, 2, -1, BlockFace.NORTH);
        parseStructure(this);
    }};

    public static MechanicStructure createMiner() {
        final MechanicStructure miner = new MechanicStructure();
        miner.set(0, 0, new Pillar()
                .add(0, Material.HOPPER)
                .add(1, Material.STAINED_CLAY, DyeColor.ORANGE.getData())
                .add(1, Material.STAINED_CLAY, DyeColor.CYAN.getData())
                .add(1, Material.CHEST, BlockFace.NORTH));
        miner.combineStructure(defaultMechanicElements);
        return miner;
    }

    public static MechanicStructure createSmelter() {
        final MechanicStructure smelter = new MechanicStructure();
        smelter.set(0, 0, new Pillar()
                .add(0, Material.CAULDRON)
                .add(1, Material.FURNACE, BlockFace.EAST)
                .add(1, Material.STAINED_CLAY, DyeColor.CYAN.getData())
                .add(1, Material.GLASS));
        smelter.combineStructure(defaultMechanicElements);
        return smelter;
    }

    public static MechanicStructure createConstructor() {
        final MechanicStructure constructor = new MechanicStructure();
        constructor.set(0, 0, new Pillar()
                .add(0, Material.CAULDRON)
                .add(1, Material.WORKBENCH)
                .add(1, Material.STAINED_CLAY, DyeColor.CYAN.getData())
                .add(1, Material.GLASS));
        constructor.combineStructure(defaultMechanicElements);
        return constructor;
    }

    public static Structure createConveyor() {
        final MechanicStructure conveyor = new MechanicStructure();
        conveyor.set(0, 0, new Pillar()
                .add(1, Material.DAYLIGHT_DETECTOR));
        return conveyor;
    }
}
