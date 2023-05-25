package me.alek.mechanics.tracker.entries;

import me.alek.mechanics.WorkerMechanic;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Set;

public class WorkerMechanicTrackerEntry<WM extends WorkerMechanic> extends MechanicTrackerEntry<WM> {

    private final WM workerMechanic;

    public WorkerMechanicTrackerEntry(WM workerMechanic) {
        super(workerMechanic);
        updateSign();
        this.workerMechanic = workerMechanic;
    }

    @Override
    public void tick(Set<Player> players) {
        if (getCounter() > 10) {
            resetCounter();
            updateSign();
        }
        super.tick(players);
    }

    public void updateSign() {
        Block block = workerMechanic.getSignLocation().getWorld().getBlockAt(workerMechanic.getSignLocation());
        BlockState state = block.getState();
        if (state instanceof Sign) {
            Sign sign = (Sign) state;
            sign.setLine(1, "[Lvl. " + workerMechanic.getLevel() + "]");
            sign.update();
        }
    }

    @Override
    public WM getUnit() {
        return workerMechanic;
    }
}
