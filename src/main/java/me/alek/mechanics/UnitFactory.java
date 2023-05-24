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
import org.bukkit.Location;

public class UnitFactory {

    public static Unit createUnit(Hub hub, Location location, UnitProfile<? extends Unit> profile, Tracker<? extends Unit> tracker) {
        Location signLocation = null;
        final IStructure structure = profile.getStructure();
        if (profile.getStructure() instanceof IMechanicStructure) {
            final IMechanicStructure mechanicStructure = (IMechanicStructure) structure;
            signLocation = location.clone().add(mechanicStructure.getSign().getVector());
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
