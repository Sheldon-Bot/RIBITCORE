package ribitcore.display;

import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PApplet;
import ribitcore.data.DataStore;
import ribitcore.thread.ThreadManager;

/**
 * Controls display functions.
 */
public class DisplayController extends ThreadManager {

    /**
     * The {@link DataStore} instance.
     */
    private final @NonNull DataStore dataStore;

    /**
     * Constructs {@link DisplayController}.
     *
     * @param dataStore the data store.
     */
    public DisplayController(final @NonNull DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Runs the processing application.
     */
    @Override
    public void runThread() {
        PApplet.runSketch(new String[]{"DisplayApplet"}, new DisplayApplet(dataStore));
    }
}
