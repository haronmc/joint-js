package ru.smokingplaya.jointjs;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    protected static Logger logger;
    protected static JavaPlugin plugin;

    @Override
    public void onEnable() {
        logger = getLogger();
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
