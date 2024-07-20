package ru.smokingplaya.jointjs.functions;

import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.Value;

public abstract class Function {
  private final String name;

  public Function(String name) {
    this.name = name;
  }

  public void onCall(Value[] event) {}

  public void onRegister() {}

  public void register(Value binding) {
    binding.putMember(this.name, (ProxyExecutable) event -> {
      this.onCall(event);
      return null;
    });

    this.onRegister();
  }
}
