package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.UnitProfile;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public interface Unit {

    Hub getHub();

    Location getLocation();

    BlockFace getDirection();

    UnitProfile<? extends Unit> getProfile();
}
