package club.rigox.bukkit;

import club.rigox.bukkit.utils.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import static club.rigox.bukkit.utils.Logger.info;

public final class PixelMOTD extends JavaPlugin {
    public static PixelMOTD instance;

    private Converter converter;

    private FileManager manager;

    private Placeholders placeholders;

    //private CommandUtils cmdUtils;

    private Motd motdUtils;

    private ServerIcon serverIcon;

    private FileConfiguration messagesConfig;
    private FileConfiguration dataConfig;
    private FileConfiguration motdConfig;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        info("&8------------------------------------------------");
        info("_____ _          _ __  __  ____ _______ _____");
        info("|  __ (_)        | |  \\/  |/ __ \\__   __|  __ \\");
        info("| |__) |__  _____| | \\  / | |  | | | |  | |  | |");
        info("|  ___/ \\ \\/ / _ \\ | |\\/| | |  | | | |  | |  | |");
        info("| |   | |>  <  __/ | |  | | |__| | | |  | |__| |");
        info("|_|   |_/_/\\_\\___|_|_|  |_|\\____/  |_|  |_____/");
        info("&8------------------------------------------------");
        instance = this;
        info("&8------------------------------------------------");
        info("&7Plugin has been &aenabled&7!");
        info(String.format("&7Using &6%s &7version.", getDescription().getVersion()));
        info("&8------------------------------------------------");
        manager      = new FileManager(this);
        serverIcon   = new ServerIcon(this);
    }

    @Override
    public void onDisable() {
        info("&8------------------------------------------------");
        info("&7Plugin has been &cdisabled&7!");
        info(String.format("&7Using &6%s &7version.", getDescription().getVersion()));
        info("&8------------------------------------------------");
    }

    public void loadConfigs() {
        messagesConfig      = manager.loadConfig("messages");
        dataConfig          = manager.loadConfig("data");
        motdConfig          = manager.loadConfig("motd");
        config              = manager.loadConfig("settings");

        createFolders();
    }

    public void createFolders() {
        manager.createFolders("normal-icons");
        manager.createFolders("whitelist-icons");
    }

    public void saveConfigs() {
        dataConfig  = manager.reloadConfig("data", dataConfig);
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMotdConfig() {
        return motdConfig;
    }

    //public Converter getConverter() {
    //    return converter;
    //}

    //public Placeholders getPlaceholders() {
    //    return placeholders;
    //}

    //public CommandUtils getCmdUtils() {
    //    return cmdUtils;
    //}

    //public Motd getMotdUtils() {
    //    return motdUtils;
    //}

    public ServerIcon getServerIcon() {
        return serverIcon;
    }

    @SuppressWarnings("unused")
    public PixelMOTD getInstance() { return instance; }
}
