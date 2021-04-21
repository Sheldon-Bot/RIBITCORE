package ribitcore.display.debug;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a text entry in the {@link DebugPanel}.
 */
public class PanelEntry {

    /**
     * The text.
     */
    private final @NonNull String text;

    /**
     * Constructs {@link PanelEntry}.
     *
     * @param text the value of the entry.
     */
    public PanelEntry(final @NonNull String text) {
        this.text = text;
    }

    /**
     * Returns the text.
     * @return the text.
     */
    public @NonNull String getText() {
        return text;
    }
}
