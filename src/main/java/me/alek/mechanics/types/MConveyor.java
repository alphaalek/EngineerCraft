package me.alek.mechanics.types;

import me.alek.hub.Hub;
import me.alek.mechanics.Transporter;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.profiles.UnitProfile;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class MConveyor implements Transporter {

    private final Hub hub;
    private final Location location;

    public MConveyor(Hub hub, Location location) {
        this.hub = hub;
        this.location = location;
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
    public BlockFace getDirection() {
        return BlockFace.SOUTH;
    }

    @Override
    public UnitProfile<? extends Unit> getProfile() {
        return UnitLibrary.UnitType.CONVEYOR.getProfile();
    }

    @Override
    public void in() {

    }

    @Override
    public void out() {

    }
}
