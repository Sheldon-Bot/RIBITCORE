package ribitcore.thread;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Manages a thread.
 */
public abstract class ThreadManager {

    /**
     * {@link Thread} storage.
     */
    private final @NonNull Thread thread;

    /**
     * Constructs {@link ThreadManager} and the internal {@link Thread}.
     */
    public ThreadManager() {
        this.thread = new Thread(this::runThread);
    }

    /**
     * Runs the thread.
     */
    protected abstract void runThread();

    /**
     * Runs the thread.
     */
    public void run() {
        this.thread.start();
    }

    /**
     * Stops the thread.
     */
    public void stop() {
        this.thread.stop();
    }

    /**
     * Returns if the thread is running.
     *
     * @return {@code true} if the thread is running, {@code false} if not.
     */
    public boolean isRunning() {
        return this.thread.isAlive();
    }

}
