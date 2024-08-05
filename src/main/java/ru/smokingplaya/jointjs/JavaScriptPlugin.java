package ru.smokingplaya.jointjs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import ru.smokingplaya.jointjs.functions.Functions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

public class JavaScriptPlugin {
  public static HashMap<String, JavaScriptPlugin> plugins = new HashMap<>();
  private final static String entryPoint = "plugin.ts";
  private final File scriptBase;
  private final File scriptEntryPoint;
  private final Logger logger = Main.logger;

  JavaScriptPlugin(File folder) {
    scriptEntryPoint = new File(folder, entryPoint);
    scriptBase = folder;

    plugins.put(scriptEntryPoint.getPath(), this);

    execute();
    initializeWatchThread();
  }

	private Context generateContext() {
    Context context = Context.newBuilder("js")
        .allowAllAccess(true)
        .option("js.ecmascript-version", "2022")
        .option("js.commonjs-require", "true")
        .option("js.commonjs-require-cwd", Executor.nodeModules.getPath())
        .allowIO(true)
        .build();

    Value bind = context.getBindings("js");
    bind.putMember("pluginFolder", scriptBase.getName());
    bind.putMember("server", getServer());

    Functions.Register(bind);

    return context;
  }

  private void executeTypeScript() {
    try {
      ArrayList<String> result = Utils.readCommand("bun build " + scriptEntryPoint.getAbsolutePath());

      execute(Source.newBuilder("js", Utils.toString(result), scriptEntryPoint.toPath().toString()).build());
    } catch (Exception err) {
      logError(err);
    }
  }

  private void logError(Exception err) {
    logger.severe(scriptBase.getPath() + ": " + err.getMessage());
  }

  private void execute(Source src) {
    generateContext().eval(src);
  }

  public void execute() {
    if (Utils.getFileExtension(scriptEntryPoint).equals("ts")) {
      executeTypeScript();
      return;
    }

    try {
      Source src = Source.newBuilder("js", scriptEntryPoint)
          .mimeType("application/javascript+module")
          .build();

      execute(src);
    } catch (IOException err) {
      logError(err);
    }
  }

  // todo @
  public void initializeWatchThread() {
  }
}
