package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.trait.CancellableEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * The event will be fired if an action is applied to a team where more than one player is involved.
 * There is a separate event for it, so that the event is called with only one player less
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.11
 **/
public class MultiPlayerTeamEvent<T extends Team> extends TeamEvent<T> implements Event, CancellableEvent {

    private final Set<Player> players;
    private boolean cancelled;

    /**
     * Creates a new instance of {@link MultiPlayerTeamEvent} representing the addition of multiple players to a team.
     *
     * @param team    The team to which players are being added.
     * @param players A set of players being added to the team.
     * @param <T>     The type of team (must extend the Team class).
     * @return A new MultiPlayerTeamEvent instance indicating the addition of multiple players to the team.
     * @throws NullPointerException if 'team' or 'players' is null.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Team> @NotNull MultiPlayerTeamEvent<T> addEvent(@NotNull T team, @NotNull Set<Player> players) {
        return new MultiPlayerTeamEvent<>(team, players, TeamEvent.Action.ADD);
    }

    /**
     * Creates a new instance of {@link MultiPlayerTeamEvent} representing the removal of multiple players from a team.
     *
     * @param team    The team from which players are being removed.
     * @param players A set of players being removed from the team.
     * @param <T>     The type of team (must extend the Team class).
     * @return A new MultiPlayerTeamEvent instance indicating the removal of multiple players from the team.
     * @throws NullPointerException if 'team' or 'players' is null.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Team> @NotNull MultiPlayerTeamEvent<T> removeEvent(
            @NotNull T team,
            @NotNull Set<Player> players) {
        return new MultiPlayerTeamEvent<>(team, players, TeamEvent.Action.REMOVE);
    }

    /**
     * Event is called when multiple players interact with a team.
     * @param team the team which is involved in the event
     * @param players the set with the players who are involved in the event
     * @param teamAction the action for the event
     */
    MultiPlayerTeamEvent(@NotNull T team, @NotNull Set<Player> players, @NotNull TeamEvent.Action teamAction) {
        super(team, teamAction);
        this.players = players;
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
     * Returns an indicator if the event is cancelled or not.
     * @return True if the game is cancelled otherwise false
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
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
