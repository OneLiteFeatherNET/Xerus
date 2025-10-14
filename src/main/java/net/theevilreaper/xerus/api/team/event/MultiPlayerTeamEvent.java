package net.theevilreaper.xerus.api.team.event;

import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.trait.CancellableEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * The event will be fired if an action is applied to a team where more than one player is involved.
 * There is a separate event for it, so that the event is called with only one player less
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.0.11
 **/
public class MultiPlayerTeamEvent implements Event, CancellableEvent {

    private final Team team;
    private final Set<Player> players;
    private final TeamAction action;
    private boolean cancelled;

    /**
     * Event is called when multiple players interact with a team.
     *
     * @param team       the team which is involved in the event
     * @param players    the set with the players who are involved in the event
     * @param teamAction the action for the event
     */
    public MultiPlayerTeamEvent(@NotNull Team team, @NotNull Set<Player> players, @NotNull TeamAction teamAction) {
        this.team = team;
        this.players = players;
        this.action = teamAction;
    }

    /**
     * Set the cancellation state of this event.
     *
     * @param cancelled true if the event should be canceled, false otherwise
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Gets the cancellation state of this event.
     *
     * @return true if this event is canceled, false otherwise
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Returns the players who are involved in this event.
     *
     * @return the set with the involved players
     */
    @NotNull
    public Set<Player> getPlayers() {
        return players;
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
}
