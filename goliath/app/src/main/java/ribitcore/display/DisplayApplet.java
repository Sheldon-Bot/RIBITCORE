package ribitcore.display;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PApplet;
import processing.core.PFont;
import ribitcore.data.DataStore;
import ribitcore.display.debug.DebugPanel;
import ribitcore.display.debug.PanelEntry;

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
     * The {@link PFont} to use for monospaced text.
     */
    private @MonotonicNonNull PFont fontMonospaced;

    /**
     * The {@link PFont} to use for main text.
     */
    private @MonotonicNonNull PFont fontMain;

    /**
     * The {@link DebugPanel} instance.
     */
    private @MonotonicNonNull DebugPanel debugPanel;

    /**
     * Constructs {@link DisplayApplet}.
     *
     * @param dataStore data store.
     */
    public DisplayApplet(final @NonNull DataStore dataStore) {
        System.out.println(dataPath("Roboto-Light.ttf"));
        this.dataStore = dataStore;
    }

    /**
     * Sets the initial settings of the DisplayApplet.
     */
    public void settings() {
        fullScreen(0);
        size(displayWidth, displayHeight);
    }

    /**
     * Sets up important objects.
     */
    public void setup() {
        this.fontMonospaced = createFont("/home/pi/bot/data/RobotoMono-Light.ttf", 18);
        this.fontMain = createFont("/home/pi/bot/data/Roboto-Light.ttf", 18);
        this.debugPanel = new DebugPanel(this, fontMonospaced, dataStore);
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
        drawDebugPanel();
    }

    /**
     * Draws a text to the screen.
     *
     * @param text text to display.
     * @param x    x-coordinate of the text.
     * @param y    y-coordinate of the text.
     */
    private void drawText(final @NonNull String text, final int x, final int y, final int textSize) {
        textFont(fontMain);
        textSize(textSize);
        fill(COLOR_TEXT_MAIN.getRGB());
        text(text, x, y);
    }

    /**
     * Draws the debug panel.
     */
    private void drawDebugPanel() {
        this.debugPanel.addPanelEntry(new PanelEntry("keyCode: "+keyCode));
        this.debugPanel.addPanelEntry(new PanelEntry("key: "+key));
        this.debugPanel.addPanelEntry(new PanelEntry("sketch path: "+sketchPath()));
        this.debugPanel.run();
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
        if (keyCode == 81) {
            System.exit(0);
        }
    }
}
