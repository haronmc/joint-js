package ru.smokingplaya.jointjs;

import java.io.File;

@SuppressWarnings("all")
public class Executor {
    private final static String rootPath = "joint-js";
    private final static String scriptPath = rootPath + "\\plugins";
    private final static File rootFolder = new File(rootPath);
    private final static File scriptFolder = new File(scriptPath);
    protected final static File nodeModules = new File(rootPath + "\\node_modules");

    public static void initialize() {
        ensureFolderExists();
        loadScripts();
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
            JavaScriptPlugin plugin = new JavaScriptPlugin(file);
        }
    }
}