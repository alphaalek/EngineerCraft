package me.alek.mechanics;

import org.bukkit.Location;

public interface Mechanic extends Unit {

    Location getSignLocation();

    int getLevel();
}
