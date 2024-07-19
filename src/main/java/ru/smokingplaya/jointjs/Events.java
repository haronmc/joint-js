package ru.smokingplaya.jointjs;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class Events implements Listener {
  private EventCallback listener;

  Events(EventCallback callback) {
    listener = callback;
  }

  public static Value getListenFunction(Context context) {
    return context.asValue((java.util.function.Function<Value, Object>) event -> {
      System.out.println("hi");
			return event;
    });
  }

  @EventHandler
  public void eventListener(Event event) {
    System.out.println(">Event");
    listener.callback(event);
  }
}
