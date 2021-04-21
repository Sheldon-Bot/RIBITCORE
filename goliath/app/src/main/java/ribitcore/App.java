package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The main entrypoint to RIBITCORE.
 */
public class App {

    /**
     * Initializes the app.
     *
     * @param args cli args.
     */
    public static void main(final @NonNull String[] args) {
        new RibitApp().start();
    }

}
