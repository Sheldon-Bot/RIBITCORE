package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.display.DisplayApplet;
import ribitcore.display.DisplayController;
import ribitcore.motor.MotorController;

/**
 * The main class that controls RIBIT.
 */
public class RibitApp {

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
        this.motorController = new MotorController();
        this.displayController = new DisplayController();
    }

    /**
     * Starts the {@link RibitApp}.
     */
    public void start() {
        this.motorController.run();
        this.displayController.run();
    }


}
