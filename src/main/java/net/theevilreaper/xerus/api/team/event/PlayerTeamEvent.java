package net.theevilreaper.xerus.api.team.event;

import net.theevilreaper.xerus.api.team.Team;
import net.theevilreaper.xerus.api.team.TeamImpl;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The event would be fired when a player leaves or joins the team.
 * The {@link TeamEvent.Action} shows if a player joins or leaves a team
 * @param <T> The generic typ must extend the given {@link TeamImpl} class.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 */
public final class PlayerTeamEvent<T extends Team> extends TeamEvent<T> implements PlayerEvent, CancellableEvent {

    private final Player player;
    private boolean cancelled;

    /**
     * Creates a new instance of {@link PlayerTeamEvent} representing the addition of a player to a team.
     *
     * @param player The player being added to the team.
     * @param team   The team to which the player is being added.
     * @param <T>    The type of team (must extend the Team class).
     * @return A new PlayerTeamEvent instance indicating the addition of the player to the team.
     * @throws NullPointerException if 'player' or 'team' is null.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Team> @NotNull PlayerTeamEvent<T> addEvent(@NotNull Player player, @NotNull T team) {
        return new PlayerTeamEvent<>(player, team, TeamEvent.Action.ADD);
    }

    /**
     * Creates a new instance of {@link PlayerTeamEvent} representing the removal of a player from a team.
     *
     * @param player The player being removed from the team.
     * @param team   The team from which the player is being removed.
     * @param <T>    The type of team (must extend the Team class).
     * @return A new PlayerTeamEvent instance indicating the removal of the player from the team.
     * @throws NullPointerException if 'player' or 'team' is null.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Team> @NotNull PlayerTeamEvent<T> removeEvent(@NotNull Player player, @NotNull T team) {
        return new PlayerTeamEvent<>(player, team, Action.REMOVE);
    }

    /**
     * Creates a new instance of this event with the given parameters.
     * @param player The player who changed his team
     * @param team The current team from the player
     * @param teamAction The current team action
     */
    PlayerTeamEvent(@NotNull Player player, @NotNull T team, @NotNull TeamEvent.Action teamAction) {
        super(team, teamAction);
        this.player = player;
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
