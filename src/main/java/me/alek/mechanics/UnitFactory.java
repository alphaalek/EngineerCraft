package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.BareUnitProfile;
import me.alek.mechanics.profiles.MechanicProfile;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.structures.api.IMechanicStructure;
import me.alek.mechanics.structures.api.IStructure;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.MConstructor;
import me.alek.mechanics.types.MConveyor;
import me.alek.mechanics.types.MMiner;
import me.alek.mechanics.types.MSmelter;
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

        if (profile.getStructure() instanceof IMechanicStructure) {

            final IMechanicStructure mechanicStructure = (IMechanicStructure) structure;
            final Function<Vector, Vector> rotateVectorFunction = FacingUtils.getRotateVectorFunction(direction);
            final Vector originalVector = mechanicStructure.getSign().getVector();
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

    public static MConstructor createConstructor(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<MConstructor> tracker, int level, Handshake doneLoading) {
        MConstructor constructor = new MConstructor(hub, direction, location, signLocation, level);
        tracker.addUnit(location, constructor, doneLoading);
        return constructor;
    }

    public static MMiner createMiner(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<MMiner> tracker, int level, Handshake doneLoading) {
        MMiner miner = new MMiner(hub, direction, location, signLocation, level);
        tracker.addUnit(location, miner, doneLoading);
        return miner;
    }

    public static MSmelter createSmelter(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<MSmelter> tracker, int level, Handshake doneLoading) {
        MSmelter smelter = new MSmelter(hub, direction, location, signLocation, level);
        tracker.addUnit(location, smelter, doneLoading);
        return smelter;
    }

    public static MConveyor createConveyor(Hub hub, Location location, Tracker<MConveyor> tracker) {
        MConveyor conveyor = new MConveyor(hub, location);
        tracker.addUnit(location, conveyor);
        return conveyor;
    }
}
