package ru.smokingplaya.jointjs;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ru.smokingplaya.jointjs.commands.Commands;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    public static JavaPlugin plugin;
    public static Logger logger;
    public static Server server;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        plugin = this;
        config = getConfig();
        logger = getLogger();
        server = getServer();

        if (!Dependencies.check())
            return;

        Executor.initialize();
        Commands.initialize(this.getCommand("joint"));
    }

    @Override
    public void onDisable() {
        logger.info("disabling plugin");
    }
}
