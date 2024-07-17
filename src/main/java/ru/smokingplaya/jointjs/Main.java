package ru.smokingplaya.jointjs;

import org.bukkit.plugin.java.JavaPlugin;
import org.graalvm.polyglot.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Joint_js extends JavaPlugin {
    @Override
    public void onEnable() {
        Path scriptPath = Paths.get("joint-js");

        Context context = Context.newBuilder("js")
                .option("js.commonjs-require-root", scriptPath.toString())
                .option("js.esm-eval-returns-exports", "true")
                .option("js.commonjs-require", "true")
                .allowAllAccess(true)
                .build();
        try {
            Value result = context.eval("js", "require('./script.mjs').main();");
            result.executeVoid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("GraalVM JS Plugin Disabled!");
    }
}
