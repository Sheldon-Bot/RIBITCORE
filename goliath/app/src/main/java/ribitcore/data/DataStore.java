package ribitcore.data;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.LocalDateTime;

/**
 * Stores global data for the robot.
 */
public class DataStore {

    private final @NonNull LocalDateTime startTime;

    public DataStore() {
        this.startTime = LocalDateTime.now();
    }

    public @NonNull LocalDateTime getTime() {
        return this.startTime;
    }

}
