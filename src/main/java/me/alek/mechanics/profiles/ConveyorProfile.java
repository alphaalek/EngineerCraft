package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.types.MConveyor;
import org.bukkit.Location;

public class ConveyorProfile implements UnitProfile<MConveyor> {

    @Override
    public String getName() {
        return "Conveyor";
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public boolean isWorker() {
        return true;
    }

    @Override
    public boolean isMechanic() {
        return false;
    }

    @Override
    public Structure getStructure() {
        return Structures.CONVEYOR.getStructure();
    }

    @Override
    public MConveyor createUnit(Hub hub, Location location, Location signLocation, Tracker<? extends Unit> tracker) {
        return UnitFactory.createConveyor(hub, location, (Tracker<MConveyor>) tracker);
    }
}
