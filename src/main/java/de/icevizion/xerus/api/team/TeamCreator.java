package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TeamCreator {

    default Team createTeam(@NotNull String name, @NotNull ColorData colorData) {
        return createTeam(name, colorData, TeamImpl.DEFAULT_CAPACITY);
    }

    @NotNull Team createTeam(@NotNull String name, @NotNull ColorData colorData, int capacity);
}
