package me.alek.mechanics.structures;

public enum Structures {

    MINER(StructureFactory.createMiner()),
    SMELTER(StructureFactory.createSmelter()),
    CONSTRUCTOR(StructureFactory.createConstructor()),

    CONVEYOR(StructureFactory.createConveyor()),
    CONVEYOR_POLE(StructureFactory.createConveyorPole());

    private final Structure structure;

    Structures(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructure() {
        return structure;
    }
}
