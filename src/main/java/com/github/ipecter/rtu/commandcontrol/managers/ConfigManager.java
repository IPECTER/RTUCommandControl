package com.github.ipecter.rtu.commandcontrol.managers;

import com.github.ipecter.rtu.commandcontrol.RTUCommandControl;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    public ConfigManager() {
    }

    public final static ConfigManager getInstance() {
        return ConfigManager.InnerInstanceClass.instance;
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }

    private Plugin plugin = RTUCommandControl.getPlugin(RTUCommandControl.class);
    private boolean enablePlugin = true;
    private boolean motd = true;
    private String locale = "EN";
    private Map<String, List<String>> cmdList = Collections.synchronizedMap(new HashMap<>());
    private String prefix = IridiumColorAPI.process("<GRADIENT:becc1f>[ RTUCommandControl ]</GRADIENT:a3a3a3> ");

    public boolean isEnablePlugin() {
        return enablePlugin;
    }

    public void setEnablePlugin(boolean enablePlugin) {
        this.enablePlugin = enablePlugin;
    }

    public boolean isMotd() {
        return motd;
    }

    public void setMotd(boolean motd) {
        this.motd = motd;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, List<String>> getCmdList() {
        return cmdList;
    }

    public void setCmdList(Map<String, List<String>> cmdList) {
        this.cmdList = cmdList;
    }

    public void initConfigFiles() {
        initSetting(RTUPluginLib.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUPluginLib.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initCommands(RTUPluginLib.getFileManager().copyResource("Commands.yml"));
    }

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin");
        motd = config.getBoolean("motd");
        locale = config.getString("locale");
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            if (key.equals("prefix")) {
                msgKeyMap.put(key, config.getString("prefix", "").isEmpty() ? prefix : config.getString("prefix"));
            } else {
                msgKeyMap.put(key, config.getString(key));
            }
        }

        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initCommands(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String group : config.getConfigurationSection("commands").getKeys(false)) {
            cmdList.put(group, config.getConfigurationSection("commands").getStringList("." + group));
        }
    }

    private Map<String, String> msgKeyMap = Collections.synchronizedMap(new HashMap<>());

    public String getTranslation(String key) {
        return msgKeyMap.getOrDefault(key, "");
    }

}
