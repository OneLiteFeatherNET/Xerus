package de.icevizion.xerus.api.team.distribution;

import de.icevizion.xerus.api.team.Team;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.network.ConnectionManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:32
 */
public class TeamDistributor<T extends Team> implements ITeamDistributor<T> {

    private static final ConnectionManager CONNECTION_MANAGER = MinecraftServer.getConnectionManager();

    @SuppressWarnings("java:S3776")
    @Override
    public void distribute(@NotNull List<T> teams, @NotNull List<Player> players, int teamSize,
                           ToIntFunction<Player> eloFunction, boolean evenTeams, boolean lowVariance) {
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
            dTeams[i] = DistributionTeam.of(currentTeam.getName());
            if (teams.get(i).getCurrentSize() > 0) {
                for (Player player : currentTeam.getPlayers())
                    dTeams[i].add(DistributionPlayer.of(player.getUuid(), eloFunction.applyAsInt(player)));
            }
        }

        //Wrap Players
        DistributionPlayer[] dPlayers = new DistributionPlayer[players.size()];
        for (int i = 0; i < players.size(); i++) {
            var player = players.get(i);
            dPlayers[i] = DistributionPlayer.of(player.getUuid(), eloFunction.applyAsInt(player));
        }

        //Distribute
        var distributedTeams = new Splitter().compute(dTeams, dPlayers, new ArrayList<>(), teamSize, evenTeams, lowVariance);

        //Unwrap
        for (DistributionTeam distributionTeam : distributedTeams) {
            Team team = null;

            for (int i = 0; i < teams.size() && team == null; i++) {
                if (!teams.get(i).getName().equals(distributionTeam.getName())) continue;
                team = teams.get(i);
            }

            if (team == null) return;

            for (DistributionPlayer player : distributionTeam.getPlayers()) {
                var realPlayer = CONNECTION_MANAGER.getOnlinePlayerByUuid(player.uuid());

                if (realPlayer == null) continue;
                team.addPlayer(realPlayer);
            }
        }
    }

    @Override
    public void distribute(@NotNull List<T> teams, @NotNull List<Player> players, int teamSize,
                           ToIntFunction<Player> eloFunction) {
        distribute(teams, players, teamSize, eloFunction, true, false);
    }
}
