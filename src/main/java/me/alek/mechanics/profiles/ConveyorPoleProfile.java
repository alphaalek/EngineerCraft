package me.alek.mechanics.profiles;

import me.alek.mechanics.structures.IStructure;
import me.alek.mechanics.structures.Structures;

public class ConveyorPoleProfile extends ConveyorProfile {
    @Override
    public String getName() {
        return "Conveyor Pole";
    }
    @Override
    public IStructure getStructure() {
        return Structures.CONVEYOR_POLE.getStructure();
    }
}
