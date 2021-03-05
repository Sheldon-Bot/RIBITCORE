package dev.kscott;

import dev.kscott.script.ScriptThread;
import org.checkerframework.checker.nullness.qual.NonNull;
import sun.misc.Signal;

public class RobotManager {

//    private final @NonNull ScriptThread motorThread;

    public RobotManager() {
        System.out.println("Starting robot programs...");

        final @NonNull ScriptThread frontThread = startFront();
        final @NonNull ScriptThread backThread = startBack();
//        motorThread = startMotors();

        Signal.handle(new Signal("INT"), signal -> {
            frontThread.interrupt();
            backThread.interrupt();
//            motorThread.interrupt();
        });
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
