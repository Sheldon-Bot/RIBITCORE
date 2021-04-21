package ribitcore.display;

import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PApplet;
import ribitcore.data.DataStore;
import ribitcore.display.debug.DebugPanel;

import java.awt.*;

/**
 * The processing applet that handles the display.
 */
public class DisplayApplet extends PApplet {

    /**
     * The color of the background.
     */
    private static final @NonNull Color COLOR_BG = new Color(13, 71, 105);

    /**
     * The color of the main text.
     */
    private static final @NonNull Color COLOR_TEXT_MAIN = new Color(144, 214, 160);

    /**
     * The {@link DataStore} instance.
     */
    private final @NonNull DataStore dataStore;

    /**
     * The {@link DebugPanel} instance.
     */
    private final @NonNull DebugPanel debugPanel;

    /**
     * Constructs {@link DisplayApplet}.
     *
     * @param dataStore data store.
     */
    public DisplayApplet(final @NonNull DataStore dataStore) {
        this.dataStore = dataStore;
        this.debugPanel = new DebugPanel(this, dataStore);
    }

    /**
     * Sets the initial settings of the DisplayApplet.
     */
    public void settings() {
        fullScreen(0);
        size(displayWidth, displayHeight);
    }

    /**
     * Renders the frame of the DisplayApplet.
     */
    public void draw() {
        // Input checks
        handleQuit();

        // Display methods
        background(COLOR_BG.getRGB());
        drawBottomBar();
        this.debugPanel.run();
    }

    /**
     * Draws a text to the screen.
     *
     * @param text text to display.
     * @param x    x-coordinate of the text.
     * @param y    y-coordinate of the text.
     */
    private void drawText(final @NonNull String text, final int x, final int y, final int textSize) {
        textSize(textSize);
        fill(COLOR_TEXT_MAIN.getRGB());
        text(text, x, y);
    }

    /**
     * Draws the bottom bar.
     */
    private void drawBottomBar() {
        textAlign(CENTER);
        drawText("RIBIT", 640, 950, 30);
        drawText("Robotic Interactive Broadcast & IoT Telecommunicator", 640, 980, 25);

        // draw date
        textAlign(LEFT);
        drawText("Start date: " + this.dataStore.getTime().toString(), 10, 1010, 10);
        drawText("Frame: " + frameCount, 10, 1000, 10);
        drawText("Keycode: " + keyCode, 10, 990, 10);
    }

    /**
     * Handles quit input.
     */
    private void handleQuit() {
        if (keyCode == 81) {
            System.exit(0);
        }
    }
}
