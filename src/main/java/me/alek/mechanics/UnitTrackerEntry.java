package me.alek.mechanics;

import org.bukkit.entity.Player;

import java.util.Set;

public class UnitTrackerEntry<U extends Unit> {

    private final U unit;

    public UnitTrackerEntry(U unit) {
        this.unit = unit;
    }

    public void tick(Set<Player> players) {

    }

    public U getUnit() {
        return unit;
    }
}
