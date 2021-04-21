package ribitcore.old.motor;

public enum MotorSpeed {
    REVERSE(1.1F),
    NEUTRAL(1.49F),
    FORWARDS(1.8F);

    private final float freq;

    MotorSpeed(final float freq) {
        this.freq = freq;
    }

    public float getFrequency() {
        return this.freq;
    }

    public static double freqToValue(double value) {
        return mapOneRangeToAnother(value, REVERSE.freq, FORWARDS.freq, -1, 1, 3);

    }

    public static double valueToFreq(double value) {
        return mapOneRangeToAnother(value, -1, 1, REVERSE.freq, FORWARDS.freq, 3);
    }

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
