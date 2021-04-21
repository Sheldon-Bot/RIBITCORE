package ribitcore.display;

import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PApplet;

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
    }

    /**
     * Draws a text to the screen.
     *
     * @param text text to display.
     * @param x x-coordinate of the text.
     * @param y y-coordinate of the text.
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
    }

    /**
     * Handles quit input.
     */
    private void handleQuit() {
        if (keyPressed) {
            if (keyCode == 81) {
                System.exit(0);
            }
        }
    }
}
