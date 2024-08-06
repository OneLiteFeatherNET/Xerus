package de.icevizion.xerus.api.team.distribution;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:21
 *
 * Taken from: <a href="https://github.com/Tobi208/TeamSplitterFX">...</a>
 */
public class Splitter {

    private DistributionTeam[] ts;
    private DistributionPlayer[] ps;
    private boolean evenTeams;
    private int evenMax;
    private int tn;
    private int score;

    /*-------------*
     * Computation *
     *-------------*/

    /**
     * initiate a new computation
     *
     * @param ts          outline for teams
     * @param ps          players
     * @param is          used player array indices
     * @param evenTeams   if teams should be forced to be even
     * @param lowVariance if low variance is preferred
     * @return optimal desired teams constellation
     */
    public DistributionTeam[] compute(DistributionTeam[] ts, DistributionPlayer[] ps, List<Integer> is, int teamSize, boolean evenTeams, boolean lowVariance) {
        //initialize
        this.ps = ps;
        this.evenTeams = evenTeams;
        this.tn = ts.length;
        this.evenMax = teamSize;
        this.score = Integer.MIN_VALUE;
        //calculate
        if (lowVariance)
            greedy(ts, ps, is);
        else
            bruteForce(0, ts, is);
        //result
        return this.ts;
    }

    /**
     * @param i  current position in players array
     * @param ts team array in development
     */
    private void bruteForce(int i, DistributionTeam[] ts, List<Integer> is) {
        if (i == ps.length) {
            if (valid(ts)) compare(ts);
        } else if (is.contains(i)) {
            bruteForce(i + 1, ts, is);
        } else {
            //this prevents some duplicate team constellations
            //by not adding a player to multiple empty teams
            boolean assignedToEmptyTeam = false;
            for (int j = 0; j < tn; j++) {
                if (ts[j].length() == 0) {
                    if (assignedToEmptyTeam)
                        continue;
                    else
                        assignedToEmptyTeam = true;
                }
                DistributionTeam[] nts = deepClone(ts);
                nts[j].add(ps[i]);
                ArrayList<Integer> nis = deepClone(is);
                nis.add(i);
                bruteForce(i + 1, nts, nis);
            }
        }
    }

    /**
     * @param cts contenting teams constellation
     * @return if cts is a valid constellation
     */
    private boolean valid(DistributionTeam[] cts) {
        if (evenTeams)
            for (DistributionTeam t : cts)
                if (t.length() > evenMax) {
                    return false;
                }
        return true;
    }

    /**
     * compare quality of contenting constellation of teams
     * to the currently best constellation of teams
     *
     * @param cts contenting team constellation
     */
    private void compare(DistributionTeam[] cts) {
        int cScore = evaluate(cts);
        if (cScore > score) {
            ts = cts;
            score = cScore;
        }
    }

    /**
     * evaluate the quality of cts
     *
     * @param cts contending team constellation
     * @return score of cts
     */
    private int evaluate(DistributionTeam[] cts) {
        int internalScore = 0;
        int[] ss = new int[tn];
        for (int i = 0; i < tn; i++) {
            ss[i] = cts[i].sum();
        }
        for (int i = 0; i < tn - 1; i++)
            for (int j = i + 1; j < tn; j++)
                internalScore -= Math.abs(ss[i] - ss[j]);
        return internalScore;
    }

    /**
     * greedy approach of distributing players on teams
     * allegedly generates teams with lowest variance
     * <p>
     * assigns player with highest elo to lowest elo team
     *
     * @param cts contending constellation of teams
     * @param ps  array of players
     * @param is  list of used indices in players array
     */
    private void greedy(DistributionTeam[] cts, DistributionPlayer @NotNull [] ps, @NotNull List<Integer> is) {
        while (is.size() < ps.length) {
            int pi = maxPlayerIndex(ps, is);
            is.add(pi);
            minTeam(cts).add(ps[pi]);
        }
        ts = cts;
    }

    /**
     * takes even teams choice into considerations
     *
     * @param ts array of teams
     * @return team with the lowest total elo
     */
    private DistributionTeam minTeam(DistributionTeam[] ts) {
        int i = 0;
        if (evenTeams)
            while (ts[i].length() >= evenMax)
                i++;
        DistributionTeam mt = ts[i];
        int min = mt.sum();
        for (int j = 1; j < tn; j++) {
            DistributionTeam t = ts[j];
            int elo = t.sum();
            if (elo < min) {
                if (evenTeams && t.length() >= evenMax)
                    continue;
                mt = t;
                min = elo;
            }
        }
        return mt;
    }

    /**
     * @param ps array of players
     * @param is list of used indices for players array
     * @return player with highest elo (who hasn't been assigned yet)
     */
    private int maxPlayerIndex(@NotNull DistributionPlayer[] ps, @NotNull List<Integer> is) {
        int i = 0;
        while (is.contains(i)) i++;
        int max = ps[i].elo();
        int k = i;
        for (int j = i; j < ps.length; j++) {
            if (!is.contains(j)) {
                int elo = ps[j].elo();
                if (elo > max) {
                    k = j;
                    max = elo;
                }
            }
        }
        return k;
    }

    /*---------*
     * Utility *
     *---------*/

    /**
     * @return deep cloned teams array
     */
    @NotNull
    private DistributionTeam[] deepClone(@NotNull DistributionTeam[] ts) {
        DistributionTeam[] nts = new DistributionTeam[tn];
        for (int i = 0; i < tn; i++) {
            nts[i] = new DistributionTeam(ts[i].getName());
            for (DistributionPlayer p : ts[i].getPlayers())
                nts[i].add(p);
        }
        return nts;
    }

    /**
     * @return deep cloned indices array list
     */
    @Contract("_ -> new")
    private @NotNull ArrayList<Integer> deepClone(List<Integer> is) {
        return new ArrayList<>(is);
    }
}
