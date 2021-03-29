package ribitcore.motor;

public enum MotorSpeed {
    REVERSE(1400),
    NEUTRAL(1500),
    FORWARDS(1600);

    private final float freq;

    MotorSpeed(final float freq) {
        this.freq = freq;
    }

    public float getFrequency() {
        return this.freq;
    }

}
