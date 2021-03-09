package club.rigox.bukkit.utils;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.enums.ShowType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Motd {
    private final PixelMOTD plugin;

    public Motd(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    public String getMotd(boolean isWhitelisted) {
        List<String> getMotd = new ArrayList<>();

        if (isWhitelisted) {
            getMotd.addAll(Objects.requireNonNull(plugin.getMotdConfig().getConfigurationSection("whitelist.motds")).getKeys(false));
            return getMotd.get(new Random().nextInt(getMotd.size()));
        }

        getMotd.addAll(Objects.requireNonNull(plugin.getMotdConfig().getConfigurationSection("normal.motds")).getKeys(false));
        return getMotd.get(new Random().nextInt(getMotd.size()));
    }

    public boolean getHexStatus(MotdType motdType, String motdName) {
        switch (motdType) {
            case NORMAL_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("normal.motds.%s.with-hex.enable", motdName));
            case WHITELIST_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("whitelist.motds.%s.with-hex.enable", motdName));
//          TODO  case TIMER_MOTD:
//          TODO      return plugin.getMotdConfig().getBoolean(String.format("timer.motds.%s.with-hex.enable"));
            default:
                break;
        }
        return false;
    }

    public String getFirstLine(MotdType motdType, String motdName, ShowType showType) {
        switch (motdType) {
            case NORMAL_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("normal.motds.%s.with-hex.line-1", motdName));
                }

                return plugin.getMotdConfig().getString(String.format("normal.motds.%s.line-1", motdName));

            case WHITELIST_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.with-hex.line-1", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.line-1", motdName));

            // TODO TIMER MOTD
        }
        return null;
    }

    public String getSecondLine(MotdType motdType, String motdName, ShowType showType) {
        switch (motdType) {
            case NORMAL_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("normal.motds.%s.with-hex.line-2", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("normal.motds.%s.line-2", motdName));

            case WHITELIST_MOTD:
                if (showType == ShowType.HEX) {
                    return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.with-hex.line-2", motdName));
                }
                return plugin.getMotdConfig().getString(String.format("whitelist.motds.%s.line-2", motdName));

            // TODO TIMER MOTD
        }
        return null;
    }

    public boolean isCustomProtocolEnabled(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getBoolean("normal.settings.custom-protocol.enable");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean("whitelist.settings.custom-protocol.enable");
        }

        return false;
    }

    public boolean getProtocolVersion(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getBoolean("normal.settings.custom-protocol.change-protocol-version");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean("whitelist.settings.custom-protocol.change-protocol-version");
        }

        return false;
    }

    public String getCustomProtocol(MotdType motdType) {
        if (motdType == MotdType.NORMAL_MOTD) {
            return plugin.getMotdConfig().getString("normal.settings.custom-protocol-name");
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getString("whitelist.settings.custom-protocol-name");
        }

        return null;
    }


    public boolean isCustomHoverEnabled(MotdType motdType, String motdName) {
        switch (motdType) {
            case NORMAL_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("normal.motds.%s.hover.toggle", motdName));
            case WHITELIST_MOTD:
                return plugin.getMotdConfig().getBoolean(String.format("whitelist.motds.%s.hover.toggle", motdName));
            case TIMER_MOTD:
                // TODO
        }
        return true;
    }

    public boolean getIconStatus(MotdType motdType, String motdName) {
        if (motdType == MotdType.NORMAL_MOTD) {

            return plugin.getMotdConfig().getBoolean(String.format("normal.motds.%s.custom-icon.enable", motdName));
        }

        if (motdType == MotdType.WHITELIST_MOTD) {
            return plugin.getMotdConfig().getBoolean(String.format("whitelist.motds.%s.custom-icon.enable", motdName));
        }
        return false;
    }
}
