package ribitcore.motor;

import org.checkerframework.checker.nullness.qual.NonNull;
import uk.pigpioj.PigpioInterface;

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
        motor_main_1.setSpeed(MotorSpeed.FORWARDS);
        motor_main_2.setSpeed(MotorSpeed.FORWARDS);
    }

    public void backwards() {
        motor_main_1.setSpeed(MotorSpeed.REVERSE);
        motor_main_2.setSpeed(MotorSpeed.REVERSE);
    }

    public void left() {
        motor_main_1.setSpeed(MotorSpeed.REVERSE);
        motor_main_2.setSpeed(MotorSpeed.FORWARDS);
    }

    public void right() {
        motor_main_1.setSpeed(MotorSpeed.FORWARDS);
        motor_main_2.setSpeed(MotorSpeed.REVERSE);
    }

    public void stop() {
        motor_main_1.setSpeed(MotorSpeed.NEUTRAL);
        motor_main_2.setSpeed(MotorSpeed.NEUTRAL);
    }

    /**
     * @param motor1 motor1 %
     * @param motor2 motor2 %
     */
    public void setValues(double motor1, double motor2) {
        motor_main_1.setValue(motor1);
        motor_main_2.setValue(motor2);
        System.out.println("1: " + motor1 + ", 2: " + motor2);
//
//        float motor1Value = cleanseInputNumber(x);
//        float motor2Value = cleanseInputNumber(x);
//
//        final float turnFactor = 0.9F;
//
//        if (y < 0) {
//            motor2Value =+ y*turnFactor;
//        } else if (y > 0) {
//            motor1Value =+ y*turnFactor;
//        }
//
//        motor_main_1.setValue(motor1Value);
//        motor_main_2.setValue(motor2Value);
//        System.out.println("[motor_val] "+motor1Value + " - " +motor2Value);
    }

    private float cleanseInputNumber(final float value) {
        if ((-0.2 < value && value < 0.2)) {
            return 0;
        }

        return value;
    }

}
