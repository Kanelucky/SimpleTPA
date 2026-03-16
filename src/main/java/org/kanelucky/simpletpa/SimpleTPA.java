package org.kanelucky.simpletpa;

import org.allaymc.api.plugin.Plugin;
import org.allaymc.api.registry.Registries;
import org.allaymc.api.utils.TextFormat;
import org.allaymc.api.utils.config.ConfigSection;

import org.kanelucky.simpletpa.commands.*;

import java.io.File;

public class SimpleTPA extends Plugin {
    private static SimpleTPA instance;
    private TPAManager tpaManager;
    public static ConfigSection defaults;
    public static File configFile;
    @Override
    public void onEnable() {
        instance = this;
        configFile = new File(getPluginContainer().dataFolder().toFile(), "config.yml");
        SimpleTPAconfig.init(configFile);
        tpaManager = new TPAManager(this);
        Registries.COMMANDS.register(new TPAcommand(tpaManager));
        Registries.COMMANDS.register(new TPAacceptCommand(tpaManager));
        Registries.COMMANDS.register(new TPAdenyCommand(tpaManager));
        Registries.COMMANDS.register(new TPAblockCommand(tpaManager));
        Registries.COMMANDS.register(new TPAhereCommand(tpaManager));
        Registries.COMMANDS.register(new TPAunlockCommand(tpaManager));
        Registries.COMMANDS.register(new TPAhelpCommand(tpaManager));
        Registries.COMMANDS.register(new TPAreloadCommand());
        this.pluginLogger.info(TextFormat.GREEN + "Enabled successfully!");
    }
    @Override
    public void onDisable() {
        this.pluginLogger.info(TextFormat.GREEN + "Disabled successfully!");
    }
    public static SimpleTPA getInstance() {
        return instance;
    }

    public TPAManager getTpaManager() {
        return tpaManager;
    }
}
