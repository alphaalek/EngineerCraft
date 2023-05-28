package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.structures.IStructure;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.types.Conveyor;
import org.bukkit.Location;
import org.bukkit.Material;

public class ConveyorProfile implements UnitProfile<Conveyor> {

    @Override
    public String getName() {
        return "Conveyor";
    }

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public boolean isWorker() {
        return false;
    }

    @Override
    public boolean isMechanic() {
        return false;
    }

    @Override
    public IStructure getStructure() {
        return Structures.CONVEYOR.getStructure();
    }

    @Override
    public Material getBlockType() {
        return Material.DAYLIGHT_DETECTOR;
    }

}
