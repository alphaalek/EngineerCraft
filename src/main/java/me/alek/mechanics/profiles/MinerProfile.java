package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitFactory;
import me.alek.mechanics.structures.Structure;
import me.alek.mechanics.structures.Structures;
import me.alek.mechanics.tracker.Tracker;
import me.alek.mechanics.types.Miner;
import me.alek.utils.Handshake;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class MinerProfile implements MechanicProfile<Miner> {

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
    public Structure getStructure() {
        return Structures.MINER.getStructure();
    }

    @Override
    public Material getBlockType() {
        return Material.HOPPER;
    }


}
