package ru.smokingplaya.jointjs.functions;

import org.graalvm.polyglot.Value;

public class Functions {
  public static void Register(Value bind) {
    new EventListener().register(bind);
  }
}