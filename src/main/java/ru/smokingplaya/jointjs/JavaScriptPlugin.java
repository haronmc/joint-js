package ru.smokingplaya.jointjs;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getServer;

/**
 * JavaScriptPlugin
 * Как все работает:
 *  Этот класс создает отдельный контекст для каждого плагина
 *  Все.
 */
public class JavaScriptPlugin {
    private final static String entryPoint = "plugin.js";
    protected static HashMap<String, JavaScriptPlugin> plugins = new HashMap<>();
    private final File scriptBase;
    private final File scriptEntryPoint;
    private final Logger logger = Main.logger;

    JavaScriptPlugin(File file) {
        scriptEntryPoint = file.isFile() ? file : new File(file.getPath() + "\\" + entryPoint);
        scriptBase = file;

        plugins.put(scriptEntryPoint.getName(), this);

        execute();
        initializeWatchThread();
    }

    private Context generateContext() {
        Context context =  Context.newBuilder("js")
            .allowAllAccess(true)
            .option("js.ecmascript-version", "2022")
            .option("js.commonjs-require", "true")
            .option("js.commonjs-require-cwd", Executor.nodeModules.getPath())
            .allowIO(true)
            .build();

        context.getBindings("js").putMember("core", getServer());

        return context;
    }

    public void execute() {
        try {
            generateContext().eval(Source.newBuilder("js", scriptEntryPoint).build());
        } catch (IOException err) {
            logger.severe("Unable to execute \"" + scriptBase.getPath() + "\": " + err.toString());
        }
    }

    public void initializeWatchThread() {}
}
