package ribitcore.motor;

import com.diozero.api.Servo;
import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.old.motor.MotorSpeed;

/**
 * Represents a physical {@link Servo} motor.
 */
public class Motor {

    /**
     * The frequency of the motor.
     */
    private static final int FREQUENCY = 50;

    /**
     * The {@link Servo} instance.
     */
    private final @NonNull Servo servo;

    /**
     * The {@link Servo.Trim} for the motor.
     */
    private final Servo.Trim trim;

    /**
     * Stores the value of the motor.
     */
    private @NonNull MotorSpeed motorSpeed;

    /**
     * Constructs {@link Motor}.
     *
     * @param pin the pin of the motor.
     */
    public Motor(final int pin) {
        this.trim = Servo.Trim.DEFAULT;
        this.servo = new Servo(pin, trim.getMidPulseWidthMs(), FREQUENCY, trim);
        this.motorSpeed = MotorSpeed.NEUTRAL;
        this.setSpeed(motorSpeed);
    }

    /**
     * Returns the speed of the motor.
     *
     * @return speed of the motor.
     */
    public @NonNull MotorSpeed getSpeed() {
        return this.motorSpeed;
    }

    /**
     * Sets the speed of the motor.
     *
     * @param speed speed of the motor.
     */
    public void setSpeed(final @NonNull MotorSpeed speed) {
        this.servo.setPulseWidthMs(speed.getFrequency());
    }

}
