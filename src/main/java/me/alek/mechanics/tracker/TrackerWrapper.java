package me.alek.mechanics.tracker;

import me.alek.mechanics.Mechanic;
import me.alek.mechanics.Unit;
import me.alek.mechanics.WorkerMechanic;
import me.alek.mechanics.tracker.entries.MechanicTrackerEntry;
import me.alek.mechanics.tracker.entries.UnitTrackerEntry;
import me.alek.mechanics.tracker.entries.WorkerMechanicTrackerEntry;

public interface TrackerWrapper<U extends Unit> {

    TrackerEntry<U> addUnit(Unit unit);

    enum Wrappers {
        UNIT(new UnitTracker<>()),
        MECHANIC(new MechanicTracker<>()),
        WORKER_MECHANIC(new WorkerMechanicTracker<>());

        private final TrackerWrapper<? extends Unit> wrapper;

        Wrappers(TrackerWrapper<? extends Unit> wrapper) {
            this.wrapper = wrapper;
        }

        public TrackerWrapper<? extends Unit> getWrapper() {
            return wrapper;
        }
    }

    class WorkerMechanicTracker<WM extends WorkerMechanic> implements TrackerWrapper<WM> {

        @Override
        public TrackerEntry<WM> addUnit(Unit unit) {
            return new WorkerMechanicTrackerEntry<>((WM) unit);
        }

    }

    class MechanicTracker<M extends Mechanic> implements TrackerWrapper<M> {

        @Override
        public TrackerEntry<M> addUnit(Unit unit) {
            return new MechanicTrackerEntry<>((M) unit);
        }

    }

    class UnitTracker<U extends Unit> implements TrackerWrapper<U> {

        @Override
        public TrackerEntry<U> addUnit(Unit unit) {
            return new UnitTrackerEntry<>((U) unit);
        }
    }
}
