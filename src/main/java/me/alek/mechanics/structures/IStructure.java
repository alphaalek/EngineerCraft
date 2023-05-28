package me.alek.mechanics.structures;

import me.alek.mechanics.structures.Pillar;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashMap;

public interface IStructure {

    Sign getSign();

    boolean isTransporter();

    boolean isMechanic();

    HashMap<Vector, Pillar> getPillars();

    void load(Location location, BlockFace direction, Handshake doneLoading);
}
