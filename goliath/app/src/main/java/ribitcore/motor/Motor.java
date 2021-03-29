package ribitcore.motor;

import com.diozero.api.PwmOutputDevice;
import com.diozero.api.Servo;
import com.diozero.util.SleepUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.tinylog.Logger;
import uk.pigpioj.PigpioConstants;
import uk.pigpioj.PigpioInterface;

/**
 * Controls a physical server motor.
 */
public class Motor {

    private static final int PWM_MAX = 255;
    private static final int FREQ = 50;


    private final @NonNull PwmOutputDevice servo;
    private final int pin;

    /**
     * Constructs {@link Motor}.
     * <p>
     * Initializes {@code pigpio} on {@code pin}.
     *
     * @param pin    Pin number of the motor.
     */
    public Motor(
            final int pin
    ) {
        this.pin = pin;

        final int frequency = 50;
        servo = new PwmOutputDevice(pin, 50, 0.5F);
        System.out.println("------------- Servo "+pin+" -------------");
        System.out.println("value :"+servo.getValue());
        System.out.println("pwmFrequency :"+servo.getPwmFrequency());
    }

    public void setValue(final float angle) {
        this.servo.setValue(angle);
    }

    public float getValue() {
        return this.servo.getValue();
    }

}
