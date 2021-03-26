package club.rigox.bukkit.nms.v1_8_R2;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import net.minecraft.server.v1_8_R2.ServerPing;
import org.bukkit.ChatColor;


public class MotdDataSerializer {
    private final PixelMOTD plugin;
    private String motdName;
    private MotdType motdType;
    public MotdDataSerializer(PixelMOTD plugin,String motdName,MotdType motdType) {
        this.plugin = plugin;
        this.motdType = motdType;
        this.motdName = motdName;
    }

    public void setMotdName(String motdName) {
        this.motdName = motdName;
    }
    public void setMotdType(MotdType motdType) {
        this.motdType = motdType;
    }

    public MotdType getMotdType() {
        return motdType;
    }
    public String getMotdName() {
        return motdName;
    }
    public ServerPing.ServerData getData() {
        int protocol = 47;
        String data = "Minecraft 1.8.3";
        if (plugin.getMotd().isCustomProtocolEnabled(motdType)) {
            if(plugin.getMotd().getProtocolVersion(motdType)) protocol = -1;
            data = ChatColor.translateAlternateColorCodes('&',plugin.getMotd().getCustomProtocol(motdType));
        }
        return new ServerPing.ServerData(data, protocol);
    }
}
