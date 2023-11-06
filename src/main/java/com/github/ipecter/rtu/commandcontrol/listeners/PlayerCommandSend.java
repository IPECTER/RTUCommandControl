package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerCommandSend implements Listener {

    private final ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onSendCmd(PlayerCommandSendEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (player.hasPermission("rtucc.bypass.send")) return;

        e.getCommands().clear();

        Set<String> cmdListSet = new HashSet<>();
        Map<String, List<String>> cmdListMap = configManager.getCmdList();
        for (String group : cmdListMap.keySet()) {
            List<String> cmdList = cmdListMap.get(group);
            if (player.hasPermission("rtucc." + group) && !cmdList.isEmpty()) {
                cmdListSet.addAll(cmdList);
            }
        }

        e.getCommands().addAll(cmdListSet);
    }

}
