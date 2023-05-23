package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitTracker;
import me.alek.mechanics.types.MSmelter;
import org.bukkit.Location;

public class SmelterProfile implements WorkerMechanicProfile<MSmelter> {

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
    public MSmelter createUnit(Hub hub, Location location, UnitTracker<MSmelter> tracker) {
        return UnitFactory.createSmelter(hub, location, tracker);
    }

}
