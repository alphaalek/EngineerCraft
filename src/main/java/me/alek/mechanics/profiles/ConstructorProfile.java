package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.MConstructor;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class ConstructorProfile implements MechanicProfile<MConstructor> {

    @Override
    public String getName() {
        return "Constructor";
    }

    @Override
    public int getId() {
        return 3;
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
        return Structures.CONSTRUCTOR.getStructure();
    }

    @Override
    public Material getBlockType() {
        return Material.WORKBENCH;
    }

    @Override
    public MConstructor createUnit(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<? extends Unit> tracker, int level, Handshake doneLoading) {
        return UnitFactory.createConstructor(hub, direction, location, signLocation, (Tracker<MConstructor>) tracker, level, doneLoading);
    }

}
