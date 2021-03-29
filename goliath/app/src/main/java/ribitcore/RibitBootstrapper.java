package ribitcore;

import org.checkerframework.checker.nullness.qual.NonNull;
import uk.pigpioj.PigpioInterface;
import uk.pigpioj.PigpioJ;

public class RibitBootstrapper {

    /**
     * {@link PigpioJ} implementation.
     */
    private final @NonNull PigpioInterface pigpio = PigpioJ.getImplementation();

    public void start() {

    }

}
