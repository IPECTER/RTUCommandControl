package com.github.ipecter.rtu.commandcontrol.commands;

import com.github.ipecter.rtu.commandcontrol.ConfigManager;
import com.github.ipecter.rtu.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ReloadCommand implements CommandExecutor, TabCompleter {

    private Util util = Util.getInstance();
    private ConfigManager configManager = ConfigManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rtucc.reload")) {
                configManager.initConfigFiles();
                sender.sendMessage(util.formattedStr(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getReloadMsg()));
                sync();
            } else {
                sender.sendMessage(util.formattedStr(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getNoPermission()));
            }
            return true;
        } else {
            sender.sendMessage(util.formattedStr(sender instanceof Player ? (Player) sender : null, configManager.getPrefix() + configManager.getCommandWrongUsage()));
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1 && sender.hasPermission("rtucc.reload")) {
            return Arrays.asList("reload");
        }
        return Arrays.asList();
    }

    private void sync() {
        for (Player p : Bukkit.getOnlinePlayers()) p.updateCommands();
    }
}
