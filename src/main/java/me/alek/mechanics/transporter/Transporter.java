package me.alek.mechanics.transporter;

import me.alek.mechanics.Unit;
import org.bukkit.inventory.ItemStack;

public interface Transporter extends Unit {

    void in(ItemStack item);

    void out(ItemStack item);
}
