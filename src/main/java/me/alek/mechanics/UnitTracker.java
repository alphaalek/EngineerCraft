package me.alek.mechanics;

import me.alek.EngineerCraft;
import me.alek.hub.Hub;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class UnitTracker<U extends Unit> {

    private final Hub hub;

    private final HashMap<Location, UnitTrackerEntry<U>> units = new HashMap<>();
    private final HashMap<Location, UnitTrackerEntry<U>> endpointUnits = new HashMap<>();

    public UnitTracker(Hub hub) {
        this.hub = hub;
    }

    public void setup() {
        new BukkitRunnable() {

            @Override
            public void run() {
                tick();
            }
        }.runTaskTimerAsynchronously(EngineerCraft.getInstance(), 0L, 1L);
    }

    public void tick() {
        for (UnitTrackerEntry<U> unit : units.values()) {
            unit.tick(hub.getOnlinePlayers());
        }

    }

    public void addUnit(Location location, U unit) {
        final UnitTrackerEntry<U> entry = new UnitTrackerEntry<>(unit);
        units.put(location, entry);
    }

    public boolean hasTrackerAtLocation(Location location) {
        return false;
    }

    public HashMap<Location, UnitTrackerEntry<U>> getUnits() {
        return units;
    }

    public HashMap<Location, UnitTrackerEntry<U>> getEndpointUnits() {
        return endpointUnits;
    }

    public Hub getHub() {
        return hub;
    }

}
