package me.alek.mechanics.tracker;

import me.alek.EngineerCraft;
import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.entries.CommonTrackerEntry;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Tracker<U extends Unit> {

    private final Hub hub;
    private final TrackerWrapper<U> trackerWrapper;

    private final HashMap<Location, CommonTrackerEntry<U>> units = new HashMap<>();
    private final HashMap<Location, CommonTrackerEntry<U>> endpointUnits = new HashMap<>();

    public Tracker(Hub hub, TrackerWrapper<U> trackerWrapper) {
        this.hub = hub;
        this.trackerWrapper = trackerWrapper;
    }

    public void setup() {
        new BukkitRunnable() {

            @Override
            public void run() {
                tick();
            }
        }.runTaskTimerAsynchronously(EngineerCraft.getInstance(), 0L, 5L);
    }

    public void tick() {
        for (CommonTrackerEntry<U> unit : units.values()) {
            unit.commonTick();
            unit.tick(hub.getOnlinePlayers());
        }

    }

    public void addUnit(Location location, Unit unit) {
        units.put(location, (CommonTrackerEntry<U>) trackerWrapper.addUnit(unit));
    }

    public boolean hasTrackerAtLocation(Location location) {
        return units.containsKey(location);
    }

    public HashMap<Location, CommonTrackerEntry<U>> getUnits() {
        return units;
    }

    public HashMap<Location, CommonTrackerEntry<U>> getEndpointUnits() {
        return endpointUnits;
    }

    public Hub getHub() {
        return hub;
    }

}
