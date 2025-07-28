package net.theevilreaper.xerus.api.team.event;

import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link PlayerTeamEvent} is called when a player changes his given team reference.
 * It contains the involved action on the team and the player who changed his team.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.0.0
 */
public final class PlayerTeamEvent implements PlayerEvent, CancellableEvent {

    private final Player player;
    private final Team team;
    private final TeamAction action;
    private boolean cancelled;

    /**
     * Creates a new instance of this event with the given parameters.
     *
     * @param player the player who changed his team
     * @param team   the current team from the player
     * @param action the action which was applied to the team
     */
    public PlayerTeamEvent(@NotNull Player player, @NotNull Team team, @NotNull TeamAction action) {
        this.player = player;
        this.team = team;
        this.action = action;
    }

    /**
     * Sets the cancellation state of this event.
     *
     * @param cancelled the state of cancellation to set
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the cancellation state of this event.
     *
     * @return true if this event is canceled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns the team which is involved in this event.
     *
     * @return the involved team
     */
    public @NotNull Team getTeam() {
        return team;
    }

    /**
     * Returns the action which was performed on the team.
     *
     * @return the performed action
     */
    public @NotNull TeamAction getAction() {
        return action;
    }

    /**
     * Returns the player which is involved in this event.
     *
     * @return the involved player
     */
    @Override
    public @NotNull Player getPlayer() {
        return player;
    }
}
