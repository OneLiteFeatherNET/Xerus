package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

/**
 * Describes any Phase which will be externally started and which can finish at some point,
 * when the phase finishes it can notify a listener using the {@code finishedCallback}
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/01/2020 21:00
 */
public abstract class Phase {

    private final String name;
    private boolean running;
    private boolean finished;
    private boolean skipping;

    private Runnable finishedCallback;

    /**
     * Creates a new reference of the {@link Phase} with the given parameters.
     *
     * @param name the name of the phase, used to identify it
     */
    protected Phase(@NotNull String name) {
        this.name = name;
    }

    /**
     * Used to start the phase.
     * A phase can only be started once, so when running is true the phase won't be started
     */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        onStart();
    }

    /**
     * Called by the implementation when a phase should be started.
     */
    protected abstract void onStart();

    /**
     * Called when the phase should be skipped.
     */
    public void onSkip() {}

    /**
     * Used to finish the phase.
     *
     * A phase can only be finished once, so when finished is true the phase won't be stated as finished again
     */
    public void finish() {
        if (finished)
            return;

        finished = true;
        running = false;
        if (finishedCallback != null)
            finishedCallback.run();
        skipping = false;
    }

    public Phase getCurrentPhase() {
        return this;
    }

    /**
     * Returns the name representing this phase.
     *
     * @return the given name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns an indication if the phase is currently running or not.
     *
     * @return true if the phase is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Returns an indication if the phase is currently finished or not.
     *
     * @return true if the phase is finished, false otherwise
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the state if a phase is finished or not.
     *
     * @param finished the state to set
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Returns an indication if the phase is currently skipped or not.
     *
     * @return true if the phase is skipped, false otherwise
     */
    public boolean isSkipping() {
        return skipping;
    }

    /**
     * Sets the state if a phase should be skipped or not.
     *
     * @param skipping the skipping state to set
     */
    public void setSkipping(boolean skipping) {
        this.skipping = skipping;
    }

    /**
     * Sets the callback which will be called when the phase is finished
     *
     * @param finishedCallback The callback to be called when the phase finished
     */
    public void setFinishedCallback(Runnable finishedCallback) {
        this.finishedCallback = finishedCallback;
    }
}
