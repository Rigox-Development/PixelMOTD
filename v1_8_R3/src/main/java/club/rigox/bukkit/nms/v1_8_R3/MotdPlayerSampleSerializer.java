package club.rigox.bukkit.nms.v1_8_R3;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.enums.ValueMode;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.ServerPing;
import org.bukkit.Bukkit;

import java.util.UUID;
@SuppressWarnings("unused")
public class MotdPlayerSampleSerializer {
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

    public MotdType getMotdType() {
        return motdType;
    }
    public String getMotdName() {
        return motdName;
    }
    public ServerPing.ServerPingPlayerSample getData() {
        int online = Bukkit.getOnlinePlayers().size();
        int max = Bukkit.getMaxPlayers();
        ValueMode value = plugin.getMotd().getMaxMode(motdType);
        if(plugin.getMotd().isCustomMaxPlayersEnabled(motdType)) {
            max = getMax(value,max,online);
        }
        if(plugin.getMotd().isCustomOnlinePlayersEnabled(motdType)) {
            online = getOnline(value,max,online);
        }
        ServerPing.ServerPingPlayerSample data = new ServerPing.ServerPingPlayerSample(online, max);
        data.a(new GameProfile[]{new GameProfile(new UUID(0L, 0L), plugin.getMotd().getHover(motdType, motdName).toString())});
        return data;
    }

    private int getMax(ValueMode maxMode, int max, int online) {
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
