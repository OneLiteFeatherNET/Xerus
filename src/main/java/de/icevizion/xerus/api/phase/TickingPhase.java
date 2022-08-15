package de.icevizion.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.Task;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalUnit;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/01/2020 21:53
 */

public abstract class TickingPhase extends TickedPhase {

    protected final long interval;
    protected final TemporalUnit temporalUnit;
    protected Task scheduledTask;

    public TickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit, long interval) {
        super(name);
        this.interval = interval;
        this.temporalUnit = temporalUnit;
    }

    public TickingPhase(@NotNull String name, @NotNull TemporalUnit temporalUnit) {
        this(name, temporalUnit, 20);
    }

    /**
     * Remember to call this method using super.onStart() !
     */
    @Override
    @MustBeInvokedByOverriders
    public void onStart() {
        scheduledTask = MinecraftServer.getSchedulerManager().buildTask(this::onUpdate).repeat(interval, temporalUnit).schedule();
    }

    @Override
    @MustBeInvokedByOverriders
    public void finish() {
        super.finish();
        if (scheduledTask != null)
            scheduledTask.cancel();
    }

    /**
     * Sets the scheduled task to the phase.
     * @param scheduledTask The {@link Task} to set
     */
    public void setScheduledTask(Task scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    /**
     * Returns the tick interval from the {@link TickingPhase}.
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * Returns the {@link Task} from the phase.
     * @return the underlying task
     */
    public Task getScheduledTask() {
        return scheduledTask;
    }

    public TemporalUnit getTemporalUnit() {
        return temporalUnit;
    }
}
