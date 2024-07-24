package ru.smokingplaya.jointjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.command.CommandMap;
import org.graalvm.polyglot.Value;

public class Utils {
  // file utils
  public static String getFileExtension(File file) {
    return getFileExtension(file.getName());
  }

  public static String getFileExtension(String file) {
    if (file == null || file.lastIndexOf(".") == -1)
      return "";

    return file.substring(file.lastIndexOf(".") + 1);
  }

  public static String getFileName(File file) {
    return getFileName(file.getName());
  }

  public static String getFileName(String file) {
    return file.split("\\.")[0];
  }

  public static String[] getOSCommand() {
    String osName = System.getProperty("os.name").toLowerCase();

    if (osName.contains("windows")) {
      return new String[] { "cmd", "/C" };
    } else if (osName.contains("linux")) {
      return new String[] { "bash", "-c" };
    } else if (osName.contains("mac")) {
      return new String[] { "zsh", "-c" };
    } else {
      throw new IllegalStateException("Unsupported OS: " + osName);
    }
  }

  public static ArrayList<String> readCommand(String command) throws IOException, InterruptedException {
    String[] cmd = getOSCommand();

    Process process = new ProcessBuilder(cmd[0], cmd[1], command)
        .start();

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    ArrayList<String> lines = new ArrayList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      lines.add(line);
    }

    return lines;
  }

  public static String toString(ArrayList<String> list) {
    StringBuilder sb = new StringBuilder();
    for (String str : list) {
      sb.append(str).append(System.lineSeparator());
    }

    return sb.toString();
  }

  // Value hacks

  public static String getPluginFolder(Value ent) {
    return ent.getContext()
        .getBindings("js")
        .getMember("pluginFolder")
        .toString();
  }

  public static CommandMap getCommandMap()
      throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    Field bukkitMap = Main.server.getClass().getDeclaredField("commandMap");
    bukkitMap.setAccessible(true);
    return (CommandMap) bukkitMap.get(Main.server);
  }

  public static Object getPrivateField(Object object, String field) throws SecurityException,
      NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Class<?> clazz = object.getClass();
    Field objectField = clazz.getDeclaredField(field);
    objectField.setAccessible(true);
    Object result = objectField.get(object);
    objectField.setAccessible(false);
    return result;
  }
}
