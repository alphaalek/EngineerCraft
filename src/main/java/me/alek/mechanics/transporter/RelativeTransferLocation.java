package me.alek.mechanics.transporter;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;


public class RelativeTransferLocation {

    private final Vector vector;
    private final BlockFace direction;

    public RelativeTransferLocation(Vector vector, BlockFace direction) {
        this.vector = vector;
        this.direction = direction;
    }

    public Vector getVector() {
        return vector;
    }

    public BlockFace getDirection() {
        return direction;
    }
}
