package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.permission.OpPermissionCalculator;
import org.kanelucky.simpletpa.TPAManager;

import java.util.List;

public class TPAunlockCommand extends Command {
    private final TPAManager tpaManager;

    public TPAunlockCommand(TPAManager tpaManager) {
        super("tpaunlock", "Unblock a player", "tpaunlock.use");
        this.tpaManager = tpaManager;
        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .playerTarget("player")
                .exec((context, player) -> {
                    List<EntityPlayer> targets = context.getResult(1);

                    if (targets.isEmpty()) {
                        context.addPlayerNotFoundError();
                        return context.fail();
                    }

                    if (targets.size() > 1) {
                        context.addTooManyTargetsError();
                        return context.fail();
                    }

                    EntityPlayer toUnblock = targets.get(0);
                    tpaManager.unblockPlayer(player, toUnblock);
                    return context.success();
                }, SenderType.PLAYER);
    }
}
