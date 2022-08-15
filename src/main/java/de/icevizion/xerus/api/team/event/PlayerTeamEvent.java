package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.ITeam;
import de.icevizion.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The event would be fired when a player leaves or joins the team.
 * The {@link TeamAction} shows if a player joins or not
 * @param <T> The generic typ must extend the given {@link Team} class.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PlayerTeamEvent<T extends ITeam> implements PlayerEvent, CancellableEvent {

    private final Player player;
    private final TeamAction teamAction;
    private final T team;
    private boolean cancelled;

    /**
     * Creates a new instance of this event with the given parameters.
     * @param player The player who changed his team
     * @param team The current team from the player
     * @param teamAction The current team action
     */
    public PlayerTeamEvent(@NotNull Player player, @NotNull T team, @NotNull TeamAction teamAction) {
        this.player = player;
        this.team = team;
        this.teamAction = teamAction;
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
     * Returns the used team action.
     * @return the team action
     */
    @NotNull
    public TeamAction getTeamAction() {
        return teamAction;
    }

    /**
     * Receive the used team from the event.
     * @return the used team
     */
    @NotNull
    public T getTeam() {
        return team;
    }

    /**
     * Gets the cancellation state of this event.
     * A cancelled event will not be executed in the server, but will still pass to other plugins.
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns the player which is involved in this event.
     * @return the involved player
     */
    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
