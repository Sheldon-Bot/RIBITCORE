package dev.kscott.sheldonbot;

import dev.kscott.sheldonbot.script.ScriptManager;
import dev.kscott.sheldonbot.utils.ResourceUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.jar.JarFile;

public class RobotManager {

    /**
     * Stores a {@link String} array, where each {@link String} is the name of a directory to extract out of the jar.
     */
    private static final @NonNull String[] resourceDirectoriesToExtract = new String[]{
            "scripts"
    };

    /**
     * {@link ResourceUtils} instance.
     */
    private final @NonNull ScriptManager scriptManager;

    public RobotManager() throws IOException {
        this.initializeResources();

        this.scriptManager = new ScriptManager();
        this.scriptManager.startServer();
    }

    private void initializeResources() throws IOException {
        final @NonNull Optional<JarFile> jarOpt = ResourceUtils.jar(App.class);

        if (jarOpt.isEmpty()) {
            throw new RuntimeException("Failed to locate jar class.");
        }

        final @NonNull JarFile jarFile = jarOpt.get();

        for (final @NonNull String path : resourceDirectoriesToExtract) {
            ResourceUtils.copyResourceDirectory(jarFile, path, new File("./sheldon/scripts"));
        }
    }
}
