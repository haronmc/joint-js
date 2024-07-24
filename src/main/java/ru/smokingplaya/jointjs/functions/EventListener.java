package ru.smokingplaya.jointjs.functions;

import java.util.HashMap;
import java.util.List;

import org.graalvm.polyglot.Value;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.Events;
import ru.smokingplaya.jointjs.Main;
import ru.smokingplaya.jointjs.Utils;

public class EventListener extends Callable {
  private static HashMap<String, Value> listeners = new HashMap<String, Value>();
  private static List<String> ignored = Main.config.getStringList("ignore-events");
  private static Events listener = new Events(eventObject -> {
    if (ignored.contains(eventObject.getEventName()))
      return;

    listeners.forEach((id, value) -> {
      value.execute(eventObject);
    });
  });

  public EventListener() {
    super("listenEvent");
  }

  private void call(Value arg) {
    String plugin = Utils.getPluginFolder(arg);

    listeners.put(plugin, arg);
  }

  public void onCall(Value[] event) {
    Value arg = event[0];
    if (arg.canExecute()) {
      call(arg);
    } else {
      System.err.println("Argument of \"listenEvent\" function should be function");
    }
  }

  public void onRegister() {
    Main.server.getPluginManager()
      .registerEvents(listener, Main.plugin);
  }
}