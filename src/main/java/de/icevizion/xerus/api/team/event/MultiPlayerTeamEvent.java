package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.ITeam;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.trait.CancellableEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * The event will be fired if an action is applied to a team where more than one player is involved.
 * There is a separate event for it, so that the event is called with only one player less
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.11
 **/
public class MultiPlayerTeamEvent<T extends ITeam> implements Event, CancellableEvent {

    private final T team;
    private final Set<Player> players;
    private final TeamAction teamAction;
    private boolean cancelled;

    /**
     * Event is called when multiple players interact with a team.
     * @param team the team which is involved in the event
     * @param players the set with the players who are involved in the event
     * @param teamAction the action for the event
     * @param cancelled if the event is cancelled or not
     */
    public MultiPlayerTeamEvent(@NotNull T team, @NotNull Set<Player> players, @NotNull TeamAction teamAction, boolean cancelled) {
        this.team = team;
        this.players = players;
        this.teamAction = teamAction;
        this.cancelled = cancelled;
    }

    /**
     * Event is called when multiple players interact with a team.
     * @param team the team which is involved in the event
     * @param players the set with the players who are involved in the event
     * @param teamAction the action for the event
     */
    public MultiPlayerTeamEvent(@NotNull T team, @NotNull Set<Player> players, @NotNull TeamAction teamAction) {
        this.team = team;
        this.players = players;
        this.teamAction = teamAction;
    }

    /**
     * Updates the state if the event is cancelled or not.
     * @param cancelled The new cancelled state
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns an indikator if the event is cancelled or not.
     * @return True if the game is cancelled otherwise false
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
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
     * Returns the used team action.
     * @return the team action
     */
    @NotNull
    public TeamAction getTeamAction() {
        return teamAction;
    }

    /**
     * Returns all player that are effected from the event.
     * @return a {@link Set} which contains the players
     */
    @NotNull
    public Set<Player> getPlayers() {
        return players;
    }
}
