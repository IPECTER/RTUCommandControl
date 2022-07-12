package com.github.ipecter.rtu.util;

import com.google.common.io.ByteStreams;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {

    private boolean usePAPI = false;

    public Util() {
    }

    public final static Util getInstance() {
        return Util.InnerInstanceClass.instance;
    }

    public boolean isUsePAPI() {
        return usePAPI;
    }

    public void setUsePAPI(boolean usePAPI) {
        this.usePAPI = usePAPI;
    }

    public String formattedStr(Player player, String msg) {
        return usePAPI ? coloredStr(PlaceholderAPI.setPlaceholders(player, msg)) : coloredStr(msg);
    }

    public String coloredStr(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public File copyResource(Plugin plugin, String sourceFile) {
        File targetFoler = getResourceFolder(plugin.getDataFolder());
        File resultFile = new File(targetFoler, sourceFile);
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
                InputStream in = plugin.getResource(sourceFile);
                OutputStream out = new FileOutputStream(resultFile);
                ByteStreams.copy(in, out);
                out.close();
                in.close();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Error copying content " + sourceFile);
                e.printStackTrace();
            }
        }
        return resultFile;
    }

    public File copyResource(Plugin plugin, String sourceFolder, String sourceFile) {
        File targetFoler = getResourceFolder(plugin.getDataFolder() + "/" + sourceFolder);
        File resultFile = new File(targetFoler, sourceFile);
        if (!resultFile.exists()) {
            try {
                resultFile.createNewFile();
                InputStream in = plugin.getResource(sourceFolder + "/" + sourceFile);
                OutputStream out = new FileOutputStream(resultFile);
                ByteStreams.copy(in, out);
                out.close();
                in.close();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Error copying file " + sourceFolder + "/" + sourceFile);
                e.printStackTrace();
            }
        }
        return resultFile;
    }

    public File getResourceWithoutNew(String folder, String file) {
        File resource = new File(folder, file);
        if (!resource.exists()) {
            return null;
        } else {
            return resource;
        }
    }

    public File getResource(String folder, String file) {
        File resourceFolder = new File(folder);
        if (!resourceFolder.exists()) {
            try {
                resourceFolder.mkdir();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating folder " + folder);
                e.printStackTrace();
            }
        }
        File resourceFile = new File(folder, file);
        if (!resourceFile.exists()) {
            try {
                resourceFile.createNewFile();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating file " + file);
                e.printStackTrace();
            }
        }
        return resourceFile;
    }

    public File getResourceFolder(String folder) {
        File resourceFolder = new File(folder);
        if (!resourceFolder.exists()) {
            try {
                resourceFolder.mkdir();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating folder " + folder);
                e.printStackTrace();
            }
        }
        return resourceFolder;
    }

    public File getResourceFolder(File folder) {
        File resourceFolder = folder;
        if (!resourceFolder.exists()) {
            try {
                resourceFolder.mkdir();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating folder " + folder);
                e.printStackTrace();
            }
        }
        return resourceFolder;
    }

    public File getResource(File folder, String file) {
        if (!folder.exists()) {
            try {
                folder.mkdir();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating folder " + folder);
                e.printStackTrace();
            }
        }
        File resourceFile = new File(folder, file);
        if (!resourceFile.exists()) {
            try {
                resourceFile.createNewFile();

            } catch (Exception e) {
                Bukkit.getLogger().severe("Error creating file " + file);
                e.printStackTrace();
            }
        }
        return resourceFile;
    }

    private static class InnerInstanceClass {
        private static final Util instance = new Util();
    }


}
