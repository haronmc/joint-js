package ru.smokingplaya.jointjs.commands;

import java.util.HashMap;

import org.bukkit.command.PluginCommand;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.commands.utils.Executor;
import ru.smokingplaya.jointjs.commands.utils.TabComplete;

public class Commands {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected static HashMap<String, Callable> list = new HashMap();

  public static void initialize(PluginCommand command) {
    command.setExecutor(new Executor());
    command.setTabCompleter(new TabComplete());

    registerCommand(new Reload());
  }

  public static Callable get(String name) {
    return list.get(name);
  }

  private static void registerCommand(Callable command) {
    list.put(command.getName(), command);
  }
}
