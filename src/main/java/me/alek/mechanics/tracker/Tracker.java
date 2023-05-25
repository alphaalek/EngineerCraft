package me.alek.mechanics.tracker;

import me.alek.EngineerCraft;
import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class Tracker<U extends Unit> {

    private final Hub hub;
    private final TrackerWrapper<U> trackerWrapper;

    private final HashMap<Location, TrackerEntry<U>> units = new HashMap<>();
    private final HashMap<Location, TrackerEntry<U>> endpointUnits = new HashMap<>();

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
        for (TrackerEntry<U> unit : units.values()) {
            unit.count();
            unit.tick(hub.getOnlinePlayers());
        }

    }

    public void addUnit(Location location, Unit unit) {
        units.put(location, trackerWrapper.addUnit(unit));
    }

    public boolean hasTrackerAtLocation(Location location) {
        return units.containsKey(location);
    }

    public HashMap<Location, TrackerEntry<U>> getUnits() {
        return units;
    }

    public HashMap<Location, TrackerEntry<U>> getEndpointUnits() {
        return endpointUnits;
    }

    public Hub getHub() {
        return hub;
    }

}
