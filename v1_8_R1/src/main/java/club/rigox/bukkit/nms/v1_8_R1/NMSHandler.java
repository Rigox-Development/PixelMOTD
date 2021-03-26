package club.rigox.bukkit.nms.v1_8_R1;

import club.rigox.bukkit.PixelMOTD;
import club.rigox.bukkit.enums.MotdType;
import club.rigox.bukkit.nms.NMS;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;

public final class NMSHandler implements NMS {
    boolean whitelistStatus = false;
    private PixelMOTD plugin;
    private MotdDataSerializer motdDataSerializer;
    private MotdPlayerSampleSerializer motdPlayerSampleSerializer;
    private MotdPingSerializer motdPingSerializer;
    private Gson motdEvent;

    @Override
    public void initMotdEvent(PixelMOTD plugin) {
        this.plugin = plugin;
        MotdType motdType = MotdType.NORMAL_MOTD;
        if(whitelistStatus) motdType = MotdType.WHITELIST_MOTD;
        String motdName = plugin.getMotd().getMotd(whitelistStatus);
        motdDataSerializer = new MotdDataSerializer(plugin,motdName,motdType);
        motdPingSerializer = new MotdPingSerializer(plugin,motdName,motdType);
        motdPlayerSampleSerializer = new MotdPlayerSampleSerializer(plugin,motdName,motdType);
        motdEvent = (new GsonBuilder())
                .registerTypeAdapter(ServerPingServerData.class, motdDataSerializer)
                .registerTypeAdapter(ServerPingPlayerSample.class, motdPlayerSampleSerializer)
                .registerTypeAdapter(ServerPing.class, motdPingSerializer)
                .registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer())
                .registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer())
                .registerTypeAdapterFactory(new ChatTypeAdapterFactory())
                .create();
    }

    @Override
    public void generateMotd() {
        MotdType motdType = MotdType.NORMAL_MOTD;
        if(whitelistStatus) motdType = MotdType.WHITELIST_MOTD;
        String motdName = plugin.getMotd().getMotd(whitelistStatus);
        if(motdDataSerializer.getMotdName().equalsIgnoreCase(motdName)) {
            motdDataSerializer.setMotdName(motdName);
            motdPlayerSampleSerializer.setMotdName(motdName);
            motdPingSerializer.setMotdName(motdName);
        }
        if(motdDataSerializer.getMotdType() != motdType) {
            motdDataSerializer.setMotdType(motdType);
            motdPlayerSampleSerializer.setMotdType(motdType);
            motdPingSerializer.setMotdType(motdType);
        }
        motdEvent = (new GsonBuilder())
                .registerTypeAdapter(ServerPingServerData.class, motdDataSerializer)
                .registerTypeAdapter(ServerPingPlayerSample.class, motdPlayerSampleSerializer)
                .registerTypeAdapter(ServerPing.class, motdPingSerializer)
                .registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer())
                .registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer())
                .registerTypeAdapterFactory(new ChatTypeAdapterFactory())
                .create();
    }

    @Override
    public void showMotd() {
        try {
            plugin.getReflectionManager().changeStaticFinalField(new PacketStatusOutServerInfo(), "a", motdEvent);
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't show the motd");
            plugin.getLogs().error(throwable);
        }
    }

    @Override
    public void setWhitelistStatus(boolean whitelistStatus) {
        this.whitelistStatus = whitelistStatus;
    }

    @Override
    public boolean getWhitelistStatus() {
        return whitelistStatus;
    }
}