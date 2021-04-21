package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.data.DataStore;
import ribitcore.display.DisplayController;
import ribitcore.motor.MotorController;
import ribitcore.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.jar.JarFile;

/**
 * The main class that controls RIBIT.
 */
public class RibitApp {

    /**
     * A list of directory names to extract.
     */
    private static final @NonNull String[] RESOURCE_DIRS = new String[]{
            "py",
            "font"
    };

    /**
     * The {@link DataStore} instance.
     */
    private final @NonNull DataStore dataStore;

    /**
     * The {@link MotorController} instance.
     */
    private final @NonNull MotorController motorController;

    /**
     * The {@link DisplayController} instance.
     */
    private final @NonNull DisplayController displayController;

    /**
     * Constructs {@link RibitApp}.
     */
    public RibitApp() {
        try {
            this.initializeResources();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.dataStore = new DataStore();
        this.motorController = new MotorController(dataStore);
        this.displayController = new DisplayController(dataStore);
    }

    /**
     * Starts the {@link RibitApp}.
     */
    public void start() {
//        this.motorController.run();
        this.displayController.run();
    }

    /**
     * Initializes resources.
     * @throws IOException if io failed.
     */
    private void initializeResources() throws IOException {
        final @NonNull Optional<JarFile> jarOpt = ResourceUtils.jar(App.class);

        if (jarOpt.isEmpty()) {
            throw new RuntimeException("Failed to locate jar class.");
        }

        final @NonNull JarFile jarFile = jarOpt.get();

        for (final @NonNull String path : RESOURCE_DIRS) {
            ResourceUtils.copyResourceDirectory(jarFile, path, new File("./sheldon"));
        }
    }


}
