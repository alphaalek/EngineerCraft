package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.TrackerEntry;

public abstract class CommonTrackerEntry<U extends Unit> implements TrackerEntry<U> {

    private int counter = 0;

    public void commonTick() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter = 0;
    }
}
