package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.entity.interfaces.EntityPlayer;
import org.allaymc.api.permission.OpPermissionCalculator;
import org.kanelucky.simpletpa.TPAManager;

import java.util.List;

import static org.kanelucky.simpletpa.SimpleTPAconfig.config;

public class TPAblockCommand extends Command {
    private final TPAManager tpaManager;

    public TPAblockCommand(TPAManager tpaManager) {
        super("tpablock", "Block a player", "tpablock.use");
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

                    EntityPlayer toBlock = targets.get(0);

                    if (toBlock.getUniqueId().equals(player.getUniqueId())) {
                        String msg = config.getString("messages.error-self-block");
                        context.addError(msg);
                        return context.fail();
                    }

                    tpaManager.blockPlayer(player, toBlock);
                    return context.success();
                }, SenderType.PLAYER);
    }
}
