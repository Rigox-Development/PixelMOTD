package club.rigox.bukkit.nms;

import club.rigox.bukkit.PixelMOTD;

public interface NMS {
    void initMotdEvent(PixelMOTD plugin);
    void generateMotd();
    void showMotd();
    void setWhitelistStatus(boolean whitelistStatus);
    boolean getWhitelistStatus();
}
