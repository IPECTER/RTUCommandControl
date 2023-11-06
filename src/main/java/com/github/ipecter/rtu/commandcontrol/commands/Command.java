package com.github.ipecter.rtu.commandcontrol.commands;

import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.CmdManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    private final ConfigManager configManager = ConfigManager.getInstance();
    private final TextManager textManager = RTUPluginLib.getTextManager();

    public Command() {
        Bukkit.getCommandAliases().
    }

    @Override
    public boolean onCommand(@Nullable CommandSender sender, @Nullable org.bukkit.command.Command command, @Nullable String label, String[] args) {
        CmdManager cmd = new CmdManager(sender, args);
        Audience audience = cmd.getAudience();
        switch (cmd.args(0)) {
            case "reload": {
                if (cmd.permission("rtucc.reload")) {
                    configManager.initConfigFiles();
                    sync();
                    audience.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("reloadMsg")));
                } else {
                    audience.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("noPermission")));
                }
                return true;
            }
            default: {
                audience.sendMessage(textManager.formatted(sender instanceof Player ? (Player) sender : null, configManager.getTranslation("prefix") + configManager.getTranslation("commandWrongUsage")));
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        CmdManager cmd = new CmdManager(sender, args);
        if (cmd.permission("rtucc.reload")) cmd.tab(0, "reload");
        return cmd.tabList();
    }

    private void sync() {
        for (Player p : Bukkit.getOnlinePlayers()) p.updateCommands();
    }
}
