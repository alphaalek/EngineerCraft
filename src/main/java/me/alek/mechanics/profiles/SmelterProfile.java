package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.tracker.wrappers.MechanicTracker;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.types.MSmelter;
import org.bukkit.Location;

public class SmelterProfile implements MechanicProfile<MSmelter> {

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
    public MSmelter createUnit(Hub hub, Location location, Location signLocation, Tracker<? extends Unit> tracker) {
        return UnitFactory.createSmelter(hub, location, signLocation, (Tracker<MSmelter>) tracker);
    }

}
