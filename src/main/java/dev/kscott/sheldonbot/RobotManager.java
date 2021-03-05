package dev.kscott.sheldonbot;

import dev.kscott.sheldonbot.script.ScriptManager;
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

    private final @NonNull ScriptManager scriptManager;

//    private final @NonNull ScriptThread motorThread;

    public RobotManager() throws IOException {
        this.resourceCopy = new ResourceCopy();

        this.initializeResources();

        this.scriptManager = new ScriptManager();
    }

    private void initializeResources() throws IOException {
        final @NonNull Optional<JarFile> jarOpt = this.resourceCopy.jar(App.class);

        if (jarOpt.isEmpty()) {
            throw new RuntimeException("Failed to locate jar class.");
        }

        final @NonNull JarFile jarFile = jarOpt.get();

        for (final @NonNull String path : resourceDirectoriesToExtract) {
            this.resourceCopy.copyResourceDirectory(jarFile, path, new File("./sheldon/scripts"));
        }
    }
    //        return motorThread;
//    }
}
