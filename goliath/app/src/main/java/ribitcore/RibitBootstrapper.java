package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.motor.MotorController;
import uk.pigpioj.PigpioConstants;
import uk.pigpioj.PigpioInterface;
import uk.pigpioj.PigpioJ;

import java.io.IOException;

public class RibitBootstrapper {

    /**
     * {@link PigpioJ} implementation.
     */
    private final @NonNull PigpioInterface pigpio = PigpioJ.getImplementation();

    /**
     * Pigpio version.
     */
    private final int pigpioVersion = pigpio.getVersion();

    public void start() {
        // version check
        if (pigpioVersion < 0) {
            System.out.println("Invalid version: "+pigpioVersion+", exiting...");
            return;
        } else {
            System.out.println("version: " + pigpioVersion);
        }

        new Thread(() -> {
            final @NonNull MotorController motorController = new MotorController(pigpio);

            while (true) {
                motorController.forwards();
            }
        }).start();
    }

}
