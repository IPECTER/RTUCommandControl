package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.ConfigManager;
import com.github.ipecter.rtu.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Util util = Util.getInstance();
    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (configManager.isMotd()) {
            player.sendMessage(configManager.getPrefix() + util.formattedStr(player, "&fRTU CommandControl developed by IPECTER"));
        } else {
            if (player.isOp())
                player.sendMessage(configManager.getPrefix() + util.formattedStr(player, "&fRTU CommandControl developed by IPECTER"));
        }
    }
}

