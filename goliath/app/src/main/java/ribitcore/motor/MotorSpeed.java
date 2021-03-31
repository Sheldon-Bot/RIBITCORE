package ribitcore.motor;

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

    public static float freqToValue(float freq) {
        return (freq - REVERSE.freq)
                * (2 - 1)
                / (FORWARDS.freq - REVERSE.freq)
                + 1;
    }

    public static float valueToFreq(float value) {
        return (value - 1)
                * (FORWARDS.freq - REVERSE.freq)
                / (2 - 1)
                + REVERSE.freq;
    }

}
