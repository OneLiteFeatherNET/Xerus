package net.theevilreaper.xerus.api.team.distribution;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The {@link DistributionPlayer} class represents a player which can be distributed to a team.
 * It contains an uuid from an entry and their elo rating.
 * <p>
 * Taken from: <a href="https://github.com/Tobi208/TeamSplitterFX">...</a>
 *
 * @param uuid the uuid of the player
 * @param elo  the elo rating of the player
 * @author Patrick Zdarsky / Rxcki
 * @version 1.1.0
 * @since 03/02/2020 20:26
 */
public record DistributionPlayer(@NotNull UUID uuid, int elo) {
}
