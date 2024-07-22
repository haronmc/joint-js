package ru.smokingplaya.jointjs.functions;

import java.util.concurrent.CompletableFuture;

import org.graalvm.polyglot.Value;

import ru.smokingplaya.jointjs.Callable;
import ru.smokingplaya.jointjs.functions.util.FetchData;

// todo @
public class Fetch extends Callable {
  private static FetchData defaultData = new FetchData() {
    {
      this.body = "";
      this.method = "get";
    }
  };

  public Fetch() {
    super("fetch");
  }

  public CompletableFuture<> fetch(Value[] event) {
    if (event.length == 0)
      throw new Error("url (#1) parameter must be specified");

    Value url = event[0];
    Value fetchData = null;
    FetchData data = null;

    if (!url.isString())
      throw new Error("url (#1) parameter must be string");

    if (event.length > 1) {
      fetchData = event[1];
      data = fetchData.as(FetchData.class);
    }

    if (fetchData == null || fetchData.isNull() || !fetchData.hasMembers())
      data = defaultData;

    call(url.toString(), data);
  }

  public void register(Value binding) {
    binding.putMember(this.name, (Fetcher) onCall());
  }

  @FunctionalInterface
  interface Fetcher {
    CompletableFuture<String> fetch(String url);
  }
}