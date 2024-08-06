package de.icevizion.xerus.api.team.distribution;

import org.jetbrains.annotations.Contract;
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
public class DistributionTeam {

    private final String name;
    private final List<DistributionPlayer> ps;

    public DistributionTeam(@NotNull String name) {
        this.name = name;
        this.ps = new ArrayList<>();
    }

    public DistributionTeam(@NotNull String name, List<DistributionPlayer> ps) {
        this.name = name;
        this.ps = ps;
    }

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull DistributionTeam of(@NotNull String name) {
        return new DistributionTeam(name);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull DistributionTeam of(@NotNull String name, @NotNull List<DistributionPlayer> ps) {
        return new DistributionTeam(name, ps);
    }

    /*------------*
     * Evaluation *
     *------------*/

    /**
     * @return sum of elo of all players
     */
    int sum() {
        int sum = 0;

        if (ps.isEmpty()) return sum;

        for (int i = 0; i < ps.size(); i++) {
            sum += ps.get(i).elo();
        }
        return sum;
    }

    /*---------*
     * Wrapper *
     *---------*/

    public int length() {
        return ps.size();
    }

    public void add(DistributionPlayer p) {
        ps.add(p);
    }

    @NotNull
    public List<DistributionPlayer> getPlayers() {
        return ps;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
