package me.alek;

import me.alek.exceptions.AlreadyExistingUnit;
import me.alek.exceptions.NoSuchHub;
import me.alek.exceptions.NoSuchProfile;
import me.alek.hub.Hub;
import me.alek.hub.HubManager;
import me.alek.mechanics.Unit;
import me.alek.utils.FacingUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Skriv hvilken unit du vil lave");
            return true;
        }
        final Player player = (Player) sender;
        final Location location = player.getLocation().add(FacingUtils.getFacingVector((int) player.getLocation().getYaw()).multiply(2));

        Unit unit;
        Hub hub;
        try {
            hub = HubManager.getHub(player);
        } catch (NoSuchHub ex) {
            hub = HubManager.createHub(player.getUniqueId());
            sender.sendMessage("Ny hub: " + hub.getId() + ", " + hub.getMembers());
        }
        try {
            unit = hub.createUnit(location, args[0], true);
        } catch (AlreadyExistingUnit ex) {
            sender.sendMessage("Der eksisterer allerede en unit på den lokation!");
            return true;
        } catch (NoSuchProfile ex) {
            sender.sendMessage("Ingen unit profile eksisterer med navnet " + args[0]);
            return true;
        }
        sender.sendMessage("Ny unit: " + unit.getHub().getOwner() + ", " + unit.getLocation());
        return true;
    }
}
