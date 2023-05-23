package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitTracker;
import me.alek.mechanics.types.MConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ConstructorProfile implements WorkerMechanicProfile<MConstructor> {

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
    public MConstructor createUnit(Hub hub, Location location, UnitTracker<MConstructor> tracker) {
        return UnitFactory.createConstructor(hub, location, tracker);
    }

}
