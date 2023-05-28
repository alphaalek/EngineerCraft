package me.alek.mechanics.transporter;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class TransferEndpoint {

    private final Location location;
    private final BlockFace direction;

    public TransferEndpoint(Location location, BlockFace direction) {
        this.location = location;
        this.direction = direction;
    }

    public Location getLocation() {
        return location;
    }

    public BlockFace getDirection() {
        return direction;
    }
}
