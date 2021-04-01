package ribitcore.motor;

import com.diozero.api.Servo;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Controls a physical server motor.
 */
public class Motor {

    private final @NonNull Servo servo;

    private final int pin;

    private final Servo.Trim trim = Servo.Trim.DEFAULT;

    private double lastValue;

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
     *
     * @param value int in range
     */
    public void setValue(final double value) {
        this.servo.setPulseWidthMs((float) MotorSpeed.valueToFreq(value));
        lastValue = value;
    }

    public double getValue() {
        return this.lastValue;
    }

}
