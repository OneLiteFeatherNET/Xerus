package de.icevizion.xerus.api.team.distribution;

import de.icevizion.xerus.api.team.ITeam;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.0
 **/
public interface ITeamDistributor<T extends ITeam> {

    void distribute(@NotNull List<T> teams, @NotNull List<Player> players, int teamSize, ToIntFunction<Player> eloFunction,
                    boolean evenTeams, boolean lowVariance);

    void distribute(@NotNull List<T> teams, @NotNull List<Player> players, int teamSize, ToIntFunction<Player> eloFunction);
}
