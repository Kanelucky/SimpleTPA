package org.kanelucky.simpletpa.commands;

import org.allaymc.api.command.Command;
import org.allaymc.api.command.SenderType;
import org.allaymc.api.command.tree.CommandTree;
import org.allaymc.api.utils.TextFormat;

import static org.kanelucky.simpletpa.SimpleTPAconfig.config;

public class TPAreloadCommand extends Command {

    public TPAreloadCommand() {
        super("tpareload", "Reload TPA configuration", "tpa.reload");
    }

    @Override
    public void prepareCommandTree(CommandTree tree) {
        tree.getRoot()
                .exec((context, sender) -> {
                    config.reload();
                    sender.sendMessage(TextFormat.GREEN + "TPA configuration reloaded!");
                    return context.success();
                }, SenderType.ANY);
    }
}
