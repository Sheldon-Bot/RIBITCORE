package ribitcore.motor;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.thread.ThreadManager;

/**
 * Controls motor functions.
 */
public class MotorController extends ThreadManager {

    /**
     * The left motor.
     */
    private final @NonNull Motor leftMotor;

    /**
     * The right motor.
     */
    private final @NonNull Motor rightMotor;

    public MotorController() {
        this.leftMotor = new Motor(23);
        this.rightMotor = new Motor(24);
    }

    @Override
    public void runThread() {

    }

}
