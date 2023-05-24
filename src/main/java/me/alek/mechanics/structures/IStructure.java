package me.alek.mechanics.structures;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashMap;

public interface IStructure {

    HashMap<Vector, Pillar> getPillars();

    void load(Location location);
}
