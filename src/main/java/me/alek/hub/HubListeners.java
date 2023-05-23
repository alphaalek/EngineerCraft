package me.alek.hub;

import me.alek.exceptions.NoSuchHub;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HubListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (!HubManager.hasHub(player)) {
            return;
        }
        try {
            final Hub hub = HubManager.getHub(player);
            hub.addOnlinePlayer(player);
        } catch (NoSuchHub ex) {
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (!HubManager.hasHub(player)) {
            return;
        }
        try {
            final Hub hub = HubManager.getHub(player);
            hub.removeOnlinePlayer(player);
        } catch (NoSuchHub ex) {
        }
    }
}
