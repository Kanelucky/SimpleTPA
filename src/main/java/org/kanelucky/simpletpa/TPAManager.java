package org.kanelucky.simpletpa;

import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.eventbus.event.entity.EntityTeleportEvent;
import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.server.Server;
import org.allaymc.api.utils.TextFormat;

import java.util.*;

import static org.kanelucky.simpletpa.SimpleTPAconfig.config;
import static org.kanelucky.simpletpa.SimpleTPAconfig.msg;

public class TPAManager {

    private final Plugin plugin;

    private final Map<UUID, TPARequest> pendingRequests = new HashMap<>();
    private final Map<UUID, Set<UUID>> blockedPlayers = new HashMap<>();

    private final long requestTimeout = 60;

    public TPAManager(Plugin plugin) {
        this.plugin = plugin;
        startCleanupTask();
    }

    private String format(String template, String player) {
        return template.replace("{player}", player);
    }

    public boolean sendRequest(EntityPlayer requester, EntityPlayer target, TPARequest.TpaType type) {

        if (requester.getUniqueId().equals(target.getUniqueId())) {
            requester.sendMessage(msg("error-self-teleport"));
            return false;
        }

        if (isBlocked(target, requester)) {
            requester.sendMessage(msg("error-blocked"));
            return false;
        }

        if (pendingRequests.containsKey(target.getUniqueId())) {
            requester.sendMessage(msg("error-already-pending"));
            return false;
        }

        TPARequest request = new TPARequest(requester, target, type);
        pendingRequests.put(target.getUniqueId(), request);

        if (type == TPARequest.TpaType.TPA) {

            requester.sendMessage(
                    format(msg("request-sent"), target.getDisplayName())
            );

            target.sendMessage(
                    format(msg("request-received"), requester.getDisplayName())
            );

            target.sendMessage(msg("request-instruction"));

        } else {

            requester.sendMessage(
                    format(msg("request-sent-here"), target.getDisplayName())
            );

            target.sendMessage(
                    format(msg("request-received-here"), requester.getDisplayName())
            );

            target.sendMessage(msg("request-instruction"));
        }

        return true;
    }

    public boolean acceptRequest(EntityPlayer player) {

        TPARequest request = pendingRequests.get(player.getUniqueId());

        if (request == null) {
            player.sendMessage(msg("error-no-request"));
            return false;
        }

        if (request.isExpired(requestTimeout)) {
            pendingRequests.remove(player.getUniqueId());
            player.sendMessage(msg("error-request-expired"));
            return false;
        }

        EntityPlayer requester = request.getRequester();

        if (!isPlayerOnline(requester)) {
            pendingRequests.remove(player.getUniqueId());
            player.sendMessage(msg("error-player-offline"));
            return false;
        }

        if (request.getType() == TPARequest.TpaType.TPA) {

            requester.teleport(player.getLocation(), EntityTeleportEvent.Reason.COMMAND);

            requester.sendMessage(
                    format(msg("accept-teleporting"), player.getDisplayName())
            );

            player.sendMessage(
                    format(msg("accept-player-teleported"), requester.getDisplayName())
            );

        } else {

            player.teleport(requester.getLocation(), EntityTeleportEvent.Reason.COMMAND);

            player.sendMessage(
                    format(msg("accept-you-teleported"), requester.getDisplayName())
            );

            requester.sendMessage(
                    format(msg("accept-player-teleported"), player.getDisplayName())
            );
        }

        pendingRequests.remove(player.getUniqueId());
        return true;
    }

    public boolean denyRequest(EntityPlayer player) {

        TPARequest request = pendingRequests.get(player.getUniqueId());

        if (request == null) {
            player.sendMessage(msg("error-no-request"));
            return false;
        }

        EntityPlayer requester = request.getRequester();

        pendingRequests.remove(player.getUniqueId());

        player.sendMessage(msg("deny-success"));

        if (isPlayerOnline(requester)) {
            requester.sendMessage(
                    format(msg("deny-notified"), player.getDisplayName())
            );
        }

        return true;
    }

    public void blockPlayer(EntityPlayer player, EntityPlayer toBlock) {

        if (player.getUniqueId().equals(toBlock.getUniqueId())) {
            player.sendMessage(msg("error-self-block"));
            return;
        }

        Set<UUID> blocked = blockedPlayers.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>());

        if (blocked.add(toBlock.getUniqueId())) {

            player.sendMessage(
                    format(msg("block-success"), toBlock.getDisplayName())
            );

        } else {

            player.sendMessage(
                    format(msg("block-already"), toBlock.getDisplayName())
            );
        }
    }

    public void unblockPlayer(EntityPlayer player, EntityPlayer toUnblock) {

        Set<UUID> blocked = blockedPlayers.get(player.getUniqueId());

        if (blocked != null && blocked.remove(toUnblock.getUniqueId())) {

            player.sendMessage(
                    format(msg("unblock-success"), toUnblock.getDisplayName())
            );

        } else {

            player.sendMessage(
                    format(msg("unblock-not-blocked"), toUnblock.getDisplayName())
            );
        }
    }

    private boolean isBlocked(EntityPlayer player, EntityPlayer requester) {
        Set<UUID> blocked = blockedPlayers.get(player.getUniqueId());
        return blocked != null && blocked.contains(requester.getUniqueId());
    }

    private boolean isPlayerOnline(EntityPlayer player) {
        return Server.getInstance().getPlayerManager().getPlayers().containsKey(player.getUniqueId());
    }

    private void startCleanupTask() {

        Server.getInstance().getScheduler().scheduleRepeating(plugin, () -> {

            pendingRequests.entrySet().removeIf(entry ->
                    entry.getValue().isExpired(requestTimeout)
            );

            return true;

        }, 20 * 10);
    }

    /**
     * Get help message
     */
    /** * Help message */
    public String getHelpMessage() {
        return TextFormat.GOLD + "=== TPA Commands ===" +
                TextFormat.RESET + "\n" +
                TextFormat.YELLOW + "/tpa <player>" +
                TextFormat.RESET + " - Request to teleport to a player\n" +
                TextFormat.YELLOW + "/tpahere <player>" +
                TextFormat.RESET + " - Request a player to teleport to you\n" +
                TextFormat.YELLOW + "/tpaaccept" +
                TextFormat.RESET + " - Accept a teleport request\n" +
                TextFormat.YELLOW + "/tpadeny" +
                TextFormat.RESET + " - Deny a teleport request\n" +
                TextFormat.YELLOW + "/tpablock <player>" +
                TextFormat.RESET + " - Block requests from a player\n" +
                TextFormat.YELLOW + "/tpaunblock <player>" +
                TextFormat.RESET + " - Unblock a player";
    }
}