package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerCommandPreprocess implements Listener {

    private final ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPreprocessCmd(PlayerCommandPreprocessEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (player.hasPermission("rtucc.bypass.process")) return;

        String cmd = e.getMessage().replaceFirst("/", "").split(" ")[0];
        Set<String> cmdListSet = new HashSet<>();
        Map<String, List<String>> cmdListMap = configManager.getCmdList();
        for (String group : cmdListMap.keySet()) {
            List<String> cmdList = cmdListMap.get(group);
            if (player.hasPermission("rtucc." + group) && !cmdList.isEmpty()) {
                cmdListSet.addAll(cmdList);
            }
        }
        if (!cmdListSet.contains(cmd)) {
            e.setCancelled(true);
            Audience audience = RTUPluginLib.adventure().player(player);
            audience.sendMessage(RTUPluginLib.getTextManager().formatted(player, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
        }
    }

}
