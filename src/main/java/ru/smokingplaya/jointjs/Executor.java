package ru.smokingplaya.jointjs;

import java.io.File;
import java.util.Arrays;

@SuppressWarnings("all")
public class Executor {
    private final static String rootPath = "joint-js";
    private final static String[] allowedExtensions = new String[] {"js", "mjs", "cjs", "ts"};
    private final static File rootFolder = new File(rootPath);
    private final static File scriptFolder = new File(rootPath, "plugins");
    protected final static File nodeModules = new File(rootPath, "node_modules");

    public static void initialize() {
        ensureFolderExists();
        loadScripts();
    }

    private static boolean fileAllowed(File file) {
        return Arrays.asList(allowedExtensions).contains(Utils.getFileExtension(file));
    }

    private static void ensureFolderExists(File folder) {
        if (!folder.exists() || !folder.isDirectory())
            folder.mkdir();
    }

    private static void ensureFolderExists() {
        ensureFolderExists(rootFolder);
        ensureFolderExists(scriptFolder);
        ensureFolderExists(nodeModules);
    }

    private static void loadScripts() {
        for (File file : Executor.scriptFolder.listFiles()) {
            if (file.isFile() && !fileAllowed(file))
                continue;

            JavaScriptPlugin plugin = new JavaScriptPlugin(file);
        }
    }
}