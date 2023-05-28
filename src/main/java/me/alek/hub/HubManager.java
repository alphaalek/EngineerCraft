package me.alek.hub;

import me.alek.EngineerCraft;
import me.alek.exceptions.NoSuchHub;
import me.alek.hub.Hub;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class HubManager {

    private static final HashMap<Integer, Hub> hubs = new HashMap<>();
    private static final HashMap<UUID, Integer> members = new HashMap<>();

    public static Hub getHubById(int id) throws NoSuchHub {
        if (!hubs.containsKey(id)) {
            throw new NoSuchHub();
        }
        return hubs.get(id);
    }

    public static boolean hasHub(UUID uuid) {
        return members.containsKey(uuid);
    }

    public static boolean hasHub(Player player) {
        return hasHub(player.getUniqueId());
    }

    public static Hub getHub(UUID uuid) {
        final int id = members.get(uuid);
        try {
            return getHubById(id);
        } catch (NoSuchHub ex) {
            return null;
        }
    }

    public static Hub getHub(Player player) {
        return getHub(player.getUniqueId());
    }

    public static void loadHubs() {
        for (final Hub hub : EngineerCraft.getStorageAdapter().loadHubs()) {
            hubs.put(hub.getId(), hub);
            for (UUID member : hub.getMembers()) {
                members.put(member, hub.getId());
            }
        }
    }

    private static int generateId() {
        return new Random().nextInt(1000000);
    }

    private static int createUniqueId() {
        int id;
        while (true) {
            if (!hubs.containsKey(id = generateId())) {
                break;
            }
        }
        return id;
    }

    public static Hub createHub(UUID uuid) {
        if (hasHub(uuid)) {
            return getHub(uuid);
        }
        final Hub hub = new Hub(createUniqueId(), uuid);
        hubs.put(hub.getId(), hub);
        members.put(uuid, hub.getId());
        return hub;
    }
}
