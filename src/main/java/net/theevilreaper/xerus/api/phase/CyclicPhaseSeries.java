package net.theevilreaper.xerus.api.phase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A {@code CyclicPhaseSeries} is a specialized {@link LinearPhaseSeries} that supports
 * repeating its sequence of phases for a defined number of iterations.
 * <p>
 * Once the end of the phase list is reached, the series either restarts from the beginning
 * or finishes, depending on whether the maximum number of iterations has been reached.
 * </p>
 * <p>
 * This class is useful for modeling repeating workflows or game logic sequences.
 * </p>
 *
 * @param <T> the type of {@link Phase} this series operates on
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 03/01/2020 21:33
 */
public class CyclicPhaseSeries<T extends Phase> extends LinearPhaseSeries<T> {

    private int maxIterations;
    private int iterations;

    /**
     * Constructs a new {@code CyclicPhaseSeries} with the specified name.
     *
     * @param name the name of the phase series
     */
    public CyclicPhaseSeries(@NotNull String name) {
        super(name);
    }

    /**
     * Constructs a new {@code CyclicPhaseSeries} with the specified name and a list of phases.
     *
     * @param name   the name of the phase series
     * @param phases the phases to be added to this series
     */
    public CyclicPhaseSeries(@NotNull String name, List<T> phases) {
        super(name, phases);
    }

    /**
     * Advances the phase series to the next phase. If the current phase is the last one,
     * the iteration count is increased. When the maximum number of iterations is reached,
     * the series is marked as finished. Otherwise, it restarts from the first phase.
     */
    @Override
    public void advance() {
        if (isFinished()) {
            return;
        }

        if (isLastPhase()) {
            if (!increaseIteration())
                return;

            setCurrentPhaseIndex(0);
            getCurrentPhase().setFinished(false);

            startCurrentPhase();
            return;
        }

        setCurrentPhaseIndex(currentPhaseIndex + 1);
        currentPhase.setFinished(false);
        startCurrentPhase();
    }

    /**
     * Skips the current iteration of the phase series.
     * <p>
     * The current phase is skipped and marked as finished, and the iteration count is increased.
     * If the maximum number of iterations has not yet been reached, the series restarts at the first phase.
     * </p>
     */
    public void skipIteration() {
        setPaused(true);
        getCurrentPhase().onSkip();
        getCurrentPhase().finish();

        if (!increaseIteration())
            return;

        setCurrentPhaseIndex(0);
        getCurrentPhase().setFinished(false);
        setPaused(false);
        startCurrentPhase();
    }

    /**
     * Increases the internal iteration counter by one.
     * If the maximum number of iterations is reached, the phase series is finished.
     *
     * @return {@code true} if another iteration is allowed; {@code false} if the series is finished
     */
    private boolean increaseIteration() {
        iterations++;
        if (iterations == maxIterations) {
            finish();
            return false;
        }
        return true;
    }

    /**
     * Returns the maximum number of iterations allowed for this phase series.
     *
     * @return the maximum number of iterations
     */
    public int getMaxIterations() {
        return maxIterations;
    }

    /**
     * Sets the maximum number of iterations for this phase series.
     *
     * @param maxIterations the number of times the series is allowed to repeat
     */
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    /**
     * Returns the current iteration value of this phase series.
     *
     * @return the current iterations
     */
    public int getIterations() {
        return iterations;
    }
}
