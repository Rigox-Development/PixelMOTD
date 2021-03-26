package club.rigox.bukkit.nms.v1_8_R1;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.enums.ShowType;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.ServerPing;
import net.minecraft.server.v1_8_R1.ServerPingSerializer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;

public class MotdPingSerializer extends ServerPingSerializer {
    private final PixelMOTD plugin;
    private String motdName;
    private MotdType motdType;
    public MotdPingSerializer(PixelMOTD plugin,String motdName,MotdType motdType) {
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

    public JsonElement a(ServerPing serverPing, Type type, JsonSerializationContext jsonSerializationContext) {
        String line1 = plugin.getMotd().getFirstLine(motdType,motdName, ShowType.WITHOUT_HEX);
        String line2 = plugin.getMotd().getSecondLine(motdType,motdName, ShowType.WITHOUT_HEX);
        if(line1 == null) line1 = "Unknown Line 1";
        if(line2 == null) line2 = "Unknown Line 2";
        serverPing.setMOTD(convertMessage(line1 + "\n" + line2));
        return super.a(serverPing, type, jsonSerializationContext);
    }
    public IChatBaseComponent convertMessage(String message) {
        return ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&',message) + "\"}");
    }
}
