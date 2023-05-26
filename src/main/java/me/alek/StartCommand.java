package me.alek;

import me.alek.exceptions.NoSuchHub;
import me.alek.exceptions.NoSuchProfile;
import me.alek.hub.Hub;
import me.alek.hub.HubManager;
import me.alek.mechanics.Unit;
import me.alek.mechanics.UnitLibrary;
import me.alek.mechanics.profiles.UnitProfile;
import me.alek.mechanics.structures.selector.Selector;
import me.alek.mechanics.structures.selector.SelectorManager;
import me.alek.utils.FacingUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }
        final Player player = (Player) sender;
        if (SelectorManager.hasSelector(player)) {
            sender.sendMessage("Du er allerede igang med at vælge en placering til en unit.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("Skriv hvilken unit du vil lave");
            return true;
        }

        if (!HubManager.hasHub(player)) {
            HubManager.createHub(player.getUniqueId());
        }

        UnitProfile<? extends Unit> profile;
        try {
            profile = UnitLibrary.getProfileByName(args[0]);
        } catch (NoSuchProfile ex) {
            sender.sendMessage("Ingen unit profile eksisterer med navnet " + args[0]);
            return true;
        }
        new Selector<>(player, profile);
        return true;
    }
}
