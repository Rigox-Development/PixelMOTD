package club.rigox.bukkit.utils;

import club.rigox.bukkit.PixelMOTD;

public class Placeholders {
    private final PixelMOTD plugin;

    public Placeholders(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getWhitelistAuthor() {
        String author             = plugin.getDataConfig().getString("whitelist.author");
        String customAuthor       = plugin.getDataConfig().getString("whitelist.custom-console-name.name");

        boolean customAuthorCheck = plugin.getDataConfig().getBoolean("whitelist.custom-console-name.toggle");

        assert author != null;
        if (!author.equalsIgnoreCase("CONSOLE"))
            return author;

        if (customAuthorCheck)
            return customAuthor;

        return "Console";
    }
}
