package ribitcore.display.debug;

import org.checkerframework.checker.nullness.qual.NonNull;
import processing.core.PConstants;
import ribitcore.data.DataStore;
import ribitcore.display.DisplayApplet;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private static final int PANEL_Y = 20;

    /**
     * The width of the panel.
     */
    private static final int PANEL_W = 400;

    /**
     * The (min) height of the panel.
     */
    private static final int PANEL_H = 200;

    /**
     * The height at which entry text starts being displayed.
     */
    private static final int ENTRY_TEXT_DRAW_START_HEIGHT = 50;

    /**
     * If true, the panel should show. If false, the panel shouldn't.
     */
    private boolean shouldShow;

    /**
     * The sketch instance.
     */
    private final @NonNull DisplayApplet sketch;

    /**
     * The {@link DataStore} instance.
     */
    private final @NonNull DataStore dataStore;

    /**
     * The list of panel entries.
     */
    private final @NonNull List<PanelEntry> panelEntryList;

    /**
     * Constructs {@link DebugPanel}.
     *
     * @param sketch    sketch instance.
     * @param dataStore dataStore.
     */
    public DebugPanel(
            final @NonNull DisplayApplet sketch,
            final @NonNull DataStore dataStore
    ) {
        this.sketch = sketch;
        this.dataStore = dataStore;
        this.panelEntryList = new ArrayList<>();
    }

    /**
     * Runs the {@link DebugPanel}.
     */
    public void run() {
        if (sketch.keyCode == 61) {
            this.shouldShow = true;
        } else if (sketch.keyCode == 45) {
            this.shouldShow = false;
        }

        this.addPanelEntry(new PanelEntry("StartTime: "+dataStore.getTime().toString()));
        this.addPanelEntry(new PanelEntry("CurTime: "+ LocalDateTime.now().toString()));

        if (this.shouldShow) {
            sketch.fill(Color.BLACK.getRGB());
            sketch.rect(PANEL_X, PANEL_Y, PANEL_W, PANEL_H);
            sketch.fill(255, 255, 255);
            sketch.textSize(15);
            sketch.textAlign(PConstants.CENTER);
            sketch.text("RIBIT DEBUG", 220, 30);

            for (int i = 0; i < panelEntryList.size(); i++) {
                final @NonNull PanelEntry entry = panelEntryList.get(i);
                sketch.text(entry.getText(), 220, ENTRY_TEXT_DRAW_START_HEIGHT + (i*18));
            }
        }

        this.panelEntryList.clear();
    }

    /**
     * Adds a {@link PanelEntry} to the internal list.
     *
     * @param entry entry to add.
     */
    public void addPanelEntry(final @NonNull PanelEntry entry) {
        this.panelEntryList.add(entry);
    }

}