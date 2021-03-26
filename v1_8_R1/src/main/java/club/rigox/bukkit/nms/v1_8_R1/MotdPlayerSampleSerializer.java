package club.rigox.bukkit.nms.v1_8_R1;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.enums.ValueMode;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSample;
import net.minecraft.server.v1_8_R1.ServerPingPlayerSampleSerializer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.UUID;

public class MotdPlayerSampleSerializer extends ServerPingPlayerSampleSerializer {
    private final PixelMOTD plugin;
    private String motdName;
    private MotdType motdType;
    public MotdPlayerSampleSerializer(PixelMOTD plugin,String motdName,MotdType motdType) {
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

    public JsonElement a(ServerPingPlayerSample serverPingPlayerSample, Type type, JsonSerializationContext jsonSerializationContext) {
        try {
            int online = Bukkit.getOnlinePlayers().size();
            int max = Bukkit.getMaxPlayers();
            ValueMode value = plugin.getMotd().getMaxMode(motdType);
            if(plugin.getMotd().isCustomMaxPlayersEnabled(motdType)) {
                max = getMax(value,max,online);
                plugin.getReflectionManager().changeField(serverPingPlayerSample, "a", max);
            }
            if(plugin.getMotd().isCustomOnlinePlayersEnabled(motdType)) {
                online = getOnline(value,max,online);
                plugin.getReflectionManager().changeField(serverPingPlayerSample, "b", online);
            }
            if (plugin.getMotd().isCustomHoverEnabled(motdType,motdName)) {
                plugin.getReflectionManager().changeField(serverPingPlayerSample, "c", new GameProfile[]{new GameProfile(new UUID(0L, 0L), plugin.getMotd().getHover(motdType, motdName).toString())});
            }
        } catch (Throwable throwable) {
            plugin.getLogs().error(throwable);
        }
        return super.a(serverPingPlayerSample, type, jsonSerializationContext);
    }

    private int getMax(ValueMode maxMode,int max,int online) {
        if(maxMode == ValueMode.ADD) return online + 1;
        if(maxMode == ValueMode.CUSTOM) return plugin.getMotd().getMaxPlayersValues(motdType);
        if(maxMode == ValueMode.HALF) {
            if(online >= 2) return online / 2;
            return 0;
        }
        if(maxMode == ValueMode.HALF_ADD) {
            int add = 0;
            if(online >= 2) add = online / 2;
            return online + add;
        }
        if(maxMode == ValueMode.EQUAL) {
            return online;
        }
        return max;
    }
    private int getOnline(ValueMode maxMode,int max,int online) {
        if(maxMode == ValueMode.ADD) return online + 1;
        if(maxMode == ValueMode.CUSTOM) return plugin.getMotd().getOnlinePlayersValues(motdType);
        if(maxMode == ValueMode.HALF) {
            if(online >= 2) return online / 2;
            return 0;
        }
        if(maxMode == ValueMode.HALF_ADD) {
            int add = 0;
            if(online >= 2) add = online / 2;
            return online + add;
        }
        if(maxMode == ValueMode.EQUAL) {
            return max;
        }
        return online;
    }
}
