package org.kanelucky.simpletpa;

import org.allaymc.api.utils.TextFormat;
import org.allaymc.api.utils.config.Config;
import org.allaymc.api.utils.config.ConfigSection;

import java.io.File;

public class SimpleTPAconfig {
    public static Config config;

    public static void init(File configFile) {
        ConfigSection defaults = new ConfigSection();

        // Request Messages
        defaults.set("messages.request-sent", "&aTeleport request sent to {player}");
        defaults.set("messages.request-sent-here", "&aTeleport request sent to {player}");
        defaults.set("messages.request-received", "&e{player} wants to teleport to you.");
        defaults.set("messages.request-received-here", "&e{player} wants you to teleport to them.");
        defaults.set("messages.request-instruction", "&eUse /tpaaccept to accept or /tpadeny to deny.");

        // Accept Messages
        defaults.set("messages.accept-teleporting", "&aTeleporting to {player}...");
        defaults.set("messages.accept-teleported-to-you", "&a{player} teleported to you.");
        defaults.set("messages.accept-you-teleported", "&aTeleporting to {player}...");
        defaults.set("messages.accept-player-teleported", "&a{player} teleported to you.");

        // Deny Messages
        defaults.set("messages.deny-success", "&eYou denied the teleport request.");
        defaults.set("messages.deny-notified", "&c{player} denied your teleport request.");

        // Block/Unblock Messages
        defaults.set("messages.block-success", "&aBlocked {player} from sending teleport requests.");
        defaults.set("messages.block-already", "&e{player} is already blocked.");
        defaults.set("messages.unblock-success", "&aUnblocked {player}.");
        defaults.set("messages.unblock-not-blocked", "&e{player} is not blocked.");

        // Error Messages
        defaults.set("messages.error-no-request", "&cYou don't have any pending teleport requests.");
        defaults.set("messages.error-request-expired", "&cThe teleport request has expired.");
        defaults.set("messages.error-player-offline", "&cThe player is no longer online.");
        defaults.set("messages.error-already-pending", "&cThis player already has a pending request.");
        defaults.set("messages.error-blocked", "&cThis player has blocked your teleport requests.");
        defaults.set("messages.error-self-teleport", "&cYou cannot send a teleport request to yourself!");
        defaults.set("messages.error-self-block", "&cYou cannot block yourself!");

        config = new Config(configFile, Config.YAML, defaults);
        config.save();
    }

    /**
     * Get message from config with color codes applied
     */
    public static String msg(String key) {
        String message = config.getString("messages." + key, "&cMessage not found: " + key);
        return TextFormat.colorize(message);
    }

    /**
     * Reload configuration from disk
     */
    public static void reload() {
        config.reload();
    }
}