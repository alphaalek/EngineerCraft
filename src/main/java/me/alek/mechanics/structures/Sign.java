package me.alek.mechanics.structures;

import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class Sign {

    private final Vector vector;
    private final BlockFace blockFace;

    public Sign(Vector vector, BlockFace blockFace) {
        this.vector = vector;
        this.blockFace = blockFace;
    }

    public Vector getVector() {
        return vector;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }
}
