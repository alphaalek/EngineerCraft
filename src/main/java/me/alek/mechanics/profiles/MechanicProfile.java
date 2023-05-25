package me.alek.mechanics.profiles;

import me.alek.hub.Hub;
import me.alek.mechanics.Mechanic;
import me.alek.mechanics.Unit;
import me.alek.mechanics.tracker.Tracker;
import me.alek.utils.handshake.Handshake;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public interface MechanicProfile<M extends Mechanic> extends UnitProfile<M> {

    M createUnit(Hub hub, BlockFace direction, Location location, Location signLocation, Tracker<? extends Unit> tracker, int level, Handshake doneLoading);
}
