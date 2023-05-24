package me.alek.mechanics.tracker.wrappers;

import me.alek.mechanics.Unit;
import me.alek.mechanics.WorkerMechanic;
import me.alek.mechanics.tracker.TrackerEntry;
import me.alek.mechanics.tracker.TrackerWrapper;
import me.alek.mechanics.tracker.entries.WorkerMechanicTrackerEntry;

public class WorkerMechanicTracker<WM extends WorkerMechanic> implements TrackerWrapper<WM> {

    @Override
    public TrackerEntry<WM> addUnit(Unit unit) {
        return new WorkerMechanicTrackerEntry<>((WM) unit);
    }

}
