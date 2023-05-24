package me.alek.mechanics.types;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.WorkerMechanic;
import me.alek.mechanics.profiles.UnitProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MSmelter implements WorkerMechanic {

    private final Hub hub;
    private final Location location;
    private final Location signLocation;
    private boolean working = false;

    public MSmelter(Hub hub, Location location, Location signLocation) {
        this.hub = hub;
        this.location = location;
        this.signLocation = signLocation;
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean isWorking() {
        return working;
    }

    @Override
    public void setWorking(boolean working) {
        this.working = working;
    }

    @Override
    public Hub getHub() {
        return hub;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public UnitProfile<? extends Unit> getProfile() {
        return UnitLibrary.UnitType.SMELTER.getProfile();
    }

    @Override
    public void in() {

    }

    @Override
    public void out() {

    }

    @Override
    public Location getSignLocation() {
        return signLocation;
    }
}
