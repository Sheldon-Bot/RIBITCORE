package ribitcore.motor;

/**
 * Represents the speed of a motor.
 *
 * <p>
 * Motor speed frequency = the physical frequency sent to the motor
 * Motor speed value = the representation of the frequency as a number between -1..1
 */
public enum MotorSpeed {

    /**
     * The reverse motor speed.
     */
    REVERSE(1.1F),

    /**
     * The neutral motor speed.
     */
    NEUTRAL(1.49F),

    /**
     * The forwards motor speed.
     */
    FORWARDS(1.8F);

    /**
     * The frequency of the motor speed (1.1F..1.8F).
     */
    private final float freq;

    /**
     * Constructs {@link MotorSpeed}.
     *
     * @param freq the frequency (1.1F..1.8F).
     */
    MotorSpeed(final float freq) {
        this.freq = freq;
    }

    /**
     * Returns the frequency value (1.1F..1.8F).
     * @return the frequency value (1.1F..1.8F).
     */
    public float getFrequency() {
        return this.freq;
    }

    /**
     * Maps a frequency to a motor speed value.
     *
     * @param freq freq to convert (1.1F..1.8F).
     * @return {@code freq} as a value (-1..1).
     */
    public static double freqToValue(double freq) {
        return mapOneRangeToAnother(freq, REVERSE.freq, FORWARDS.freq, -1, 1, 3);

    }

    /**
     * Maps a motor speed value to a frequency.
     *
     * @param value value to convert (-1..1).
     * @return {@code value} as a frequency (1.1F..1.8F).
     */
    public static double valueToFreq(double value) {
        return mapOneRangeToAnother(value, -1, 1, REVERSE.freq, FORWARDS.freq, 3);
    }

    /**
     * Maps a value between 0..1024 to -1..1.
     *
     * @param value value to map (0..1024)
     * @return mapped value (-1..1)
     */
    public static double thousandsValueToOneValue(double value) {
        return mapOneRangeToAnother(
                value,
                0,
                1024,
                -1,
                1,
                3
        );
    }

    public static double mapOneRangeToAnother(double sourceNumber, double fromA, double fromB, double toA, double toB, int decimalPrecision) {
        double deltaA = fromB - fromA;
        double deltaB = toB - toA;
        double scale = deltaB / deltaA;
        double negA = -1 * fromA;
        double offset = (negA * scale) + toA;
        double finalNumber = (sourceNumber * scale) + offset;
        int calcScale = (int) Math.pow(10, decimalPrecision);
        return (double) Math.round(finalNumber * calcScale) / calcScale;
    }
}
