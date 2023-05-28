package me.alek.mechanics.types;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.WorkerMechanic;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.transporter.SimpleTransporter;
import me.alek.mechanics.transporter.TransferEndpoint;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class Smelter implements WorkerMechanic, SimpleTransporter {

    private final Hub hub;
    private final BlockFace direction;
    private final Location location;
    private final Location signLocation;
    private final TransferEndpoint input;
    private final TransferEndpoint output;
    private boolean working = false;
    private int level;

    public Smelter(Hub hub,
                   BlockFace direction,
                   Location location,
                   Location signLocation,
                   TransferEndpoint input,
                   TransferEndpoint output,
                   int level) {
        this.hub = hub;
        this.location = location;
        this.signLocation = signLocation;
        this.direction = direction;
        this.input = input;
        this.output = output;
        this.level = level;
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
    public BlockFace getDirection() {
        return direction;
    }

    @Override
    public UnitProfile<? extends Unit> getProfile() {
        return UnitLibrary.UnitType.SMELTER.getProfile();
    }

    @Override
    public Location getSignLocation() {
        return signLocation;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void in(ItemStack item) {

    }

    @Override
    public void out(ItemStack item) {

    }

    @Override
    public TransferEndpoint getInputEndpoint() {
        return input;
    }

    @Override
    public TransferEndpoint getOutputEndpoint() {
        return output;
    }
}
