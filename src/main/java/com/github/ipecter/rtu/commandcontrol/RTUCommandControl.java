package com.github.ipecter.rtu.commandcontrol;

import com.github.ipecter.rtu.commandcontrol.commands.ReloadCommand;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandPreprocess;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerCommandSend;
import com.github.ipecter.rtu.commandcontrol.listeners.PlayerJoin;
import com.github.ipecter.rtu.util.Util;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUCommandControl extends JavaPlugin {

    private String prefix = IridiumColorAPI.process("<GRADIENT:fcf003>[ RTUCommandControl ]</GRADIENT:a3a3a3> ");

    @Override
    public void onEnable() {
        try {
            Bukkit.getLogger().info(Util.getInstance().coloredStr(prefix + "&aEnable&f!"));
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
        Bukkit.getLogger().info(Util.getInstance().coloredStr(prefix + "&cDisable&f!"));
    }

    protected void registerEvent() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandSend(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocess(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    protected void setExecutor() {
        getCommand("rtucc").setExecutor(new ReloadCommand());
    }

    private void loadDependencies() {
        loadPAPI();
    }

    private void loadPAPI() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Util.getInstance().setUsePAPI(true);
        }
    }
}
