package net.theevilreaper.xerus.api.team.distribution;

import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * The {@link TeamDistributor} can be used to distribute a list of players into a list of teams.
 * It provides methods to distribute players based on their elo ratings and team size.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public interface TeamDistributor {

    /**
     * Distributes players into teams based on their elo ratings and team size.
     *
     * @param teams       the list of teams to distribute players into
     * @param players     the list of players to distribute
     * @param teamSize    the size of each team
     * @param eloFunction a function to get the elo rating of a player
     * @param evenTeams   if true, teams will be distributed evenly based on player elo ratings
     * @param lowVariance if true, the distribution will aim for low variance in team elo ratings
     */
    void distribute(
            @NotNull List<Team> teams,
            @NotNull List<Player> players,
            int teamSize,
            @NotNull ToIntFunction<Player> eloFunction,
            boolean evenTeams,
            boolean lowVariance
    );

    /**
     * Distributes players into teams based on their elo ratings and team size.
     *
     * @param teams       the list of teams to distribute players into
     * @param players     the list of players to distribute
     * @param teamSize    the size of each team
     * @param eloFunction a function to get the elo rating of a player
     */
    void distribute(
            @NotNull List<Team> teams,
            @NotNull List<Player> players,
            int teamSize,
            @NotNull ToIntFunction<Player> eloFunction
    );
}
