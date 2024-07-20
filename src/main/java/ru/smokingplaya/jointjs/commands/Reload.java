package ru.smokingplaya.jointjs.commands;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.JavaScriptPlugin;

public class Reload extends Callable {
  public Reload() {
    super("reload");
  }

  public void onCall() {
    JavaScriptPlugin.plugins.forEach((name, plugin) -> {
      plugin.execute();
    });
  }
}
