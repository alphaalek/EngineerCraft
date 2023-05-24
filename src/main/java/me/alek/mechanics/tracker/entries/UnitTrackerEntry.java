package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.TrackerEntry;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class UnitTrackerEntry<U extends Unit> implements TrackerEntry<U> {

    private final U unit;
    private int counter = 0;

    public UnitTrackerEntry(U unit) {
        this.unit = unit;
    }

    @Override
    public void tick(Set<Player> players) {

    }

    @Override
    public void count() {
        counter++;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void resetCounter() {
        counter = 0;
    }

    @Override
    public U getUnit() {
        return unit;
    }
}
