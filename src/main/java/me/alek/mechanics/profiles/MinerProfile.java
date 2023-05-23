package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.UnitTracker;
import me.alek.mechanics.types.MMiner;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MinerProfile implements WorkerMechanicProfile<MMiner> {

    @Override
    public String getName() {
        return "Miner";
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public boolean isWorker() {
        return true;
    }

    @Override
    public boolean isMechanic() {
        return true;
    }

    @Override
    public MMiner createUnit(Hub hub, Location location, UnitTracker<MMiner> tracker) {
        return UnitFactory.createMiner(hub, location, tracker);
    }

}
