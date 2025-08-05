package net.theevilreaper.xerus.api.team.distribution;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link DistributionTeam} class represents a team construct which is required for the distribution of players.
 * Taken from: <a href="https://github.com/Tobi208/TeamSplitterFX">...</a>
 *
 * @param name    the name of the team
 * @param players the list of players in the team
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:28
 */
public record DistributionTeam(@NotNull String name, List<DistributionPlayer> players) {

    /**
     * Creates a new instance of the {@link DistributionTeam} with the given value.
     *
     * @param name the name of the team
     */
    public DistributionTeam(@NotNull String name) {
        this(name, new ArrayList<>());
    }

    /*------------*
     * Evaluation *
     *------------*/

    /**
     * Calculates the sum of all players elo.
     *
     * @return the sum elo of all players
     */
    int sum() {
        int sum = 0;

        if (players.isEmpty()) return sum;

        for (int i = 0; i < players.size(); i++) {
            sum += players.get(i).elo();
        }
        return sum;
    }

    /*---------*
     * Wrapper *
     *---------*/

    /**
     * Returns the length of the team.
     *
     * @return the length
     */
    public int length() {
        return players.size();
    }

    /**
     * Adds a list of players to the team.
     *
     * @param players the players to add
     */
    public void addAll(@NotNull List<DistributionPlayer> players) {
        this.players.addAll(players);
    }

    /**
     * Adds a single player to the team.
     *
     * @param player the player to add
     */
    public void add(@NotNull DistributionPlayer player) {
        players.add(player);
    }
}
