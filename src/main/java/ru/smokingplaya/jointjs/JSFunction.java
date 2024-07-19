package ru.smokingplaya.jointjs;

import org.graalvm.polyglot.Value;

@FunctionalInterface
interface JSFunction {
  void execute(Value jsFunction);
}