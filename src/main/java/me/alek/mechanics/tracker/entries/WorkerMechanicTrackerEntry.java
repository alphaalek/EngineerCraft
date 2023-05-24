package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.WorkerMechanic;

public class WorkerMechanicTrackerEntry<WM extends WorkerMechanic> extends MechanicTrackerEntry<WM> {

    private final WM workerMechanic;

    public WorkerMechanicTrackerEntry(WM workerMechanic) {
        super(workerMechanic);
        this.workerMechanic = workerMechanic;
    }

    @Override
    public WM getUnit() {
        return workerMechanic;
    }
}
