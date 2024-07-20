package ru.smokingplaya.jointjs.commands.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.commands.Commands;

public class Executor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("jointjs.use.command") || args.length == 0)
      return false;

    Callable cmd = Commands.get(args[0]);

    if (cmd == null)
      return false;

    cmd.onCall();

    return true;
  }
}
