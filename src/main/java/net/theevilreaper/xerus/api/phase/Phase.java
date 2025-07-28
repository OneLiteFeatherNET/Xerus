package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a unit of execution within a phase-based system.
 * <p>
 * A {@code Phase} is started externally and can complete at any time. Once finished,
 * it can notify an optional listener via a {@code finishedCallback}.
 * </p>
 * <p>
 * Each phase has a unique name and maintains internal state indicating whether it is running,
 * finished, or being skipped. Subclasses implement the behavior by overriding {@link #onStart()}.
 * </p>
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 03/01/2020 21:00
 */
public abstract class Phase {

    private final String name;
    private boolean running;
    private boolean finished;
    private boolean skipping;
    private Runnable finishedCallback;

    /**
     * Constructs a new {@code Phase} with the specified name.
     *
     * @param name the name used to identify this phase
     */
    protected Phase(@NotNull String name) {
        this.name = name;
    }

    /**
     * Starts the phase.
     * <p>
     * A phase can only be started once. If it is already running, this method has no effect.
     * </p>
     */
    public void start() {
        if (running) {
            return;
        }
        running = true;
        onStart();
    }

    /**
     * Defines the behavior to execute when the phase starts.
     * This method is called automatically by {@link #start()}.
     */
    protected abstract void onStart();

    /**
     * Called when the phase should be skipped.
     * <p>
     * Subclasses may override this to implement custom skipping behavior.
     * </p>
     */
    public void onSkip() {
    }

    /**
     * Marks the phase as finished and invokes the {@code finishedCallback}, if set.
     * <p>
     * A phase can only be finished once. Subsequent calls will be ignored.
     * </p>
     */
    public void finish() {
        if (finished) {
            return;
        }

        finished = true;
        running = false;
        skipping = false;

        if (finishedCallback != null) {
            finishedCallback.run();
        }
    }

    /**
     * Returns the current phase instance.
     * <p>
     * Subclasses that wrap other phases may override this to return the active child phase.
     * </p>
     *
     * @return the current {@code Phase} instance
     */
    public Phase getCurrentPhase() {
        return this;
    }

    /**
     * Returns the name of this phase.
     *
     * @return the phase name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns whether this phase is currently running.
     *
     * @return {@code true} if running, otherwise {@code false}
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Returns whether this phase has been marked as finished.
     *
     * @return {@code true} if finished, otherwise {@code false}
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Manually sets the finished state of this phase.
     *
     * @param finished {@code true} to mark as finished, {@code false} otherwise
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * Returns whether this phase is currently being skipped.
     *
     * @return {@code true} if skipping, otherwise {@code false}
     */
    public boolean isSkipping() {
        return skipping;
    }

    /**
     * Sets the skipping state of this phase.
     *
     * @param skipping {@code true} to mark as skipping, {@code false} otherwise
     */
    public void setSkipping(boolean skipping) {
        this.skipping = skipping;
    }

    /**
     * Sets the callback to be invoked when this phase finishes.
     *
     * @param finishedCallback the {@link Runnable} to be executed on phase completion
     */
    public void setFinishedCallback(Runnable finishedCallback) {
        this.finishedCallback = finishedCallback;
    }
}
