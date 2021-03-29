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

}
