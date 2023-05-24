package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.tracker.wrappers.MechanicTracker;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.types.MConstructor;
import org.bukkit.Location;

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
    public MConstructor createUnit(Hub hub, Location location, Location signLocation, Tracker<? extends Unit> tracker) {
        return UnitFactory.createConstructor(hub, location, signLocation, (Tracker<MConstructor>) tracker);
    }

}
