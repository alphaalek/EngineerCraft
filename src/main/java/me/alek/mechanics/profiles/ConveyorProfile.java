package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitTracker;
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
    public MConveyor createUnit(Hub hub, Location location, UnitTracker<MConveyor> tracker) {
        return UnitFactory.createConveyor(hub, location, tracker);
    }
}
