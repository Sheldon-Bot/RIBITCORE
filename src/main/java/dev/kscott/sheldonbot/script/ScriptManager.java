package dev.kscott.sheldonbot.script;

import dev.kscott.sheldonbot.utils.OSType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScriptManager {

    public ScriptManager() {
        this.initializeEnvironment();
    }

    private void initializeEnvironment() {
        final @NonNull OSType osType = OSType.getOSType();

        if (osType == OSType.UNKNOWN) {
            return;
        }

        // Initialize python venv
        final @NonNull List<String> commandArguments = new ArrayList<>();

        if (osType == OSType.WINDOWS) {
            commandArguments.add("py");
            commandArguments.add("-3");
        } else if (osType == OSType.UNIX) {
            commandArguments.add("python3");
        }

        commandArguments.add("-m");
        commandArguments.add("venv");
        commandArguments.add("sheldon/env");

        ProcessBuilder pythonProcessBuilder = new ProcessBuilder(commandArguments);
        Process pythonSetupProcess;

        try {
            pythonSetupProcess = pythonProcessBuilder.start();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        final @NonNull Thread pythonSetupErrorThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(pythonSetupProcess.getErrorStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf(line);
                }
            } catch (IOException ex) {
            }
        });

        pythonSetupErrorThread.start();

        final @NonNull Thread pythonSetupOutThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(pythonSetupProcess.getInputStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf("%s\n", line);
                }
            } catch (IOException ex) {
            }
        });

        try {
            pythonSetupProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        pythonSetupOutThread.start();

        try {
            pythonSetupProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs a Python script.
     *
     * @param id         the id to refer to this script instance as.
     * @param scriptPath the path to this script (as located in the resources package). i.e. the motor script would be {@code scripts/motor.py}.
     * @param args       the arguments to pass in to the script.
     */
    public void runScript(
            final @NonNull String id,
            final @NonNull String scriptPath,
            final @NonNull String... args
    ) {
        ScriptThread scriptThread = new ScriptThread(scriptPath, args);
        scriptThread.start();
    }

}
