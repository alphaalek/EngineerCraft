package me.alek.mechanics.types;

import me.alek.hub.Hub;
import me.alek.mechanics.transporter.SimpleTransporter;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.transporter.TransferLocation;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class Conveyor implements SimpleTransporter {

    private final Hub hub;
    private final Location location;

    public Conveyor(Hub hub, Location location) {
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
    public TransferLocation getInputLocation() {
        return null;
    }

    @Override
    public TransferLocation getOutputLocation() {
        return null;
    }

    @Override
    public void in(ItemStack item) {

    }

    @Override
    public void out(ItemStack item) {

    }
}
