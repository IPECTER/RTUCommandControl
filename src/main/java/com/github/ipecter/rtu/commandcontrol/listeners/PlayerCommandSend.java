package com.github.ipecter.rtu.commandcontrol.listeners;

import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.*;

public class PlayerCommandSend implements Listener {

    private ConfigManager configManager = ConfigManager.getInstance();

    @EventHandler
    public void onSendCmd(PlayerCommandSendEvent e) {
        if (!configManager.isEnablePlugin()) return;
        Player player = e.getPlayer();
        if (player.hasPermission("rtucc.bypass.send")) return;

        e.getCommands().clear();

        Set<String> cmdListSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, List<String>> cmdListMap = ConfigManager.getInstance().getCmdList();
        for (String group : cmdListMap.keySet()) {
            List<String> cmdList = cmdListMap.get(group);
            if (player.hasPermission("rtucc." + group) && !cmdList.isEmpty()) {
                cmdListSet.addAll(cmdList);
            }
        }

        e.getCommands().addAll(cmdListSet);
    }

}
