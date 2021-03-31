package ribitcore.motor;

import com.diozero.api.Servo;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Controls a physical server motor.
 */
public class Motor {

    private static final int PWM_MAX = 255;
    private static final int FREQ = 50;


    private final @NonNull Servo servo;
    private final int pin;

    private final Servo.Trim trim = Servo.Trim.DEFAULT;

    private float lastValue;

    /**
     * Constructs {@link Motor}.
     *
     * @param pin Pin number of the motor.
     */
    public Motor(
            final int pin
    ) {
        this.pin = pin;

        final int frequency = 50;
        servo = new Servo(pin, trim.getMidPulseWidthMs(), frequency, trim);
    }

    public void setSpeed(final @NonNull MotorSpeed speed) {
        setValue(MotorSpeed.freqToValue(speed.getFrequency()));
    }

    /**
     * Between -1.0 and +1.0.
     * @param value int in rangeq
     */
    public void setValue(final float value) {
        this.servo.setPulseWidthMs(MotorSpeed.valueToFreq(value));
        lastValue = value;
    }

    public float getValue() {
        return this.lastValue;
    }

}
