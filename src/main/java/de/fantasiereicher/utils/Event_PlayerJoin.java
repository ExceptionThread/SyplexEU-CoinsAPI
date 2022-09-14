package de.fantasiereicher.utils;

import de.fantasiereicher.mysql.MySQL_CoinsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event_PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!MySQL_CoinsAPI.isRegistered(p)) {
            MySQL_CoinsAPI.register(p);
            MySQL_CoinsAPI.setCoins(p, 500);

        }

    }
}
