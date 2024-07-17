package ru.smokingplaya.jointjs;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

import java.util.logging.Logger;

public class ReloadCommand  implements CommandExecutor {
    private static Logger logger = Main.logger;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        JavaScriptPlugin.plugins.forEach((name, plugin) -> {
            logger.info("Reloading plugin " + name);
            plugin.execute();
        });

        return true;
    }
}
