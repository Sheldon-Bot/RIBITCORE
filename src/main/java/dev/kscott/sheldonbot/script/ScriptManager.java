package dev.kscott.sheldonbot.script;

import dev.kscott.sheldonbot.App;
import dev.kscott.sheldonbot.utils.OSType;
import dev.kscott.sheldonbot.utils.ResourceUtils;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScriptManager {

    private static @NonNull String runningFolderPath = "UNK";

    private static @NonNull String targetPythonBinPath = "UNK";

    static {
        try {
            runningFolderPath = new File(ResourceUtils.getLocation(App.class).toURI()).getParent();
        } catch (URISyntaxException e) {
            runningFolderPath = "";
            e.printStackTrace();
        }

        final @NonNull OSType osType = OSType.getOSType();
        @MonotonicNonNull String envDir = "";

        if (osType == OSType.WINDOWS) {
            envDir = "Scripts";
        } else if (osType == OSType.UNIX) {
            envDir = "bin";
        } else {
            System.out.println("This application is not running on a supported OS! Things will likely break.");
        }

        targetPythonBinPath = Paths.get(runningFolderPath, "sheldon", "env", envDir).toAbsolutePath().toString();

        System.out.println(targetPythonBinPath);
    }

    public ScriptManager() {
        this.initializeEnvironment();
    }

    public void startServer() {
        Server server = new Server("localhost", 8765, "/", Map.of(), ScriptServerEndpoint.class);

        try {
            server.start();
            this.runScript("motor_client", "motor_client.py");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

    private void initializeEnvironment() {
        final @NonNull OSType osType = OSType.getOSType();

        if (osType == OSType.UNKNOWN) {
            return;
        }

        // Initialize python venv
        @NonNull List<String> commandArguments = new ArrayList<>();

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

        pythonSetupOutThread.interrupt();
        pythonSetupErrorThread.interrupt();

        // Install packages
        commandArguments = new ArrayList<>();
        commandArguments.add(Paths.get(targetPythonBinPath, OSType.isWindows() ? "pip.exe" : "pip").toAbsolutePath().toString());
        commandArguments.add("install");
        commandArguments.add("-r");
        commandArguments.add(Paths.get(runningFolderPath, "sheldon", "scripts", "requirements.txt").toAbsolutePath().toString());

        final @NonNull ProcessBuilder installerProcessBuilder = new ProcessBuilder(commandArguments);

        final @NonNull Process installerProcess;
        try {
            installerProcess = installerProcessBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        final @NonNull Thread pipErrorThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(installerProcess.getErrorStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf(line);
                }
            } catch (IOException ex) {
            }
        });

        pipErrorThread.start();

        final @NonNull Thread pipOutThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(installerProcess.getInputStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf("%s\n", line);
                }
            } catch (IOException ex) {
            }
        });

        pipOutThread.start();

        try {
            installerProcess.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Runs a Python script.
     *
     * @param id         the id to refer to this script instance as.
     * @param scriptPath the path to this script (as located in the resources package). i.e. the motor script would be {@code motor_client.py}.
     * @param args       the arguments to pass in to the script.
     */
    public @NonNull ScriptThread runScript(
            final @NonNull String id,
            final @NonNull String scriptPath,
            final @NonNull String... args
    ) {
        ScriptThread scriptThread = new ScriptThread(
                Paths.get(targetPythonBinPath, "python").toAbsolutePath().toString(),
                Paths.get(runningFolderPath, "sheldon", "scripts", scriptPath).toAbsolutePath().toString(),
                args
        );
        scriptThread.start();
        return scriptThread;
    }

}
