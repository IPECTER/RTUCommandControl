package com.github.ipecter.rtu.commandcontrol.managers;

import com.github.ipecter.rtu.commandcontrol.RTUCommandControl;
import com.github.ipecter.rtu.pluginlib.RTUPluginLib;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    public final static ConfigManager getInstance() {
        return ConfigManager.InnerInstanceClass.instance;
    }

    private static class InnerInstanceClass {
        private static final ConfigManager instance = new ConfigManager();
    }

    @Getter
    private final Map<String, List<String>> cmdList = new HashMap<>();
    private final Map<String, String> msgKeyMap = Collections.synchronizedMap(new HashMap<>());
    @Getter
    private boolean enablePlugin = true;
    @Getter
    private boolean motd = true;

    public void initConfigFiles() {
        initSetting(RTUPluginLib.getFileManager().copyResource("Setting.yml"));
        initMessage(RTUPluginLib.getFileManager().copyResource("Translations", "Locale_" + locale + ".yml"));
        initCommands(RTUPluginLib.getFileManager().copyResource("Commands.yml"));
    }
    @Getter
    private String locale = "EN";

    private void initSetting(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        enablePlugin = config.getBoolean("enablePlugin", enablePlugin);
        motd = config.getBoolean("motd", motd);
        locale = config.getString("locale", locale);
    }

    public String getTranslation(String key) {
        return msgKeyMap.getOrDefault(key, "");
    }

    private void initMessage(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        msgKeyMap.clear();
        for (String key : config.getKeys(false)) {
            if (key.equals("prefix")) {
                msgKeyMap.put(key, config.getString("prefix", "").isEmpty() ? MiniMessage.miniMessage().serialize(RTUCommandControl.prefix) : config.getString("prefix"));
            } else {
                msgKeyMap.put(key, config.getString(key));
            }
        }

        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_EN.yml");
        RTUPluginLib.getFileManager().copyResource("Translations", "Locale_KR.yml");
    }

    private void initCommands(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.getConfigurationSection("commands") != null) {
            cmdList.clear();
            for (String group : config.getConfigurationSection("commands").getKeys(false)) {
                cmdList.put(group, config.getConfigurationSection("commands").getStringList("." + group));
            }
        }
    }
}
