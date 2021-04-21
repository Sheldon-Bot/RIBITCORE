package ribitcore.old.motor;

import com.diozero.util.SleepUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.pigpioj.PigpioInterface;

/**
 * Controls the RIBIT motor array.
 */
public class MotorController {

    private static @NonNull MotorController motorController;

    private static @NonNull Thread thread;

    public static void start(final @NonNull PigpioInterface pigpioInterface) {
        motorController = new MotorController(pigpioInterface);
        thread = new Thread(() -> {
            while (true) {
                System.out.println("MC FORWARDS");
                motorController.forwards();
                SleepUtil.sleepSeconds(1);

                System.out.println("MC BACKWARDS");
                motorController.backwards();
                SleepUtil.sleepSeconds(1);

                System.out.println("MC LEFT");
                motorController.left();
                SleepUtil.sleepSeconds(1);

                System.out.println("MC RIGHT");
                motorController.right();
                SleepUtil.sleepSeconds(1);

                System.out.println("MC STOP");
                motorController.stop();
                SleepUtil.sleepSeconds(1);
            }
        });

        thread.start();

    }

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
        motor_main_1.setValue(cleanseInputNumber(motor1));
        motor_main_2.setValue(cleanseInputNumber(motor2));
        System.out.println("1: " + MotorSpeed.valueToFreq(motor_main_1.getValue()) + ", 2: " + MotorSpeed.valueToFreq(motor_main_2.getValue()));
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

    private double cleanseInputNumber(final double value) {
        if ((-0.2 < value && value < 0.2)) {
            return 0;
        }

        return value;
    }

}
