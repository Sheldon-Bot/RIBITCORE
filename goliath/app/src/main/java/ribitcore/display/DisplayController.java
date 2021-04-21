package ribitcore.display;

import processing.core.PApplet;
import ribitcore.thread.ThreadManager;

/**
 * Controls display functions.
 */
public class DisplayController extends ThreadManager {

    @Override
    public void runThread() {
        PApplet.runSketch(new String[]{"DisplayApplet"}, new DisplayApplet());
    }
}
