package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.permission.OpPermissionCalculator;

import org.kanelucky.simpletpa.TPAManager;

public class TPAacceptCommand extends Command {
    private final TPAManager tpaManager;

    public TPAacceptCommand(TPAManager tpaManager) {
        super("tpaaccept", "Accept a teleport request", "simpletpa.tpaaccept.use");
        this.tpaManager = tpaManager;

        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .exec((context, player) -> {
                    tpaManager.acceptRequest(player);
                    return context.success();
                }, SenderType.PLAYER);
    }
}
