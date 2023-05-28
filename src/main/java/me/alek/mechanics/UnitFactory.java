package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.*;
import me.alek.mechanics.structures.IStructure;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.transporter.RelativeTransferLocation;
import me.alek.mechanics.transporter.TransferEndpoint;
import me.alek.mechanics.types.Constructor;
import me.alek.mechanics.types.Conveyor;
import me.alek.mechanics.types.Miner;
import me.alek.mechanics.types.Smelter;
import me.alek.utils.FacingUtils;
import me.alek.utils.Handshake;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UnitFactory {

    public static Unit createUnitObject(Hub hub, Location location, BlockFace direction, UnitProfile<? extends Unit> profile, Tracker<? extends Unit> tracker, int level, Handshake doneLoading) {
        Location signLocation = null;
        final List<TransferEndpoint> inputs = new ArrayList<>();
        final List<TransferEndpoint> outputs = new ArrayList<>();

        TransferEndpoint singleInput = null;
        TransferEndpoint singleOutput = null;

        final IStructure structure = profile.getStructure();

        if (structure.isMechanic()) {
            signLocation = assignSignLocation(structure, direction, location);
        }
        if (structure.isTransporter()) {
            assignEndpoints(structure, location, inputs, outputs);
        }
        if (inputs.isEmpty() || outputs.isEmpty()) return null;

        singleInput = inputs.get(0);
        singleOutput = outputs.get(0);

        Unit unit = null;
        if (profile instanceof ConstructorProfile) {
            unit = new Constructor(hub, direction, location, signLocation, singleInput, singleOutput, level);
        }
        if (profile instanceof MinerProfile) {
            unit = new Miner(hub, direction, location, signLocation, singleInput, singleOutput, level);
        }
        if (profile instanceof SmelterProfile) {
            unit = new Smelter(hub, direction, location, signLocation, singleInput, singleOutput, level);
        }
        if (profile instanceof ConveyorProfile) {
            unit = new Conveyor(hub, location, singleInput, singleOutput);
        }
        if (unit != null) {
            tracker.addUnit(location, unit, doneLoading);
        }
        return unit;
    }

    private static Location assignSignLocation(IStructure structure, BlockFace direction, Location location) {
        final Function<Vector, Vector> rotateVectorFunction = FacingUtils.getRotateVectorFunction(direction);

        final Vector originalVector = structure.getSign().getVector();
        final Vector rotatedVector = rotateVectorFunction.apply(originalVector);

        return location.clone().add(rotatedVector.setY(originalVector.getY()));
    }

    private static void assignEndpoints(IStructure structure, Location location, List<TransferEndpoint> inputs, List<TransferEndpoint> outputs) {
        for (RelativeTransferLocation relativeInput : structure.getInputs()) {
            inputs.add(
                    new TransferEndpoint(
                            location.clone().add(relativeInput.getVector()),
                            relativeInput.getDirection()
                    )
            );
        }
        for (RelativeTransferLocation relativeOutput : structure.getOutputs()) {
            outputs.add(
                    new TransferEndpoint(
                            location.clone().add(relativeOutput.getVector()),
                            relativeOutput.getDirection()
                    )
            );
        }
    }

}
