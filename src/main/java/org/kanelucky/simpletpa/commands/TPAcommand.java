package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.permission.OpPermissionCalculator;
import org.kanelucky.simpletpa.TPAManager;
import org.kanelucky.simpletpa.TPARequest;

import java.util.*;

import static org.kanelucky.simpletpa.SimpleTPAconfig.config;

public class TPAcommand extends Command {
    private final TPAManager tpaManager;
    private String requestSent;

    public TPAcommand(TPAManager tpaManager) {
        super("tpa", "Teleport request system", "simpletpa.tpa.use");
        this.tpaManager = tpaManager;
        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .key("help")
                .permission("simpletpa.tpahelp.use")
                .exec(context -> {
                    context.getSender().sendMessage(tpaManager.getHelpMessage());
                    return context.success();
                })
                .root()
                .playerTarget("player")
                .exec((context, player) -> {
                    List<EntityPlayer> targets = context.getResult(0);

                    if (targets.isEmpty()) {
                        context.addPlayerNotFoundError();
                        return context.fail();
                    }

                    if (targets.size() > 1) {
                        context.addTooManyTargetsError();
                        return context.fail();
                    }

                    EntityPlayer target = targets.get(0);

                    if (target.getUniqueId().equals(player.getUniqueId())) {
                        String msg = config.getString("messages.error-self-teleport");
                        context.addError(msg);
                        return context.fail();
                    }

                    tpaManager.sendRequest(player, target, TPARequest.TpaType.TPA);
                    return context.success();
                }, SenderType.PLAYER);
    }
}

