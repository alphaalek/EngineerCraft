package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.structures.IMechanicStructure;
import me.alek.mechanics.structures.IStructure;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.MConstructor;
import me.alek.mechanics.types.MConveyor;
import me.alek.mechanics.types.MMiner;
import me.alek.mechanics.types.MSmelter;
import me.alek.utils.FacingUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.function.Function;

public class UnitFactory {

    public static Unit createUnit(Hub hub, Location location, BlockFace direction, UnitProfile<? extends Unit> profile, Tracker<? extends Unit> tracker) {
        Location signLocation = null;
        final IStructure structure = profile.getStructure();

        if (profile.getStructure() instanceof IMechanicStructure) {

            final IMechanicStructure mechanicStructure = (IMechanicStructure) structure;
            final Function<Vector, Vector> rotateVectorFunction = FacingUtils.getRotateVectorFunction(direction);
            final Vector originalVector = mechanicStructure.getSign().getVector();
            final Vector rotatedVector = rotateVectorFunction.apply(originalVector);

            signLocation = location.clone().add(rotatedVector.setY(originalVector.getY()));
        }
        Unit unit = profile.createUnit(hub, location, signLocation, tracker);
        tracker.addUnit(location, unit);
        return unit;
    }

    public static MConstructor createConstructor(Hub hub, Location location, Location signLocation, Tracker<MConstructor> tracker) {
        MConstructor constructor = new MConstructor(hub, location, signLocation);
        tracker.addUnit(location, constructor);
        return constructor;
    }

    public static MMiner createMiner(Hub hub, Location location, Location signLocation, Tracker<MMiner> tracker) {
        MMiner miner = new MMiner(hub, location, signLocation);
        tracker.addUnit(location, miner);
        return miner;
    }

    public static MSmelter createSmelter(Hub hub, Location location, Location signLocation, Tracker<MSmelter> tracker) {
        MSmelter smelter = new MSmelter(hub, location, signLocation);
        tracker.addUnit(location, smelter);
        return smelter;
    }

    public static MConveyor createConveyor(Hub hub, Location location, Tracker<MConveyor> tracker) {
        MConveyor conveyor = new MConveyor(hub, location);
        tracker.addUnit(location, conveyor);
        return conveyor;
    }
}
