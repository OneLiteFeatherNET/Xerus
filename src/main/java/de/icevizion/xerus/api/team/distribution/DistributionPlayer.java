package de.icevizion.xerus.api.team.distribution;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/02/2020 20:26
 * <p>
 * Taken from: <a href="https://github.com/Tobi208/TeamSplitterFX">...</a>
 */
public record DistributionPlayer(@NotNull UUID uuid, int elo) {
}
