package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.Tracker;
import org.bukkit.Location;

public interface BareUnitProfile<U extends Unit> extends UnitProfile<U> {

    U createUnit(Hub hub, Location location, Tracker<? extends Unit> tracker);
}
