package net.theevilreaper.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;

/**
 * A {@link TickingPhase} that runs for a specific number of ticks, either counting up or down.
 * <p>
 * The {@code TimedPhase} tracks its tick progression based on the configured {@link TickDirection}
 * and automatically finishes when reaching the end tick threshold.
 * </p>
 *
 * <strong>Important:</strong> After calling {@link #onUpdate()}, you must check whether the phase has finished.
 * This is critical because the phase may end and cancel itself during the update.
 *
 * <p>
 * Subclasses must override {@link #onFinish()} to perform cleanup logic when the phase finishes,
 * and should call {@code super.onStart()} if they override {@link #onStart()}.
 * </p>
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 03/01/2020 22:00
 */
public abstract class TimedPhase extends TickingPhase {

    private boolean paused;
    private int endTicks;
    private int currentTicks;
    private TickDirection tickDirection = TickDirection.DOWN;

    /**
     * Constructs a new {@code TimedPhase} with the specified name, temporal unit, and interval.
     *
     * @param name         the name used to identify this phase
     * @param temporalUnit the unit of time for the interval
     * @param interval     the interval at which {@link #onUpdate()} will be called, in the specified temporal unit
     */
    protected TimedPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit, long interval) {
        super(name, temporalUnit, interval);
    }

    /**
     * Constructs a {@code TimedPhase} with a default interval of 20 ticks in the specified temporal unit.
     *
     * @param name         the name used to identify this phase
     * @param temporalUnit the unit of time for the interval
     */
    protected TimedPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit) {
        super(name, temporalUnit);
    }

    /**
     * Starts the internal update loop for this timed phase.
     * <p>
     * Subclasses overriding this method must call {@code super.onStart()} to ensure
     * correct scheduling behavior.
     * </p>
     */
    @Override
    @MustBeInvokedByOverriders
    public void onStart() {
        scheduledTask = MinecraftServer.getSchedulerManager()
                .buildTask(this::onUpdate0)
                .repeat(getInterval(), getTemporalUnit())
                .schedule();
    }

    /**
     * Internal update loop called by the scheduler.
     * <p>
     * This method handles tick progression and determines if the phase should finish.
     * After execution, {@link #onUpdate()} is invoked unless the phase is paused or completed.
     * </p>
     *
     * <strong>Important:</strong> Always check {@link #isFinished()} after {@code onUpdate()}
     * to determine whether the phase has already completed.
     */
    public void onUpdate0() {
        if (paused)
            return;

        if ((tickDirection == TickDirection.DOWN && currentTicks <= endTicks)
                || (tickDirection == TickDirection.UP && currentTicks >= endTicks)) {
            finish();
            scheduledTask.cancel(); // redundant but defensive
            return;
        }

        if (tickDirection == TickDirection.DOWN) {
            currentTicks--;
        } else {
            currentTicks++;
        }

        onUpdate(); // user-defined update behavior
    }

    /**
     * Called when the phase finishes.
     * <p>
     * This method is invoked before the phase is fully marked as finished.
     * Use this to clean up or perform any final logic.
     * </p>
     */
    protected abstract void onFinish();

    /**
     * Marks the phase as finished and invokes {@link #onFinish()}.
     * <p>
     * Subclasses overriding this method must call {@code super.finish()}.
     * </p>
     */
    @Override
    @MustBeInvokedByOverriders
    public void finish() {
        onFinish();
        super.finish();
    }

    /**
     * Pauses or resumes the ticking behavior of this phase.
     *
     * @param paused whether the phase should be paused
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Returns whether the phase is currently paused.
     *
     * @return {@code true} if paused, {@code false} otherwise
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the direction in which ticks are counted.
     *
     * @param tickDirection the new {@link TickDirection}
     */
    public void setTickDirection(TickDirection tickDirection) {
        this.tickDirection = tickDirection;
    }

    /**
     * Returns the current tick direction.
     *
     * @return the {@link TickDirection} of this phase
     */
    public TickDirection getTickDirection() {
        return tickDirection;
    }

    /**
     * Sets the final tick count at which the phase should end.
     *
     * @param endTicks the tick threshold to finish at
     */
    public void setEndTicks(int endTicks) {
        this.endTicks = endTicks;
    }

    /**
     * Returns the configured end tick threshold.
     *
     * @return the number of ticks to reach before finishing
     */
    public int getEndTicks() {
        return endTicks;
    }

    /**
     * Sets the current tick value.
     *
     * @param currentTicks the tick value to set
     */
    public void setCurrentTicks(int currentTicks) {
        this.currentTicks = currentTicks;
    }

    /**
     * Returns the current tick value.
     *
     * @return the current tick position of the phase
     */
    public int getCurrentTicks() {
        return currentTicks;
    }
}
