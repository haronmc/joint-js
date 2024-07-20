package ru.smokingplaya.jointjs;

import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    protected static Logger logger;
    public static JavaPlugin plugin;
    public static Server server;

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();
        server = getServer();
        if (!Dependencies.check())
            return;
        Executor.initialize();
        this.getCommand("jointreload").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        logger.info("sosi jopu");
    }
}
