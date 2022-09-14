package de.fantasiereicher.utils;

import de.fantasiereicher.coinsapi.*;
import de.fantasiereicher.mysql.MySQL_CoinsAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_coins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 0) {
                p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix + "Du besitzt derzeit §e" + MySQL_CoinsAPI.getCoins(p) + " §7Coins");
            } else if (args.length == 1) {
                if (p.hasPermission("system.sup")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (MySQL_CoinsAPI.isRegistered(target.getName())) {
                        p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix + target.getName() + MySQL_CoinsAPI.getCoinsUUID(target.getUniqueId().toString()) + "Coins");
                    } else {
                        p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix + "§c" +  target.getName() + " wurde nicht gefunden!");
                        return false;
                    }
                }
            } else if (args.length == 3 &&
                    p.hasPermission("system.*")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                try {
                    int toAdd = Integer.parseInt(args[2]);
                    if (args[1].equalsIgnoreCase("set")) {
                        MySQL_CoinsAPI.setCoins(target.getPlayer(), toAdd);
                        p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix + target.getName() + " besitzt nun §e" + MySQL_CoinsAPI.getCoins((Player)target) + " §7Coins");
                    } else if (args[1].equalsIgnoreCase("add")) {
                        MySQL_CoinsAPI.addCoins(target.getPlayer(), toAdd);
                        p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix +  target.getName() + " hat nun §e" + toAdd + " §7Coins hinzugefügt §8(§e" + MySQL_CoinsAPI.getCoins((Player)target) + "§8)");
                    } else if (args[1].equalsIgnoreCase("remove")) {
                        MySQL_CoinsAPI.removeCoins(target.getPlayer(), toAdd);
                        p.sendMessage(de.fantasiereicher.coinsapi.CoinsAPI.prefix + target.getName() + " hat nun §e" + toAdd + " §7Coins abgezogen §8(§e" + MySQL_CoinsAPI.getCoins((Player)target) + "§8)");
                    }
                } catch (NumberFormatException ex) {
                    p.sendMessage("Du musst eine Zahl angeben!");
                    return false;
                }
            }
        }
        return false;
    }
}
