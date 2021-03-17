package dev.kscott.sheldonbot.script;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScriptInstance {

    private final @NonNull ScriptThread scriptThread;

    private final @Nullable Thread errorListenerThread;

    private final @Nullable Thread printListenerThread;

    private final @Nullable Process scriptProcess;

    public ScriptInstance(
            final @NonNull String id,
            final @NonNull String pythonPath,
            final @NonNull String scriptPath,
            final @NonNull String... args
    ) {
        this.scriptThread = new ScriptThread(pythonPath, scriptPath, args);
        this.scriptThread.start();

        scriptProcess = this.scriptThread.getProcess();

        if (scriptProcess == null) {
            System.out.println("Failed to get script process for " + id + ", aborting listeners.");
            this.errorListenerThread = null;
            this.printListenerThread = null;
            return;
        }

        errorListenerThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(scriptProcess.getErrorStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf(line);
                }
            } catch (IOException ex) {
            }
        });

        errorListenerThread.start();

        printListenerThread = new Thread(() -> {
            BufferedReader ir = new BufferedReader(new InputStreamReader(scriptProcess.getInputStream()));
            String line = null;
            try {
                while ((line = ir.readLine()) != null) {
                    System.out.printf("%s\n", line);
                }
            } catch (IOException ex) {
            }
        });

        printListenerThread.start();
    }

    public void writeString(final @NonNull String string) {
        final BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(scriptProcess.getOutputStream()));

        try {
            bf.write(string);
            bf.newLine();
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
