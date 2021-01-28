package club.rigox.bungee.utils;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static club.rigox.bungee.utils.Logger.*;

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
    public Configuration loadConfig(String configName) {
        File configFile = new File(plugin.getDataFolder().getPath(), configName + ".yml");

        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            debug(configName + ".yml config was created!");
            saveConfig(configName);
        }

        Configuration cnf = null;
        try {
            cnf = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            error(String.format("A error occurred while loading the settings file. Error: %s", e));
            e.printStackTrace();
        }
        return cnf;
    }


    public void saveConfig(String configName) {
        File folderDir = plugin.getDataFolder();
        File file      = new File(plugin.getDataFolder(), configName + ".yml");

        if (!folderDir.exists()) {
            folderDir.mkdir();
        }

        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream(configName + ".yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                warn(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", configName, e));
                e.printStackTrace();
            }
        }
    }

    public void reloadConfig(ConfigType type) throws IOException {
        switch (type) {
            case SETTINGS:
                File settings = new File(plugin.getDataFolder(), "settings.yml");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.settingsFile, settings);
            case WHITELIST_MOTD:
                File whitelistMotd = new File(plugin.getDataFolder(), "whitelist-motd.yml");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.whitelistMotdFile, whitelistMotd);
            case NORMAL_MOTD:
                File normalMotd = new File(plugin.getDataFolder(), "normal-motd.yml");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.normalMotdFile, normalMotd);
            case EDITABLE:
                File editable = new File(plugin.getDataFolder(), "edit.yml");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(plugin.editFile, editable);
            default:
                error("Something went wrong. Please notify it to the plugin author.");
        }

//        commandFile         = manager.loadConfig("command");
//        editFile            = manager.loadConfig("edit");
//        modulesFile         = manager.loadConfig("modules");
//        normalMotdFile      = manager.loadConfig("normal-motd");
//        settingsFile        = manager.loadConfig("settings");
//        timerMotdFile       = manager.loadConfig("timer-motd");
//        whitelistMotdFile   = manager.loadConfig("whitelist-motd");

    }
}
