package club.rigox.bukkit.utils;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ServerIcon {
    private final PixelMOTD plugin;

    public ServerIcon(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public BufferedImage getIcon(MotdType motdType, String icon) {


        BufferedImage favicon = null;
        InputStream nullIcon  = plugin.getResource("not-set.png");

        plugin.createFolders();

        try {
            String directoryPrefix = "normal";

            if (motdType == MotdType.WHITELIST_MOTD) {
                directoryPrefix = "whitelist";
            }

            File iconPath = new File(plugin.getDataFolder(), directoryPrefix + "-icons/" + icon);

            if (!iconPath.exists()) {
                plugin.getLogs().warn("Favicon " + icon + " doesn't exists on " + directoryPrefix + "-icons folder! Creating one...");

                assert nullIcon != null;
                Files.copy(nullIcon, iconPath.toPath());
            }
            favicon = ImageIO.read(iconPath);

            int height = favicon.getHeight();
            int width  = favicon.getWidth();

            if (height != 64 || width != 64) {
                plugin.getLogs().error("Icon is not a 64x64 resolution. Copying default icon...");

                if (!iconPath.renameTo(new File(iconPath + ".1"))) {
                    plugin.getLogs().error("Cannot rename file.");
                }

                assert nullIcon != null;
                Files.copy(nullIcon, iconPath.toPath());
            }

        } catch (IOException e) {
            plugin.getLogs().error(String.format("Something weird happened while getting %s favicon. Error: %s", icon, e));
        }

        return favicon;
    }
}
