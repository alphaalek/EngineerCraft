package me.alek.mechanics.tracker.entries;

import me.alek.EngineerCraft;
import me.alek.mechanics.Mechanic;
import me.alek.mechanics.WorkerMechanic;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;

public class MechanicTrackerEntry<M extends Mechanic> extends UnitTrackerEntry<M> {

    private final M mechanic;
    protected final Block signBlock;

    public MechanicTrackerEntry(M mechanic) {
        super(mechanic);
        this.signBlock = mechanic.getSignLocation().getWorld().getBlockAt(mechanic.getSignLocation());
        this.mechanic = mechanic;

        if (!(mechanic instanceof WorkerMechanic)) {
            initSign();
        }
    }

    @Override
    public void tick(Set<Player> players) {

    }

    public void initSign() {
        BlockState state = signBlock.getState();
        if (state instanceof Sign) {
            Sign sign = (Sign) state;

            sign.setLine(0, mechanic.getProfile().getName().toUpperCase());
            sign.setLine(3, "Tryk her");

            sign.update();
        }
    }

    @Override
    public M getUnit() {
        return mechanic;
    }
}
