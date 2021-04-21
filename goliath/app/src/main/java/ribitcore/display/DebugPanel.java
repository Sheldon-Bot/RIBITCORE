package ribitcore.display;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.awt.*;

/**
 * Controls debug panel operations.
 */
public class DebugPanel {

    /**
     * The x pos of the panel.
     */
    private static final int PANEL_X = 20;

    /**
     * The y pos of the panel.
     */
    private static final int PANEL_Y = 1000;

    /**
     * The width of the panel.
     */
    private static final int PANEL_W = 400;

    /**
     * The height of the panel.
     */
    private static final int PANEL_H = 700;

    /**
     * If true, the panel should show. If false, the panel shouldn't.
     */
    private boolean shouldShow;

    /**
     * The sketch instance.
     */
    private final @NonNull DisplayApplet sketch;

    /**
     * Constructs {@link DebugPanel}.
     *
     * @param sketch sketch instance.
     */
    public DebugPanel(final @NonNull DisplayApplet sketch) {
        this.sketch = sketch;
    }

    /**
     * Runs the {@link DebugPanel}.
     */
    public void run() {
        if (sketch.keyPressed) {
            if (sketch.key == '+') {
                this.shouldShow = true;
            } else if (sketch.key == '-') {
                this.shouldShow = false;
            }
        }

        if (this.shouldShow) {
            sketch.fill(Color.BLACK.getRGB());
            sketch.rect(PANEL_X, PANEL_Y, PANEL_W, PANEL_H);
        }
    }

}
