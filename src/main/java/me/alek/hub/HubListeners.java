package me.alek.hub;

import me.alek.exceptions.NoSuchHub;
import me.alek.mechanics.structures.selector.SelectorManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HubListeners implements Listener {

    private Hub getHub(Player player) {
        if (!HubManager.hasHub(player)) {
            return null;
        }
        return HubManager.getHub(player);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Hub hub = getHub(event.getPlayer());
        if (hub != null) {
            hub.addOnlinePlayer(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Hub hub = getHub(player);
        if (hub != null) {
            hub.removeOnlinePlayer(player);
        }
        if (SelectorManager.hasSelector(player)) {
            SelectorManager.removeSelector(player);
        }
    }
}
