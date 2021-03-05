package dev.kscott.sheldonbot;

import dev.kscott.sheldonbot.script.ScriptThread;
import dev.kscott.sheldonbot.utils.ResourceCopy;
import org.checkerframework.checker.nullness.qual.NonNull;
import sun.misc.Signal;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.jar.JarFile;

public class RobotManager {

    private static final @NonNull String[] resourceDirectoriesToExtract = new String[]{
            "scripts"
    };

    /**
     * {@link ResourceCopy} instance.
     */
    private final @NonNull ResourceCopy resourceCopy;

//    private final @NonNull ScriptThread motorThread;

    public RobotManager() throws IOException {
        this.resourceCopy = new ResourceCopy();

        this.initializeResources();

        final @NonNull ScriptThread frontThread = startFront();
        final @NonNull ScriptThread backThread = startBack();
//        motorThread = startMotors();

        Signal.handle(new Signal("INT"), signal -> {
            frontThread.interrupt();
            backThread.interrupt();
//            motorThread.interrupt();
        });
    }

    private void initializeResources() throws IOException {
        final @NonNull Optional<JarFile> jarOpt = this.resourceCopy.jar(App.class);

        if (jarOpt.isEmpty()) {
            throw new RuntimeException("Failed to locate jar class.");
        }

        final @NonNull JarFile jarFile = jarOpt.get();

        for (final @NonNull String path : resourceDirectoriesToExtract) {
            this.resourceCopy.copyResourceDirectory(jarFile, path, new File("./temp/scripts"));
        }
    }

    public ScriptThread startFront() {
        ScriptThread thread = new ScriptThread("scripts/camera.py", "http://192.168.2.205:5001/api/image/front", "/dev/video0");
        thread.start();
        return thread;
    }

    public ScriptThread startBack() {
        ScriptThread thread = new ScriptThread("scripts/camera.py", "http://192.168.2.205:5001/api/image/back", "/dev/video2");
        thread.start();
        return thread;
    }

    public ScriptThread startMotors() {
        ScriptThread thread = new ScriptThread("scripts/motor_client.py");
        thread.onReady(() -> {
            System.out.println("motors ready");
            thread.send("hi!");
            thread.send("hi!");
            thread.send("hi!");
            thread.send("hi!");
        });
        thread.start();
        return thread;
    }

//    public ScriptThread getMotorThread() {
//        return motorThread;
//    }
}
