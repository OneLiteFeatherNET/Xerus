package net.theevilreaper.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Task;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;

/**
 * Represents a {@link TickedPhase} that automatically schedules a repeating task
 * using Minestom’s scheduler infrastructure.
 * <p>
 * The {@code TickingPhase} calls {@link #onUpdate()} periodically at the specified interval.
 * It is the responsibility of subclasses to implement {@code onUpdate()} and call {@code super.onStart()}
 * when overriding {@link #onStart()}.
 * </p>
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 03/01/2020 21:53
 */
public abstract class TickingPhase extends TickedPhase {

    protected final long interval;
    protected final TemporalUnit temporalUnit;
    protected Task scheduledTask;

    /**
     * Constructs a new {@code TickingPhase} with the given name, interval, and time unit.
     *
     * @param name         the name used to identify this phase
     * @param temporalUnit the unit of time between each tick
     * @param interval     the interval at which {@link #onUpdate()} will be called
     */
    protected TickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit, long interval) {
        super(name);
        this.interval = interval;
        this.temporalUnit = temporalUnit;
    }

    /**
     * Constructs a {@code TickingPhase} with a default interval of {@code 20} in the given unit.
     *
     * @param name         the name used to identify this phase
     * @param temporalUnit the unit of time between each tick
     */
    protected TickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit) {
        this(name, temporalUnit, 20);
    }

    /**
     * Starts the scheduled ticking task for this phase.
     * <p>
     * Subclasses overriding this method <strong>must</strong> call {@code super.onStart()} to ensure
     * the periodic task is properly scheduled.
     * </p>
     */
    @Override
    @MustBeInvokedByOverriders
    public void onStart() {
        scheduledTask = MinecraftServer.getSchedulerManager()
                .buildTask(this::onUpdate)
                .repeat(interval, temporalUnit)
                .schedule();
    }

    /**
     * Finishes the phase and cancels the associated scheduled task, if present.
     * <p>
     * Subclasses overriding this method <strong>must</strong> call {@code super.finish()}.
     * </p>
     */
    @Override
    @MustBeInvokedByOverriders
    public void finish() {
        super.finish();
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

    /**
     * Sets the scheduled task for this phase.
     * <p>
     * This is intended for advanced use cases such as overriding default scheduling behavior.
     * </p>
     *
     * @param scheduledTask the task to be associated with this phase
     */
    public void setScheduledTask(Task scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    /**
     * Returns the interval between task executions.
     *
     * @return the configured tick interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * Returns the temporal unit used for scheduling.
     *
     * @return the temporal unit (e.g., {@code ChronoUnit.MILLIS})
     */
    public TemporalUnit getTemporalUnit() {
        return temporalUnit;
    }

    /**
     * Returns the scheduled task associated with this phase.
     *
     * @return the scheduled {@link Task}, or {@code null} if not scheduled
     */
    public Task getScheduledTask() {
        return scheduledTask;
    }
}
