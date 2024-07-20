package ru.smokingplaya.jointjs.functions;

import org.graalvm.polyglot.Value;

import ru.smokingplaya.jointjs.Events;
import ru.smokingplaya.jointjs.Main;

public class EventListener extends Function {
  public EventListener() {
    super("listenEvent");
  }

  private void call(Value arg) {
    Main.server.getPluginManager().registerEvents(new Events(eventObject -> {
      arg.execute(eventObject);
    }), Main.plugin);
  }

  public void onCall(Value[] event) {
    Value arg = event[0];
    if (arg.canExecute()) {
      call(arg);
    } else {
      System.err.println("Argument of \"listenEvent\" function should be function");
    }
  }
}