package me.alek.mechanics.structures;

import me.alek.utils.handshake.Handshake;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashMap;

public interface IStructure {

    HashMap<Vector, Pillar> getPillars();

    void load(Location location, BlockFace direction, Handshake doneLoading);
}
