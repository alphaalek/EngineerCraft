package me.alek.mechanics.structures.api;

import me.alek.mechanics.structures.Sign;
import me.alek.mechanics.structures.api.IStructure;
import me.alek.mechanics.structures.api.IStructureParser;

public interface IMechanicStructure extends IStructure, IStructureParser {

    Sign getSign();
}
