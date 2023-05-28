package me.alek.mechanics.tracker;

import me.alek.EngineerCraft;
import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.entries.CommonTrackerEntry;
import me.alek.utils.Handshake;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tracker<U extends Unit> {

    private final Hub hub;
    private final TrackerWrapper<U> trackerWrapper;

    private final HashMap<Chunk, List<CommonTrackerEntry<U>>> units = new HashMap<>();
    private final HashMap<Chunk, List<CommonTrackerEntry<U>>> endpointUnits = new HashMap<>();

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
        for (List<CommonTrackerEntry<U>> unitList : units.values()) {
            for (CommonTrackerEntry<U> unit : unitList) {
                unit.commonTick();
                unit.tick(hub.getOnlinePlayers());
            }
        }

    }

    public void addUnit(Location location, Unit unit) {
        if (!units.containsKey(location.getChunk())) {
            units.put(location.getChunk(), new ArrayList<>());
        }
        units.get(location.getChunk()).add((CommonTrackerEntry<U>) trackerWrapper.addUnit(unit));
    }

    public void addUnit(Location location, Unit unit, Handshake handshake) {
        handshake.addRequest(() -> addUnit(location, unit));
    }

    public boolean hasTrackerAtLocation(Location location) {
        return units.containsKey(location);
    }

    public HashMap<Chunk, List<CommonTrackerEntry<U>>> getUnits() {
        return units;
    }

    public List<CommonTrackerEntry<U>> getUnitsForChunk(Chunk chunk) {
        return units.get(chunk);
    }

    public HashMap<Chunk, List<CommonTrackerEntry<U>>> getEndpointUnits() {
        return endpointUnits;
    }

    public List<CommonTrackerEntry<U>> getEndpointUnitsForChunk(Chunk chunk) {
        return endpointUnits.get(chunk);
    }

    public Hub getHub() {
        return hub;
    }

}
