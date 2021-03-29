package ribitcore.motor;

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


    private final @NonNull Servo servo;
    private final int pin;

    private final Servo.Trim trim = Servo.Trim.DEFAULT;

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
        servo = new Servo(pin, trim.getMidPulseWidthMs(), frequency, trim);
        System.out.println("------------- Servo "+pin+" -------------");
        System.out.println("pulseWidthMs :"+servo.getPulseWidthMs());
        System.out.println("angle :"+servo.getAngle());
        System.out.println("value :"+servo.getValue());
    }

    public void on() {
        this.servo.setPulseWidthMs(2F);
        this.servo.setValue(1);
    }

    public void off() {
    }

    public void setSpeed(final @NonNull MotorSpeed speed) {
        this.servo.setPulseWidthMs(speed.getFrequency());
        this.servo.setValue(1);
    }

    public void setSpeed(final float angle) {
        this.servo.setAngle(angle);
    }

    public float getAngle() {
        return this.servo.getAngle();
    }

}
