package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.Mechanic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

public class MechanicTrackerEntry<M extends Mechanic> extends UnitTrackerEntry<M> {

    private final M mechanic;
    private int local;

    public MechanicTrackerEntry(M mechanic) {
        super(mechanic);
        this.mechanic = mechanic;
    }

    @Override
    public void tick(Set<Player> players) {
        if (getCounter() > 10) {
            local++;
            updateSign();
            resetCounter();
        }
    }

    public void updateSign() {
        Block block = mechanic.getSignLocation().getWorld().getBlockAt(mechanic.getSignLocation());
        BlockState state = block.getState();
        if (state instanceof Sign) {
            Sign sign = (Sign) state;
            sign.setLine(0, mechanic.getProfile().getName());
            sign.setLine(1, local + "");
            sign.update();
        }
    }

    @Override
    public M getUnit() {
        return mechanic;
    }
}
