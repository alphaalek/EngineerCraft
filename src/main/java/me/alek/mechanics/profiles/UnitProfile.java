package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Mechanic;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitTracker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface UnitProfile<U extends Unit> {

    String getName();

    int getId();

    boolean isWorker();

    boolean isMechanic();

    U createUnit(Hub hub, Location location, UnitTracker<U> tracker);
}
