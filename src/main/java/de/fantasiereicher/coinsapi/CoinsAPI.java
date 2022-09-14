package de.fantasiereicher.coinsapi;

import de.fantasiereicher.mysql.MySQL;
import de.fantasiereicher.mysql.MySQL_CoinsAPI;
import de.fantasiereicher.utils.CMD_coins;
import de.fantasiereicher.utils.Event_PlayerJoin;
import eu.syplex.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CoinsAPI extends JavaPlugin {
    public static final String prefix = "§e§lCoins §8| §7";

    private MySQL mysql;

    public void onEnable() {
        FileUtils file = new FileUtils();
        file.saveFile();
        this.mysql = new MySQL(file.getConfig().getString("MySQL.hostname"), file.getConfig().getString("MySQL.port"), file.getConfig().getString("MySQL.database"), file.getConfig().getString("MySQL.username"), file.getConfig().getString("MySQL.password"));
        this.mysql.connect();
        MySQL_CoinsAPI.createTable();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents((Listener)new Event_PlayerJoin(), (Plugin)this);
        getCommand("coins").setExecutor((CommandExecutor)new CMD_coins());
    }

    public void onDisable() {
        this.mysql.disconnect();
    }

    public MySQL getMysql() {
        return this.mysql;
    }
}
