package ribitcore.motor;

/**
 * Represents a physical RIBIT motor.
 */
public class Motor {

    private final int pin;

    public Motor(final int pin) {
        this.pin = pin;
    }

    /**
     * Stores the speeds a {@link Motor} can run at.
     */
    public enum Speed {

        FORWARD_FAST(4),
        FORWARD_MED(5),
        FORWARD_SLOW(6),
        NEUTRAL(7),
        REVERSE_SLOW(8),
        REVERSE_MED(9),
        REVERSE_FAST(10);

        /**
         * The integer value of the speed.
         */
        private final int speed;

        /**
         * Constructs a {@link Speed}.
         *
         * @param speed speed as integer
         */
        Speed(final int speed) {
            this.speed = speed;
        }

        /**
         * Returns the integer value of this {@link Speed} value.
         *
         * @return integer value.
         */
        public int getSpeed() {
            return speed;
        }
    }

}
