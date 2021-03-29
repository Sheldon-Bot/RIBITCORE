package ribitcore.motor;

import com.diozero.util.SleepUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.pigpioj.PigpioInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Controls the RIBIT motor array.
 */
public class MotorController {

    private final @NonNull Motor motor_main_1;

    private final @NonNull Motor motor_main_2;

    public MotorController(
            final @NonNull PigpioInterface pigpio
    ) {
        motor_main_1 = new Motor(23);
        motor_main_2 = new Motor(24);

    }

    public void forwards() {
        motor_main_1.setValue(1F);
    }

    public void backwards() {
    }

    public void left() {
    }

    public void right() {
    }

}
