package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.BareUnitProfile;
import me.alek.mechanics.profiles.MechanicProfile;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.structures.IStructure;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.Constructor;
import me.alek.mechanics.types.Conveyor;
import me.alek.mechanics.types.Miner;
import me.alek.mechanics.types.Smelter;
import me.alek.utils.FacingUtils;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.function.Function;

public class UnitFactory {

    public static Unit createUnitObject(Hub hub, Location location, BlockFace direction, UnitProfile<? extends Unit> profile, Tracker<? extends Unit> tracker, int level, Handshake doneLoading) {
        Location signLocation = null;
        final IStructure structure = profile.getStructure();

        if (profile.getStructure().isMechanic()) {

            final Function<Vector, Vector> rotateVectorFunction = FacingUtils.getRotateVectorFunction(direction);
            final Vector originalVector = structure.getSign().getVector();
            final Vector rotatedVector = rotateVectorFunction.apply(originalVector);

            signLocation = location.clone().add(rotatedVector.setY(originalVector.getY()));
        }
        if (profile instanceof BareUnitProfile) {
            return ((BareUnitProfile<? extends Unit>)profile).createUnit(hub, location, tracker);
        }
        if (profile instanceof MechanicProfile) {
            return ((MechanicProfile<? extends Unit>)profile).createUnit(hub, direction, location, signLocation, tracker, level, doneLoading);
        }
        return null;
    }

    public static Constructor createConstructor(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<Constructor> tracker, int level, Handshake doneLoading) {
        Constructor constructor = new Constructor(hub, direction, location, signLocation, level);
        tracker.addUnit(location, constructor, doneLoading);
        return constructor;
    }

    public static Miner createMiner(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<Miner> tracker, int level, Handshake doneLoading) {
        Miner miner = new Miner(hub, direction, location, signLocation, level);
        tracker.addUnit(location, miner, doneLoading);
        return miner;
    }

    public static Smelter createSmelter(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<Smelter> tracker, int level, Handshake doneLoading) {
        Smelter smelter = new Smelter(hub, direction, location, signLocation, level);
        tracker.addUnit(location, smelter, doneLoading);
        return smelter;
    }

    public static Conveyor createConveyor(Hub hub, Location location, Tracker<Conveyor> tracker) {
        Conveyor conveyor = new Conveyor(hub, location);
        tracker.addUnit(location, conveyor);
        return conveyor;
    }
}
