package ribitcore.motor;

import com.diozero.util.SleepUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import ribitcore.thread.ThreadManager;

/**
 * Controls motor functions.
 */
public class MotorController extends ThreadManager {

    /**
     * The left motor.
     */
    private final @NonNull Motor leftMotor;

    /**
     * The right motor.
     */
    private final @NonNull Motor rightMotor;

    /**
     * Constructs {@link MotorController}.
     */
    public MotorController() {
        this.leftMotor = new Motor(23);
        this.rightMotor = new Motor(24);
    }

    /**
     * Runs the thread.
     */
    @Override
    public void runThread() {
        while (true) {
            setSpeedNice(TankSpeed.FORWARDS);
            setSpeedNice(TankSpeed.BACKWARDS);
            setSpeedNice(TankSpeed.LEFT);
            setSpeedNice(TankSpeed.RIGHT);
        }
    }

    /**
     * Sets the speed.
     */
    private void setSpeedNice(final @NonNull TankSpeed speed) {
        System.out.println(speed);
        this.setTankSpeed(speed);
        SleepUtil.sleepSeconds(1);
    }

    /**
     * Sets the robot's tank speed.
     *
     * @param speed the speed of the robot.
     */
    private void setTankSpeed(final @NonNull TankSpeed speed) {
        final @NonNull MotorSpeed leftSpeed = switch (speed) {
            case BACKWARDS, RIGHT -> MotorSpeed.REVERSE;
            case FORWARDS, LEFT -> MotorSpeed.FORWARDS;
        };

        final @NonNull MotorSpeed rightSpeed = switch (speed) {
            case BACKWARDS, LEFT -> MotorSpeed.REVERSE;
            case FORWARDS, RIGHT -> MotorSpeed.FORWARDS;
        };

        this.leftMotor.setSpeed(leftSpeed);
        this.rightMotor.setSpeed(rightSpeed);
    }

}
