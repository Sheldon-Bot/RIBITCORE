package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.input.InputClient;
import ribitcore.motor.MotorController;
import uk.pigpioj.PigpioInterface;
import uk.pigpioj.PigpioJ;

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
            System.out.println("Invalid version: " + pigpioVersion + ", exiting...");
            return;
        } else {
            System.out.println("version: " + pigpioVersion);
        }

        final @NonNull MotorController motorController = new MotorController(pigpio);

//        new Thread(() -> {
//            while (true) {
//                System.out.println("MC FORWARDS");
//                motorController.forwards();
//                SleepUtil.sleepSeconds(1);
//
//                System.out.println("MC BACKWARDS");
//                motorController.backwards();
//                SleepUtil.sleepSeconds(1);
//
//                System.out.println("MC LEFT");
//                motorController.left();
//                SleepUtil.sleepSeconds(1);
//
//                System.out.println("MC RIGHT");
//                motorController.right();
//                SleepUtil.sleepSeconds(1);
//
//                System.out.println("MC STOP");
//                motorController.stop();
//                SleepUtil.sleepSeconds(1);
//            }
//        }).start();

        new Thread(() -> {
            try {
                new InputClient(motorController).connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
