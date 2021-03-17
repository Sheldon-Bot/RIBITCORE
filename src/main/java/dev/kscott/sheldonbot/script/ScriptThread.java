package dev.kscott.sheldonbot.script;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptThread extends Thread {

    private final @NonNull String fileName;

    private final @NonNull List<String> command;

    private @MonotonicNonNull Process process;

    private @Nullable Runnable readyCallback;

    public ScriptThread(
            final @NonNull String pythonPath,
            final @NonNull String fileName,
            final @NonNull String... args
    ) {
        this.fileName = fileName;
        this.command = new ArrayList<>();

        this.command.add(pythonPath);
        this.command.add(fileName);
        this.command.addAll(Arrays.asList(args));
    }

    public void send(final @NonNull String data) {
        if (this.process == null) {
            System.out.println("process is null");
            return;
        }

        try {
            this.process.getOutputStream().write((data).getBytes(StandardCharsets.UTF_8));
            this.process.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ProcessBuilder pb = new ProcessBuilder(command);

        pb.redirectErrorStream(true);
        try {
            process = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Reader reader = new InputStreamReader(process.getInputStream());
        BufferedReader bf = new BufferedReader(reader);
        String s = null;

        while (true) {
            if (this.isInterrupted()) {
                break;
            }
            try {
                if ((s = bf.readLine()) == null) break;
                if (s.equals("ready")) {
                    if (this.readyCallback != null) {
                        this.readyCallback.run();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("["+fileName+"] "+s);
        }
    }

    public @Nullable Process getProcess() {
        return process;
    }
}
