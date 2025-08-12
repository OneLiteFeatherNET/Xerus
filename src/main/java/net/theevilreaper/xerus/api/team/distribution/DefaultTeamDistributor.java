package net.theevilreaper.xerus.api.team.distribution;

import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Default implementation of the {@link TeamDistributor} interface.
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:32
 */
public class DefaultTeamDistributor implements TeamDistributor {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("java:S3776")
    @Override
    public void distribute(@NotNull List<Team> teams, @NotNull List<Player> players, int teamSize,
                           @NotNull ToIntFunction<Player> eloFunction, boolean evenTeams, boolean lowVariance) {
        if (teams.isEmpty()) {
            throw new IllegalArgumentException("The list with the teams can not be empty");
        }

        if (players.isEmpty()) {
            throw new IllegalArgumentException("The list with the players can not be empty");
        }

        //Wrap Teams
        DistributionTeam[] dTeams = new DistributionTeam[teams.size()];
        for (int i = 0; i < teams.size(); i++) {
            var currentTeam = teams.get(i);
            dTeams[i] = new DistributionTeam(currentTeam.key());
            if (teams.get(i).getCurrentSize() > 0) {
                for (Player player : currentTeam.getPlayers())
                    dTeams[i].add(new DistributionPlayer(player.getUuid(), eloFunction.applyAsInt(player)));
            }
        }

        //Wrap Players
        DistributionPlayer[] dPlayers = new DistributionPlayer[players.size()];
        for (int i = 0; i < players.size(); i++) {
            var player = players.get(i);
            dPlayers[i] = new DistributionPlayer(player.getUuid(), eloFunction.applyAsInt(player));
        }

        //Distribute
        var distributedTeams = new Splitter().compute(dTeams, dPlayers, new ArrayList<>(), teamSize, evenTeams, lowVariance);

        //Unwrap
        for (DistributionTeam distributionTeam : distributedTeams) {
            Team team = null;

            for (int i = 0; i < teams.size() && team == null; i++) {
                if (!teams.get(i).key().equals(distributionTeam.name())) continue;
                team = teams.get(i);
            }

            if (team == null) return;

            for (DistributionPlayer player : distributionTeam.players()) {
                final Player realPlayer = MinecraftServer.getConnectionManager().getOnlinePlayerByUuid(player.uuid());

                if (realPlayer == null) continue;
                team.addPlayer(realPlayer);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void distribute(@NotNull List<Team> teams, @NotNull List<Player> players, int teamSize,
                           @NotNull ToIntFunction<Player> eloFunction) {
        distribute(teams, players, teamSize, eloFunction, true, false);
    }
}
