package club.rigox.bungee.commands.subcommands;

import club.rigox.bungee.PixelMOTD;
import club.rigox.bungee.enums.ConfigType;
import club.rigox.bungee.enums.KickType;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.md_5.bungee.api.CommandSender;

import java.util.List;

import static club.rigox.bungee.commands.CommandUtils.isUuid;
import static club.rigox.bungee.utils.FileManager.getMessageString;
import static club.rigox.bungee.utils.Logger.sendMessage;

@CommandAlias("bpmotd")
@CommandPermission("pixelmotd.blacklist.manage")
public class BlacklistCommand extends BaseCommand {
    private final PixelMOTD plugin;

    public BlacklistCommand(PixelMOTD plugin) {
        this.plugin = plugin;
    }

    @Subcommand("blacklist")
    public void sendWhitelistHelp(CommandSender sender) {
        sendMessage(sender, plugin.getMessagesConfig().getStringList("help.blacklist"));
    }

    @Subcommand("blacklist add")
    @CommandCompletion("@players")
    public void onBlacklistAdd(CommandSender sender, @Single String player) {
        List<String> uuidList   = plugin.getPlayersConfig().getStringList("blacklist.players-uuid");
        List<String> playerList = plugin.getPlayersConfig().getStringList("blacklist.players-name");

        if (isUuid(player)) {
            if (plugin.getPlayersConfig().get("blacklist.players-uuid") == null) {

                plugin.getCmdUtils().initEditList(KickType.BLACKLIST_UUID, player);
                plugin.getCmdUtils().kickOnWhitelist(KickType.BLACKLIST);

                sendMessage(sender, String.format(getMessageString("blacklist.uuid.added"), player));
                return;
            }

            if (uuidList.contains(player)) {
                sendMessage(sender, String.format(getMessageString("blacklist.uuid.already"), player));
                return;
            }

            uuidList.add(player);

            plugin.getPlayersConfig().set("blacklist.players-uuid", uuidList);
            plugin.getManager().reloadConfig(ConfigType.PLAYERS);

            plugin.getCmdUtils().kickOnWhitelist(KickType.BLACKLIST);

            sendMessage(sender, String.format(getMessageString("blacklist.uuid.added"), player));
            return;
        }

        if (plugin.getPlayersConfig().get("blacklist.players-name") == null) {
            plugin.getCmdUtils().initEditList(KickType.BLACKLIST_PLAYER, player);

            plugin.getCmdUtils().kickOnWhitelist(KickType.BLACKLIST);
            sendMessage(sender, String.format(getMessageString("blacklist.player.added"), player));
            return;
        }

        if (playerList.contains(player)) {
            sendMessage(sender, String.format(getMessageString("blacklist.player.already"), player));
            return;
        }

        playerList.add(player);

        plugin.getPlayersConfig().set("blacklist.players-name", playerList);
        plugin.getManager().reloadConfig(ConfigType.PLAYERS);

        plugin.getCmdUtils().kickOnWhitelist(KickType.BLACKLIST);

        sendMessage(sender, String.format(getMessageString("blacklist.player.added"), player));
    }
}
