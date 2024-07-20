package ru.smokingplaya.jointjs;

import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.graalvm.polyglot.Value;

public abstract class Callable {
  public final String name;

  public Callable(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void onCall() {}

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
