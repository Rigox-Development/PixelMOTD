package club.rigox.bukkit.nms.v1_8_R1;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import net.minecraft.server.v1_8_R1.ServerPingServerData;
import net.minecraft.server.v1_8_R1.ServerPingServerDataSerializer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public class MotdDataSerializer extends ServerPingServerDataSerializer {
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

    public JsonElement a(ServerPingServerData serverData, Type type, JsonSerializationContext jsonSerializationContext) {
        if (plugin.getMotd().isCustomProtocolEnabled(motdType)) {
            try {
                if(plugin.getMotd().getProtocolVersion(motdType)) plugin.getReflectionManager().changeField(serverData, "b", -1);
                plugin.getReflectionManager().changeField(serverData, "a", ChatColor.translateAlternateColorCodes('&',plugin.getMotd().getCustomProtocol(motdType)));
            } catch (Throwable throwable) {
                plugin.getLogs().error(throwable);
            }
        }
        return super.a(serverData, type, jsonSerializationContext);
    }
}
