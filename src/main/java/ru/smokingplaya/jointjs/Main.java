package ru.smokingplaya.jointjs;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {
    protected static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        System.out.println("hui");
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
