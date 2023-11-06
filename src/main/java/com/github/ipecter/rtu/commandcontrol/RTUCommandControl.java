package com.github.ipecter.rtu.commandcontrol;

import com.github.ipecter.rtu.commandcontrol.commands.Command;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandPreprocess;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandSend;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerJoin;
import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.github.ipecter.rtu.pluginlib.managers.TextManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUCommandControl extends JavaPlugin {

    public static final Component prefix = RTUPluginLib.getTextManager().colored("<gradient:#becc1f:#a3a3a3>[ RTUCommandControl ]</gradient> ");

    private final TextManager textManager = RTUPluginLib.getTextManager();
    private final Audience console = RTUPluginLib.adventure().sender(Bukkit.getConsoleSender());

    @Override
    public void onEnable() {
        RTUPluginLib.init(this);
        console.sendMessage(prefix.append(textManager.colored("<green>Enable</green>")));
        ConfigManager.getInstance().initConfigFiles();
        registerEvent();
        setExecutor();
        loadDependencies();
    }

    @Override
    public void onDisable() {
        console.sendMessage(prefix.append(textManager.colored("<red>Disble</red>")));
    }

    private void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandSend(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    private void setExecutor() {
        getCommand("rtucc").setExecutor(new Command());
    }

    private void loadDependencies() {
        loadPAPI();
    }

    private void loadPAPI() {
        RTUPluginLib.hookDependency("PlaceholderAPI", Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"));
    }
}
