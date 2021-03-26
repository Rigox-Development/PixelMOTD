package club.rigox.bukkit.nms.v1_8_R3;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.enums.ShowType;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ServerPing;
import org.bukkit.ChatColor;

import java.lang.reflect.Type;

public class MotdPingSerializer extends ServerPing.Serializer {
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
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&',message) + "\"}");
    }
}
