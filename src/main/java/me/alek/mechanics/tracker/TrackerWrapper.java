package me.alek.mechanics.tracker;

import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.wrappers.MechanicTracker;
import me.alek.mechanics.tracker.wrappers.UnitTracker;
import me.alek.mechanics.tracker.wrappers.WorkerMechanicTracker;

import java.util.ArrayList;
import java.util.function.Function;

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
}
