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
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (configManager.isMotd()) {
            player.sendMessage(util.formattedStr(player, configManager.getPrefix() + util.formattedStr(player, "&fRTU CommandControl developed by IPECTER")));
        } else {
            if (player.isOp())
                player.sendMessage(util.formattedStr(player, configManager.getPrefix() + util.formattedStr(player, "&fRTU CommandControl developed by IPECTER")));
        }
    }
}

