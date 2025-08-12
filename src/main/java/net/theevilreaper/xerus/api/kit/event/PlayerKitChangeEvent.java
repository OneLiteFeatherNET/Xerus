package net.theevilreaper.xerus.api.kit.event;

import net.theevilreaper.xerus.api.kit.Kit;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Thrown when a player changes his actual kit.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PlayerKitChangeEvent implements PlayerEvent, CancellableEvent {

    private final Player player;
    private final Kit currentKit;
    private final Kit newKit;
    private boolean cancelled;

    /**
     * Creates a new instance of this event with the given parameters.
     * @param player The player who changes his kit
     * @param currentKit The current kit of the player
     * @param newKit The new kit of the player
     */
    public PlayerKitChangeEvent(@NotNull Player player, @Nullable Kit currentKit, @NotNull Kit newKit) {
        this.player = player;
        this.currentKit = currentKit;
        this.newKit = newKit;
    }

    /**
     * Returns the player which is involved with this event.
     * @return the involved player
     */
    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    /**
     * Sets the cancellation state of this event.
     *
     * @param cancelled - true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the cancellation state of this event.
     * A cancelled event will not be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns the current kit of a player
     * If the kit is null, then the player has no kit yet
     * @return the current kit
     */
    public @Nullable Kit getCurrentKit() {
        return currentKit;
    }

    /**
     * Returns the new kit for a player
     * @return the new kit
     */
    public @NotNull Kit getNewKit() {
        return newKit;
    }
}