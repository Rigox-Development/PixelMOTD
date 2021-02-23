package club.rigox.bukkit.utils;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Logger {
    /**
     * Colorize a string provided to method
     * for proxies.
     *
     * @param message Message to transform.
     * @return transformed message with colors.
     */
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a error message to console.
     * @param message message to send.
     */
    public static void error(String message) {
        sendMessage("&f[Pixel MOTD &7| &cERROR&f] " + message);
    }

    /**
     * Send a warn message to console.
     * @param message message to send.
     */
    public static void warn(String message) {
        sendMessage("&f[Pixel MOTD &7| &eWARN&f] " + message);
    }

    /**
     * Send a debug message to console.
     * @param message message to send.
     */
    public static void debug(String message) {
        sendMessage("&f[Pixel MOTD &7| &9DEBUG&f] " + message);
    }

    /**
     * Send a info message to console.
     * @param message message to send.
     */
    public static void info(String message) {
        sendMessage("&f[Pixel MOTD &7| &bINFO&f] " + message);
    }

    /**
     * Sends a message to a Player.
     *
     * @param player Player
     * @param message Message to send.
     */
    public static void sendMessage(CommandSender player, String message) {
        player.sendMessage(color(message));
    }

    /**
     * Sends a message list to a Player.
     *
     * @param player Player
     * @param message Message list to send.
     */
    public static void sendMessage(CommandSender player, List<String> message) {
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
    private static void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }
}
