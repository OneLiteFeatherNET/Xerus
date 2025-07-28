package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A {@code LinearPhaseSeries} is a sequential collection of {@link Phase} instances.
 * <p>
 * It manages the flow from one phase to the next in a linear order, invoking the next phase automatically
 * once the current one finishes. The series can be paused, skipped, or advanced manually.
 * </p>
 *
 * @param <T> the type of {@link Phase} managed by this series
 *
 * @version 1.0.1
 * @since 03/01/2020 21:12
 * @author Patrick Zdarsky / Rxcki
 */
public class LinearPhaseSeries<T extends Phase> extends PhaseCollection<T> {

    /** The currently active phase in the series. */
    protected T currentPhase;

    /** The index of the currently active phase. */
    protected int currentPhaseIndex;

    /** Indicates whether the phase series is currently paused. */
    private boolean paused;

    /**
     * Constructs a new {@code LinearPhaseSeries} with the given name.
     *
     * @param name the name of the phase series
     */
    public LinearPhaseSeries(@NotNull String name) {
        super(name);
    }

    /**
     * Constructs a new {@code LinearPhaseSeries} with the given name and list of phases.
     *
     * @param name   the name of the phase series
     * @param phases the phases to be added to this series
     */
    public LinearPhaseSeries(@NotNull String name, @NotNull List<T> phases) {
        super(name, phases);
    }

    /**
     * Called when the series starts. Initializes and starts the first phase.
     */
    @Override
    public void onStart() {
        setCurrentPhase(getFirst());
        startCurrentPhase();
    }

    /**
     * Advances the series to the next phase.
     * <p>
     * If the current phase is the last one or the series is marked as finished, the series is completed.
     * If the series is paused, this method does nothing.
     *
     * @throws IllegalStateException if called while the series is not running
     */
    public void advance() {
        if (!isRunning())
            throw new IllegalStateException("Advance called on not running phase!");

        if (isPaused())
            return;

        if (isFinished() || isLastPhase()) {
            finish();
            return;
        }

        currentPhaseIndex++;
        setCurrentPhase(phaseList.get(currentPhaseIndex));
        startCurrentPhase();
    }

    /**
     * Returns the currently active phase, or the nested current phase if applicable.
     *
     * @return the current {@link Phase}, or {@code null} if not initialized
     */
    @Override
    public Phase getCurrentPhase() {
        return this.currentPhase == null ? null : this.currentPhase.getCurrentPhase();
    }

    /**
     * Skips the current phase by delegating the skip logic to the active phase.
     */
    @Override
    public void onSkip() {
        if (getCurrentPhase() != null)
            getCurrentPhase().onSkip();
    }

    /**
     * Returns whether the current phase is the last in the series.
     *
     * @return {@code true} if at the last phase, {@code false} otherwise
     */
    public boolean isLastPhase() {
        return currentPhaseIndex == size() - 1;
    }

    /**
     * Starts the currently selected phase and registers a callback to advance when it finishes.
     */
    public void startCurrentPhase() {
        currentPhase.setFinishedCallback(this::advance);
        currentPhase.start();
    }

    /**
     * Sets the index of the current phase and updates the active phase accordingly.
     *
     * @param currentPhaseIndex the index to set as current
     */
    public void setCurrentPhaseIndex(int currentPhaseIndex) {
        this.currentPhaseIndex = currentPhaseIndex;
        setCurrentPhase(get(currentPhaseIndex));
    }

    /**
     * Sets the currently active phase.
     *
     * @param currentPhase the phase to set as current
     */
    private void setCurrentPhase(T currentPhase) {
        this.currentPhase = currentPhase;
    }

    /**
     * Returns whether the phase series is currently paused.
     *
     * @return {@code true} if paused, {@code false} otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the paused state of the phase series.
     * <p>
     * If set to {@code false}, the series will attempt to advance.
     *
     * @param paused {@code true} to pause the series, {@code false} to resume
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
        advance();
    }
}
