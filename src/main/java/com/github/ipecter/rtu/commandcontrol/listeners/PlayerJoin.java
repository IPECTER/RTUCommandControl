package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final ConfigManager configManager = ConfigManager.getInstance();
    private final TextManager textManager = RTUPluginLib.getTextManager();
    private final String motd = "<white>RTU CommandControl developed by IPECTER<white>";

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        Audience audience = RTUPluginLib.adventure().player(player);
        if (configManager.isMotd()) {
            audience.sendMessage(textManager.formatted(player, configManager.getTranslation("prefix") + motd));
        } else {
            if (player.isOp())
                audience.sendMessage(textManager.formatted(player, configManager.getTranslation("prefix") + motd));
        }
    }
}

