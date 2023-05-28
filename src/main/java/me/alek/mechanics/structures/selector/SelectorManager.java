package me.alek.mechanics.structures.selector;

import me.alek.EngineerCraft;
import me.alek.hub.HubManager;
import me.alek.mechanics.Unit;
import me.alek.utils.FacingUtils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SelectorManager {

    static {
        Bukkit.broadcastMessage("register");
        Bukkit.getPluginManager().registerEvents(new Listener(), EngineerCraft.getInstance());
    }

    private final static HashMap<Player, Selector<? extends Unit>> playerSelectorMap = new HashMap<>();

    public static void addSelector(Player player, Selector<? extends Unit> selector) {
        playerSelectorMap.put(player, selector);
    }

    public static void removeSelector(Player player) {
        playerSelectorMap.remove(player);
    }

    public static Selector<? extends Unit> getSelector(Player player) {
        return playerSelectorMap.get(player);
    }

    public static boolean hasSelector(Player player) {
        return playerSelectorMap.containsKey(player);
    }

    public static @NotNull ItemStack applySelectorTag(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        compound.set("selector", new NBTTagString("JHDUISAHDSAHSDAUHSASSAD"));

        nmsItem.setTag(compound);
        return CraftItemStack.asCraftMirror(nmsItem);
    }

    public static boolean hasSelectorTag(ItemStack item) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if (!nmsItem.hasTag()) return false;

        return nmsItem.getTag().getString("selector") != null;
    }

    public static class Listener implements org.bukkit.event.Listener {

        @EventHandler
        public <U extends Unit> void onInteract(PlayerInteractEvent event) {
            final Player player = event.getPlayer();
            if (!HubManager.hasHub(player) || !hasSelector(player) || event.getItem() == null || event.getItem().getType() == Material.AIR) {
                return;
            }
            final Selector<U> selector = (Selector<U>) getSelector(player);
            selector.showItems();
            event.setCancelled(true);
            if (event.getItem().getType() == Material.BARRIER) {
                selector.cancel();
            }
            if (!hasSelectorTag(event.getItem()) || selector.getProfile().getBlockType() != event.getItem().getType()) {
                return;
            }
            if (event.getClickedBlock() == null) {
                player.sendMessage("Du skal klikke på en block");
                return;
            }

            final BlockFace direction = FacingUtils.getFacingBlockFace((int) player.getLocation().getYaw());
            final Location location = event.getClickedBlock().getLocation().add(0, 1, 0);

            if (!selector.allowBuild(location, direction)) {
                player.sendMessage("Den her lokation er for tæt på en anden unit.");
                return;
            }
            final Unit unit = selector.create(location, direction);
            if (unit == null) {
                player.sendMessage("§cFejl opstået ved oprettelse af unit.");
                return;
            }
            player.sendMessage("Ny unit: " + unit.getProfile().getName() + ", " + unit.getLocation());
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        if (!HubManager.hasHub(player) || !SelectorManager.hasSelector(player)) {
            return;
        }
        event.setCancelled(true);
        SelectorManager.getSelector(player).showItems();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if (!HubManager.hasHub(player) || !SelectorManager.hasSelector(player)) {
            return;
        }
        event.setCancelled(true);
        SelectorManager.getSelector(player).showItems();
        player.closeInventory();
        player.updateInventory();
    }
}
