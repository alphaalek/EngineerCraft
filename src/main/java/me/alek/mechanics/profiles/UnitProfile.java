package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.structures.IStructure;
import org.bukkit.Location;

public interface UnitProfile<U extends Unit> {

    String getName();

    int getId();

    boolean isWorker();

    boolean isMechanic();

    IStructure getStructure();

    U createUnit(Hub hub, Location location, Location signLocation, Tracker<? extends Unit> tracker);
}
