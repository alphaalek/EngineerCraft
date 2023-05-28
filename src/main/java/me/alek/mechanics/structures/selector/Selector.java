package me.alek.mechanics.structures.selector;

import me.alek.exceptions.NoSuchHub;
import me.alek.hub.Hub;
import me.alek.hub.HubManager;
import me.alek.mechanics.Unit;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Selector<U extends Unit> {

    private final Player player;
    private final UnitProfile<U> profile;

    private Hub hub;

    public Selector(Player player, UnitProfile<U> profile) {
        this.player = player;
        this.profile = profile;

        this.hub = HubManager.getHub(player);

        SelectorManager.addSelector(player, this);
        showItems();
    }

    public Player getPlayer() {
        return player;
    }

    public UnitProfile<U> getProfile() {
        return profile;
    }

    public boolean allowBuild(Location location, BlockFace direction) {
        return hub.allowBuild(location, direction, profile);
    }

    public Unit create(Location location, BlockFace direction) {
        cancel();
        return hub.createUnit(location, direction, profile, true);
    }

    public void cancel() {
        SelectorManager.removeSelector(player);
        player.getInventory().clear();
    }

    public void showItems() {
        final ItemStack item1 = new ItemBuilder(profile.getBlockType())
                .displayname(profile.getName())
                .build();
        final ItemStack item2 = new ItemBuilder(Material.BARRIER)
                .displayname("§cAnnulér")
                .build();

        player.getInventory().setItem(0, SelectorManager.applySelectorTag(item1));
        player.getInventory().setItem(1, SelectorManager.applySelectorTag(item2));

        for (int i = 2; i <= 8; i++) {
            player.getInventory().setItem(i, null);
        }
    }
}
