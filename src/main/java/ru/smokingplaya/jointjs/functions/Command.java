package ru.smokingplaya.jointjs.functions;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.Main;
import ru.smokingplaya.jointjs.Utils;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.graalvm.polyglot.Value;

public class Command extends Callable {
  private static HashMap<String, JSCommand> commands = new HashMap<String, JSCommand>();

  public Command() {
    super("addCommand");
  }

  public void addCommand(Value command) {
    Value name = command.getMember("commandName");
    if (!name.isString())
      throw new Error("commandName field must be string");

    if (!command.getMember("executor").canExecute())
      throw new Error("executor field must be function");

    String plugin = Utils.getPluginFolder(command);

    if (commands.containsKey(plugin))
      commands.get(plugin).unregister();

    JSCommand cmd = new JSCommand(command, name.toString());

    commands.put(plugin, cmd);
  }

  public void onCall(Value[] event) {
    Value command = event[0];

    if (command.isNull() || !command.hasMembers())
      throw new Error("command (#1) parameter must be object");

    addCommand(command);
  }

  /**
   * JSCommand
   */
  private class JSCommand extends org.bukkit.command.Command {
    @SuppressWarnings("unused")
    private Value command;
    private Value executor;
    private Value sendMessage = null;
    private String permission;
    private String name;

    public JSCommand(Value command, String name) {
      super(
          name,
          command.getMember("description").toString(),
          command.getMember("usage").toString(),
          Arrays.asList(new String[0]));

      this.command = command;
      this.executor = command.getMember("executor");
      this.sendMessage = command.getMember("sendMessage");
      this.permission = command.getMember("permission").toString();
      this.name = name;

      this.register();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      if (!sender.hasPermission(this.permission)) {
        this.sendMessage.execute(sender, Main.config.getString("lang.dont-have-permissions"));

        return false;
      }

      var arguments = this.command.getMember("arguments");
      if (arguments.hasArrayElements()) {
        long arraySize = arguments.getArraySize();

        for (int i = 0; i < arraySize; i++) {
          Value data = arguments.getMember(i + "");
          boolean isOptional = data
              .getMember("isOptional")
              .asBoolean();

          if (!isOptional && (i > args.length - 1)) {
            this.sendMessage.execute(sender,
                String.format(Main.config.getString("lang.missing-argument"), data.getMember("name").toString()));

            return false;
          }
        }
      }

      Value result = executor.execute(sender, (Object[]) args);

      if (result.isNull())
        return true;

      if (!result.isBoolean()) {
        System.out.println("Command executor should return boolean or nothing!");

        return false;
      }

      return result.asBoolean();
    }

    public void register() {
      // todo @ tabCompleter
      try {
        Utils.getCommandMap().register(
            Main.plugin
                .getDescription()
                .getName(),
            this);
      } catch (Exception err) {
        Main.logger.severe("Unable to register command " + this.name + ": " + err.toString());
      }
    }

    @SuppressWarnings("unchecked")
    public void unregister() {
      try {
        Object result = Utils.getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        HashMap<String, Command> knownCommands;

        try {
          Object knownCommandsMap = Utils.getPrivateField(commandMap, "knownCommands");
          knownCommands = (HashMap<String, Command>) knownCommandsMap;
        } catch (Exception ignored) {
          try {
            knownCommands = (HashMap<String, Command>) commandMap.getClass().getMethod("getKnownCommands")
                .invoke(commandMap);
          } catch (Exception _ignored) {
            return;
          }
        }
        knownCommands.remove(getName());
        for (String alias : getAliases()) {
          if (knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(this.getName())) {
            knownCommands.remove(alias);
          }
        }
      } catch (Exception err) {
        Main.logger.severe("Unable to register command " + this.name + ": " + err.toString());
      }
    }
  }

  public static class Argument {
    private String name;
    private boolean isOptional;

    public Argument(String name, boolean isOptional) {
      this.name = name;
      this.isOptional = isOptional;
    }
  }
}