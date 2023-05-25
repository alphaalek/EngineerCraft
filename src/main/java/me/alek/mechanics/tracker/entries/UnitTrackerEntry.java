package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.Unit;
import org.bukkit.entity.Player;

import java.util.Set;

public class UnitTrackerEntry<U extends Unit> extends CommonTrackerEntry<U> {

    private final U unit;

    public UnitTrackerEntry(U unit) {
        this.unit = unit;
    }

    @Override
    public void tick(Set<Player> players) {

    }

    @Override
    public U getUnit() {
        return unit;
    }
}
