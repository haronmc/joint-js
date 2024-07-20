package ru.smokingplaya.jointjs;

import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.reflections.Reflections;

public class Events implements Listener {
  private EventCallback listener;

  public Events(EventCallback callback) {
    listener = callback;

    Set<Class<? extends Event>> classes = getAllEventClasses();

    for (var eventClass: classes)
      Main.server.getPluginManager().registerEvent(eventClass, this, EventPriority.HIGH, (listener, event) -> {
        eventListener(event);
      }, Main.plugin);
  }

  // <- нахуя он тут нужен?
  public void eventListener(Event event) {
    listener.callback(event);
  }

  public static Set<Class<? extends Event>> getAllEventClasses() {
    Reflections reflections = new Reflections("org.bukkit.event");
    var classes = reflections.getSubTypesOf(Event.class);

    classes.removeIf(jopa -> {
      try {
				jopa.getMethod("getHandlerList");
			} catch (Exception e) {
				return true;
			}

      return false;
    });

    return classes;
  }
}
