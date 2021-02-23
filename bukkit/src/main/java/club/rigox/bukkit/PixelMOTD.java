package club.rigox.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public final class PixelMOTD extends JavaPlugin {
    private static PixelMOTD instance;
    @Override
    public void onEnable() {
        instance = this;
    }
    public PixelMOTD getInstance() { return instance; }
}
