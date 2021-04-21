package ribitcore.io;

import au.edu.jcu.v4l4j.exceptions.InitialisationException;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.pigpioj.PigpioInterface;
import uk.pigpioj.PigpioJ;

/**
 * Handles {@link PigpioInterface} initialization.
 */
public class IOManager {

    /**
     * The {@link PigpioInterface} instance.
     */
    private final @NonNull PigpioInterface pigpioInterface;

    /**
     * The {@link PigpioInterface} version.
     */
    private final int pigpioVersion;

    /**
     * Constructs {@link IOManager}.
     * <p>
     * Initializes {@link PigpioInterface}.
     */
    public IOManager() throws InitialisationException {
        this.pigpioInterface = PigpioJ.getImplementation();

        this.pigpioVersion = this.pigpioInterface.getVersion();

        // version check
        if (this.pigpioVersion < 0) {
            throw new InitialisationException("PigpioJ version is invalid! Got: "+this.pigpioVersion);
        }
    }



}
