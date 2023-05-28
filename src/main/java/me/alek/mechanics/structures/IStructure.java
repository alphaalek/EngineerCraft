package me.alek.mechanics.structures;

import me.alek.mechanics.structures.Pillar;
import me.alek.mechanics.transporter.RelativeTransferLocation;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public interface IStructure {

    List<RelativeTransferLocation> getInputs();

    List<RelativeTransferLocation> getOutputs();

    Sign getSign();

    boolean isTransporter();

    boolean isMechanic();

    HashMap<Vector, Pillar> getPillars();

    void load(Location location, BlockFace direction, Handshake doneLoading);
}
