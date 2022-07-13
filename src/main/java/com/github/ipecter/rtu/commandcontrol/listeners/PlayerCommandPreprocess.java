package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.ConfigManager;
import com.github.ipecter.rtu.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.*;

public class PlayerCommandPreprocess implements Listener {

    private Util util = Util.getInstance();
    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onPreprocessCmd(PlayerCommandPreprocessEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (player.hasPermission("rtucc.bypass.process")) return;

        String cmd = e.getMessage().replaceFirst("/", "").split(" ")[0];
        Set<String> cmdListSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, List<String>> cmdListMap = ConfigManager.getInstance().getCmdList();
        for (String group : cmdListMap.keySet()) {
            List<String> cmdList = cmdListMap.get(group);
            if (player.hasPermission("rtucc." + group) && !cmdList.isEmpty()) {
                cmdListSet.addAll(cmdList);
            }
        }
        if (!cmdListSet.contains(cmd)) {
            e.setCancelled(true);
            player.sendMessage(util.formattedStr(player, configManager.getPrefix() + configManager.getNoPermission()));
        }
    }

}
