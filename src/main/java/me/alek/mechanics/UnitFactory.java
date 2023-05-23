package me.alek.mechanics;

import me.alek.exceptions.NoSuchProfile;
import me.alek.hub.Hub;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.types.MConstructor;
import me.alek.mechanics.types.MConveyor;
import me.alek.mechanics.types.MMiner;
import me.alek.mechanics.types.MSmelter;
import org.bukkit.Location;

public class UnitFactory {

    public static <U extends Unit> Unit createUnit(Hub hub, Location location, UnitProfile<U> profile, UnitTracker<U> tracker) {
        U unit = profile.createUnit(hub, location, tracker);
        tracker.addUnit(location, unit);
        return unit;
    }

    public static MConstructor createConstructor(Hub hub, Location location, UnitTracker<MConstructor> tracker) {
        MConstructor constructor = new MConstructor(hub, location);
        tracker.addUnit(location, constructor);
        return constructor;
    }

    public static MMiner createMiner(Hub hub, Location location, UnitTracker<MMiner> tracker) {
        MMiner miner = new MMiner(hub, location);
        tracker.addUnit(location, miner);
        return miner;
    }

    public static MSmelter createSmelter(Hub hub, Location location, UnitTracker<MSmelter> tracker) {
        MSmelter smelter = new MSmelter(hub, location);
        tracker.addUnit(location, smelter);
        return smelter;
    }

    public static MConveyor createConveyor(Hub hub, Location location, UnitTracker<MConveyor> tracker) {
        MConveyor conveyor = new MConveyor(hub, location);
        tracker.addUnit(location, conveyor);
        return conveyor;
    }
}
