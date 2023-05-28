package me.alek.mechanics.profiles;

import me.alek.mechanics.Unit;
import me.alek.mechanics.structures.IStructure;
import org.bukkit.Material;

public interface UnitProfile<U extends Unit> {

    String getName();

    int getId();

    boolean isWorker();

    boolean isMechanic();

    IStructure getStructure();

    Material getBlockType();
}
