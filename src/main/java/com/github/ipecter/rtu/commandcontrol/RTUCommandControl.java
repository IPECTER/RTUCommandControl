package com.github.ipecter.rtu.commandcontrol;

import com.github.ipecter.rtu.commandcontrol.commands.Command;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandPreprocess;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandSend;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerJoin;
import com.github.ipecter.rtu.commandcontrol.managers.ConfigManager;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUCommandControl extends JavaPlugin {

    private String prefix = IridiumColorAPI.process("<GRADIENT:becc1f>[ RTUCommandControl ]</GRADIENT:a3a3a3> ");

    @Override
    public void onEnable() {
        try {
            RTUPluginLib.init(this);
            Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&aEnable&f!"));
            ConfigManager.getInstance().initConfigFiles();
            registerEvent();
            setExecutor();
            loadDependencies();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(RTUPluginLib.getTextManager().formatted(prefix + "&cDisable&f!"));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandSend(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    protected void setExecutor() {
        getCommand("rtucc").setExecutor(new Command());
    }

    private void loadDependencies() {
        loadPAPI();
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            RTUPluginLib.getDependencyManager().setUsePAPI(true);
        }
    }
}
