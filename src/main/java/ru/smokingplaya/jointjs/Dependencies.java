package ru.smokingplaya.jointjs;

import java.io.IOException;
import java.util.logging.Logger;

public class Dependencies {
    protected static Logger logger = Main.logger;
    public static boolean check() {
        if (!checkProcess("bun", "-v")) {
            logger.severe("Unable to find bun.sh on PC. Install bun.sh from https://bun.sh/");
            return false;
        }

        return true;
    }

    // todo @ refactor
    private static boolean checkProcess(String name, String arg) {
        String[] cmds = Utils.getOSCommand();

        try {
            Process proc = new ProcessBuilder(cmds[0], cmds[1], name, arg).start();

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