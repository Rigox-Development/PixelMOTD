package club.rigox.bukkit.utils;


import club.rigox.bukkit.PixelMOTD;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Logger {
    private final PixelMOTD plugin;
    public Logger(PixelMOTD plugin) {
        this.plugin = plugin;
    }
    /**
     * Colorize a string provided to method
     * for servers.
     *
     * @param message Message to transform.
     * @return transformed message with colors.
     */
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a error message to console.
     * @param message message to send.
     */
    public void error(String message) {
        sendMessage("&f[Pixel MOTD &7| &cERROR&f] " + message);
    }
    /**
     * Send a error message to console.
     * @param throwable throwable to send.
     */
    public void error(Throwable throwable) {
        String location = throwable.getClass().getName();
        String error = throwable.getClass().getSimpleName();
        sendMessage("&f[Pixel MOTD &7| &cERROR] -------------------------");
        sendMessage("&f[Pixel MOTD &7| &cERROR] Location: " + location.replace("." + error,""));
        sendMessage("&f[Pixel MOTD &7| &cERROR] Error: " + error);
        if(throwable.getStackTrace() != null) {
            sendMessage("&f[Pixel MOTD &7| &cERROR] Internal - StackTrace: ");
            List<StackTraceElement> other = new ArrayList<>();
            for(StackTraceElement line : throwable.getStackTrace()) {
                if(line.toString().contains("rigox")) {
                    sendMessage("&f[Pixel MOTD &7| &cERROR] (Line: " + line.getLineNumber() + ") " + line.toString().replace("(" + line.getFileName() + ":" + line.getLineNumber() + ")","").replace("club.rigox.bukkit.","").replace("club.rigox.",""));
                } else {
                    other.add(line);
                }
            }
            sendMessage("&f[Pixel MOTD &7| &cERROR]  -------------------------");
            sendMessage("&f[Pixel MOTD &7| &cERROR] External - StackTrace: ");
            for(StackTraceElement line : other) {
                sendMessage("&f[Pixel MOTD &7| &cERROR] (Line: " + line.getLineNumber() + ") (Class: " + line.getFileName() + ") (Method: " + line.getMethodName() + ")".replace(".java",""));
            }

        }
        sendMessage("&f[Pixel MOTD &7| &cERROR]  -------------------------");
    }

    /**
     * Send a warn message to console.
     * @param message message to send.
     */
    public void warn(String message) {
        sendMessage("&f[Pixel MOTD &7| &eWARN&f] " + message);
    }

    /**
     * Send a debug message to console.
     * @param message message to send.
     */
    public void debug(String message) {
        sendMessage("&f[Pixel MOTD &7| &9DEBUG&f] " + message);
    }

    /**
     * Send a info message to console.
     * @param message message to send.
     */
    public void info(String message) {
        sendMessage("&f[Pixel MOTD &7| &bINFO&f] " + message);
    }

    /**
     * Sends a message to a Player.
     *
     * @param player Player
     * @param message Message to send.
     */
    public void sendMessage(CommandSender player, String message) {
        player.sendMessage(color(message));
    }

    /**
     * Sends a message list to a Player.
     *
     * @param player Player
     * @param message Message list to send.
     */
    public void sendMessage(CommandSender player, List<String> message) {
        for (String list : message) {
            player.sendMessage(color(list));
        }
    }

    /**
     * Used to other methods and prevent this copy pasta
     * to those methods.
     *
     * @param message Provided message
     */
    private void sendMessage(String message) {
        plugin.getServer().getConsoleSender().sendMessage(color(message));
    }
}
