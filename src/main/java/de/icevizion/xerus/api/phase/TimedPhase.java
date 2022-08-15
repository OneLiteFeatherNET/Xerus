package de.icevizion.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.time.temporal.TemporalUnit;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/01/2020 22:00
 *
 *
 * Remember to check if the Phase is finished after calling this class's onUpdate()!
 *
 */
public abstract class TimedPhase extends TickingPhase {

    private boolean paused;
    private int endTicks;
    private int currentTicks;
    private TickDirection tickDirection = TickDirection.DOWN;

    public TimedPhase(String name, TemporalUnit temporalUnit, long interval) {
        super(name, temporalUnit, interval);
    }

    public TimedPhase(String name, TemporalUnit temporalUnit) {
        super(name, temporalUnit);
    }

    /**
     * Remember to call this method using super.onStart() !
     */
    @Override
    @MustBeInvokedByOverriders
    public void onStart() {
        scheduledTask = MinecraftServer.getSchedulerManager().buildTask(this::onUpdate0).repeat(getInterval(), getTemporalUnit()).schedule();
    }

    public void onUpdate0() {
        if (paused)
            return;

        if ((tickDirection == TickDirection.DOWN && currentTicks <= endTicks)
                || (tickDirection == TickDirection.UP && currentTicks >= endTicks)) {
            finish();
            scheduledTask.cancel();
            return;
        }

        if (tickDirection == TickDirection.DOWN) {
            currentTicks--;
        } else {
            currentTicks++;
        }

        onUpdate();
    }

    protected abstract void onFinish();

    @Override
    @MustBeInvokedByOverriders
    public void finish() {
        onFinish();
        super.finish();
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Sets the tick direction of the phase.
     * @param tickDirection New tick direction
     */
    public void setTickDirection(TickDirection tickDirection) {
        this.tickDirection = tickDirection;
    }

    /**
     * Sets the new endticks for the phase.
     * @param endTicks The ticks to set
     */
    public void setEndTicks(int endTicks) {
        this.endTicks = endTicks;
    }

    /**
     * Sets the new current ticks.
     * @param currentTicks The ticks to set
     */
    public void setCurrentTicks(int currentTicks) {
        this.currentTicks = currentTicks;
    }

    /**
     * Returns whether the phase is paused.
     * @return True when the phase is paused otherwise false
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Returns the end ticks from the phase.
     * @return The end ticks
     */
    public int getEndTicks() {
        return endTicks;
    }

    /**
     * Returns the given tick direction.
     * @return The tick direction
     */
    public TickDirection getTickDirection() {
        return tickDirection;
    }

    /**
     * Returns the current ticks from the phase.
     * @return The current ticks
     */
    public int getCurrentTicks() {
        return currentTicks;
    }
}
