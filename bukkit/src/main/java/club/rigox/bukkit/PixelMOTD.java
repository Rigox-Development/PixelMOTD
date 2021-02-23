package club.rigox.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import static club.rigox.bukkit.utils.Logger.info;

public final class PixelMOTD extends JavaPlugin {
    private static PixelMOTD instance;
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
    }

    @Override
    public void onDisable() {
        info("&8------------------------------------------------");
        info("&7Plugin has been &cdisabled&7!");
        info(String.format("&7Using &6%s &7version.", getDescription().getVersion()));
        info("&8------------------------------------------------");
    }
    @SuppressWarnings("unused")
    public PixelMOTD getInstance() { return instance; }
}
