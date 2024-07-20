package ru.smokingplaya.jointjs;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import ru.smokingplaya.jointjs.commands.Commands;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static JavaPlugin plugin;
    public static Logger logger;
    public static Server server;

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();
        server = getServer();

        if (!Dependencies.check())
            return;

        Executor.initialize();
        Commands.initialize(this.getCommand("joint"));
    }

    @Override
    public void onDisable() {
        logger.info("sosi jopu");
    }
}
