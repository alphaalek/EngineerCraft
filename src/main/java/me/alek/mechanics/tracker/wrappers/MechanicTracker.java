package me.alek.mechanics.tracker.wrappers;

import me.alek.mechanics.Mechanic;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.TrackerEntry;
import me.alek.mechanics.tracker.TrackerWrapper;
import me.alek.mechanics.tracker.entries.MechanicTrackerEntry;
import me.alek.mechanics.tracker.entries.UnitTrackerEntry;

import java.util.HashMap;
import java.util.function.Function;

public class MechanicTracker<M extends Mechanic> implements TrackerWrapper<M> {

    @Override
    public TrackerEntry<M> addUnit(Unit unit) {
        return new MechanicTrackerEntry<>((M) unit);
    }

}
