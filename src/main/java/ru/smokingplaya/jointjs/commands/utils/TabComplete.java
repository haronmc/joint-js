package ru.smokingplaya.jointjs.commands.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (args.length == 1)
      return Arrays.asList("reload"); // todo @ make it scalable

    return new ArrayList<>();
  }
}
