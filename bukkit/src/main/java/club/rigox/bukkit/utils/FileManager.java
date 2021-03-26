package club.rigox.bukkit.utils;

import club.rigox.bukkit.PixelMOTD;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileManager {
    private final PixelMOTD plugin;

    public FileManager (PixelMOTD plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a config File if it doesn't exists,
     * reloads if specified file exists.
     *
     * @param configName config to create/reload.
     */
    public FileConfiguration loadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder().getPath(), configName + ".yml");

        if (!configFile.exists()) {
            saveConfig(configName);
        }

        FileConfiguration cnf = null;
        try {
            cnf = YamlConfiguration.loadConfiguration(configFile);
        } catch (Exception e) {
            plugin.getLogs().warn(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }

        plugin.getLogs().info(String.format("&7File &e%s.yml &7has been loaded", configName));
        return cnf;
    }

    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file = new File(folderDir, configName + ".yml");

        if (!folderDir.exists()) {
            folderDir.mkdir();
        }

        if (!file.exists()) {
            try (InputStream in = plugin.getResource("sp-" + configName + ".yml")) {
                assert in != null;
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                plugin.getLogs().warn(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", configName, e));
                e.printStackTrace();
            }
        }
    }

    public FileConfiguration reloadConfig(String file, FileConfiguration configuration) {

        File path = new File(plugin.getDataFolder(), file + ".yml");
        try {
            configuration.save(path);

            return YamlConfiguration.loadConfiguration(
                    new File(plugin.getDataFolder(), file + ".yml"));
        } catch (IOException e) {
            plugin.getLogs().warn(String.format("A error occurred while copying the config %s.yml to the plugin data folder. Error: %s", file, e));
            e.printStackTrace();
        }

        return null;
    }

    public void createFolders(String path) {
        File folderDir = new File(plugin.getDataFolder(), path);

        if (!folderDir.exists()) {
            folderDir.mkdir();
        }
    }

    @SuppressWarnings("unused")
    public static String getMessageString(String path) {
        return PixelMOTD.instance.getMessagesConfig().getString(path);
    }
}
