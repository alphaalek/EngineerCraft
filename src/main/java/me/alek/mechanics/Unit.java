package me.alek.mechanics;

import me.alek.hub.Hub;
import me.alek.mechanics.profiles.UnitProfile;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Unit {

    Hub getHub();

    Location getLocation();

    UnitProfile<? extends Unit> getProfile();
}
