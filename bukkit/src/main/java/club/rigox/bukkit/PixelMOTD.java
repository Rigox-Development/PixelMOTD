package club.rigox.bukkit;

import club.rigox.bukkit.enums.NMSenum;
import club.rigox.bukkit.nms.NMS;
import club.rigox.bukkit.utils.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PixelMOTD extends JavaPlugin {
    public static PixelMOTD instance;

    private Converter converter;

    private FileManager manager;

    private Placeholders placeholders;

    private NMS nmsHandler;

    //private CommandUtils cmdUtils;
    private ReflectionManager reflectionManager;

    private Motd motdUtils;

    private Logger logs;

    private ServerIcon serverIcon;

    private FileConfiguration messagesConfig;
    private FileConfiguration dataConfig;
    private FileConfiguration motdConfig;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        logs = new Logger(this);
        logs.info("&8------------------------------------------------");
        logs.info("_____ _          _ __  __  ____ _______ _____");
        logs.info("|  __ (_)        | |  \\/  |/ __ \\__   __|  __ \\");
        logs.info("| |__) |__  _____| | \\  / | |  | | | |  | |  | |");
        logs.info("|  ___/ \\ \\/ / _ \\ | |\\/| | |  | | | |  | |  | |");
        logs.info("| |   | |>  <  __/ | |  | | |__| | | |  | |__| |");
        logs.info("|_|   |_/_/\\_\\___|_|_|  |_|\\____/  |_|  |_____/");
        logs.info("&8------------------------------------------------");
        motdUtils = new Motd(this);
        reflectionManager = new ReflectionManager(this);
        logs.info("&8------------------------------------------------");
        logs.info("&7Plugin has been &aenabled&7!");
        logs.info(String.format("&7Using &6%s &7version.", getDescription().getVersion()));
        logs.info("&8------------------------------------------------");
        manager      = new FileManager(this);
        serverIcon   = new ServerIcon(this);
        nmsSetup();
        nmsHandler.showMotd();
    }

    private void nmsSetup() {
        try {
            nmsHandler = (NMS) Class.forName("club.rigox.bukkit.nms." + NMSenum.getCurrent() + ".NMSHandler").getConstructor(new Class[0]).newInstance(new Object[0]);
            getLogs().info("Successfully connected with version: " + NMSenum.getCurrent() + ", the plugin can work correctly. If you found an issue please report to the developer.");
        }catch (Throwable throwable) {
            getLogs().error("Can't initialize NMS, unsupported version: " + NMSenum.getCurrent());
            getLogs().error(throwable);
        }
    }

    @Override
    public void onDisable() {
        logs.info("&8------------------------------------------------");
        logs.info("&7Plugin has been &cdisabled&7!");
        logs.info(String.format("&7Using &6%s &7version.", getDescription().getVersion()));
        logs.info("&8------------------------------------------------");
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

    public ReflectionManager getReflectionManager() { return reflectionManager; }

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

    public Motd getMotd() { return motdUtils; }

    public Logger getLogs() { return logs; }

    public ServerIcon getServerIcon() {
        return serverIcon;
    }

    @SuppressWarnings("unused")
    public PixelMOTD getInstance() { return instance; }
}
