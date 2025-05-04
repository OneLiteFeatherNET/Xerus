package net.theevilreaper.xerus.api.team.distribution;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:28
 *
 * Taken from: <a href="https://github.com/Tobi208/TeamSplitterFX">...</a>
 */

public record DistributionTeam(@NotNull String name, List<DistributionPlayer> players) {

    public DistributionTeam(@NotNull String name) {
        this(name, new ArrayList<>());
    }

    /*------------*
     * Evaluation *
     *------------*/

    /**
     * @return sum of elo of all players
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

    public int length() {
        return players.size();
    }

    public void addAll(@NotNull List<DistributionPlayer> players) {
        this.players.addAll(players);
    }

    public void add(@NotNull DistributionPlayer player) {
        players.add(player);
    }
}
