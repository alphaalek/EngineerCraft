package me.alek.mechanics.tracker.wrappers;

import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.TrackerEntry;
import me.alek.mechanics.tracker.TrackerWrapper;
import me.alek.mechanics.tracker.entries.UnitTrackerEntry;

public class UnitTracker<U extends Unit> implements TrackerWrapper<U> {

    @Override
    public TrackerEntry<U> addUnit(Unit unit) {
        return new UnitTrackerEntry<>((U) unit);
    }
}
