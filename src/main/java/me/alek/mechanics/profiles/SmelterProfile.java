package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.Smelter;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class SmelterProfile implements MechanicProfile<Smelter> {

    @Override
    public String getName() {
        return "Smelter";
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public boolean isWorker() {
        return true;
    }

    @Override
    public boolean isMechanic() {
        return true;
    }

    @Override
    public Structure getStructure() {
        return Structures.SMELTER.getStructure();
    }

    @Override
    public Material getBlockType() {
        return Material.FURNACE;
    }

    @Override
    public Smelter createUnit(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<? extends Unit> tracker, int level, Handshake doneLoading) {
        return UnitFactory.createSmelter(hub, direction, location, signLocation, (Tracker<Smelter>) tracker, level, doneLoading);
    }

}
