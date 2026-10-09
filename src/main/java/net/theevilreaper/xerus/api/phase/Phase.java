package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a unit of execution within a phase-based system.
 * <p>
 * A {@code Phase} is started externally and can complete at any time. Once finished,
 * it notifies all registered finished callbacks.
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

    private static final Logger PHASE_LOGGER = LoggerFactory.getLogger(Phase.class);

    private final String name;
    private final List<Runnable> finishedCallbacks;
    private boolean running;
    private boolean finished;
    private boolean skipping;
    private Runnable seriesCallback;

    /**
     * Constructs a new {@code Phase} with the specified name.
     *
     * @param name the name used to identify this phase
     */
    protected Phase(@NotNull String name) {
        this.name = name;
        this.finishedCallbacks = new ArrayList<>();
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
     * Marks the phase as finished and invokes all registered finished callbacks in registration order.
     * <p>
     * A phase can only be finished once. Subsequent calls will be ignored.
     * An exception thrown by a callback is logged and does not prevent the remaining callbacks
     * or the owning series from being notified.
     * </p>
     */
    public void finish() {
        if (finished) {
            return;
        }

        finished = true;
        running = false;
        skipping = false;

        notifyFinishedCallbacks();

        if (seriesCallback != null) {
            seriesCallback.run();
        }
    }

    /**
     * Invokes all registered finished callbacks in registration order.
     * <p>
     * Iterates over a copy so callbacks may remove themselves while being invoked.
     * Exceptions are logged and do not prevent the remaining callbacks from running.
     * </p>
     */
    private void notifyFinishedCallbacks() {
        if (finishedCallbacks.isEmpty()) return;

        for (Runnable callback : List.copyOf(finishedCallbacks)) {
            try {
                callback.run();
            } catch (Exception exception) {
                PHASE_LOGGER.error("A finished callback of phase '{}' threw an exception", name, exception);
            }
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
     * Registers a callback to be invoked when this phase finishes.
     *
     * @param callback the {@link Runnable} to be executed on phase completion
     */
    public void addFinishedCallback(@NotNull Runnable callback) {
        this.finishedCallbacks.add(callback);
    }

    /**
     * Removes a previously registered finished callback.
     *
     * @param callback the {@link Runnable} to remove
     * @return {@code true} if the callback was registered, otherwise {@code false}
     */
    public boolean removeFinishedCallback(@NotNull Runnable callback) {
        return this.finishedCallbacks.remove(callback);
    }

    /**
     * Replaces all registered finished callbacks with the given one.
     *
     * @param finishedCallback the {@link Runnable} to be executed on phase completion, or {@code null} to clear all callbacks
     * @deprecated use {@link #addFinishedCallback(Runnable)} and {@link #removeFinishedCallback(Runnable)} instead
     */
    @Deprecated(forRemoval = true)
    public void setFinishedCallback(Runnable finishedCallback) {
        this.finishedCallbacks.clear();
        if (finishedCallback != null) {
            this.finishedCallbacks.add(finishedCallback);
        }
    }

    /**
     * Sets the internal callback used by an owning {@link LinearPhaseSeries} to advance once this phase finishes.
     * <p>
     * It is kept separate from the user-defined finished callbacks so it can not be removed by them.
     * The series callback is always invoked after all finished callbacks.
     * </p>
     *
     * @param seriesCallback the {@link Runnable} to be executed by the owning series
     */
    void setSeriesCallback(Runnable seriesCallback) {
        this.seriesCallback = seriesCallback;
    }
}
