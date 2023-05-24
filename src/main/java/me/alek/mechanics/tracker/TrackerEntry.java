package me.alek.mechanics.tracker;

import me.alek.mechanics.Unit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public interface TrackerEntry<U extends Unit> {

    void count();

    int getCounter();

    void resetCounter();

    void tick(Set<Player> players);

    U getUnit();
}
