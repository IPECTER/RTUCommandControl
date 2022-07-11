package com.github.ipecter.rtu.commandcontrol;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RTUCommandControl extends JavaPlugin {

    public static final String prefix = IridiumColorAPI.process("<GRADIENT:fcf003>[ RTUCommandControl ]</GRADIENT:a3a3a3>");

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', prefix + " &aEnable&f!"));
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', prefix + " &cDisable&f!"));
    }
}
