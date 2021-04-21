package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.data.DataStore;
import ribitcore.display.DisplayController;
import ribitcore.motor.MotorController;

/**
 * The main class that controls RIBIT.
 */
public class RibitApp {

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


}
