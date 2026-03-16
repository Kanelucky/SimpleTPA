package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.permission.OpPermissionCalculator;
import org.kanelucky.simpletpa.TPAManager;

public class TPAhelpCommand extends Command {
    private final TPAManager tpaManager;

    public TPAhelpCommand(TPAManager tpaManager) {
        super("tpahelp", "Show a list of simpletpa commands", "simpletpa.tpahelp.use");
        this.tpaManager = tpaManager;
        OpPermissionCalculator.NON_OP_PERMISSIONS.addAll(this.permissions);
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .exec(context -> {
                    context.getSender().sendMessage(tpaManager.getHelpMessage());
                    return context.success();
                });
    }
}
