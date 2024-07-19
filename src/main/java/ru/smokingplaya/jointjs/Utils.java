package ru.smokingplaya.jointjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
}
