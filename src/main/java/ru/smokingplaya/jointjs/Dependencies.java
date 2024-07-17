package ru.smokingplaya.jointjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Dependencies {
    protected static Logger logger = Main.logger;
    public static boolean check() {
        if (!checkProcess("npm", "-v", false, null)) {
            logger.severe("Unable to find npm on PC. Install npm from https://npmjs.com/");
            return false;
        }

        if (!checkProcess("tsc", "-v", false, null)) {
            logger.info("Unable to find tsc on PC.");
            logger.info("Installing from command: npm install -g typescript");
            checkProcess("npm install -g", "typescript", true, "(npm) tsc install");
        }

        return true;
    }

    // todo @ refactor
    private static String[] getOSCommand() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("windows")) {
            return new String[] {"cmd", "/C"};
        } else if (osName.contains("linux")) {
            return new String[] {"bash", "-c"};
        } else if (osName.contains("mac")) {
            return new String[] {"zsh", "-c"};
        } else {
            throw new IllegalStateException("Unsupported OS: " + osName);
        }
    }

    // todo @ refactor
    private static boolean checkProcess(String name, String arg, boolean needToPrint, String printPrefix) {
        String[] cmds = getOSCommand();

        try {
            Process proc = new ProcessBuilder(cmds[0], cmds[1], name, arg).start();

            if (needToPrint) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty())
                        continue;
                    System.out.println("[" + printPrefix + "] " + line);
                }
            }

            int exitCode = proc.waitFor();
            return exitCode == 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}