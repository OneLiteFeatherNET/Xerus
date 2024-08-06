package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import org.jetbrains.annotations.NotNull;

/**
 * This functional interface defines a contract for creating custom implementations of a {@link Team}.
 * Implementations of this interface can be supplied to a {@link Team.Builder} using either direct implementation
 * or method references.
 *
 * @author theEvilReaper
 * @version 1.0.3
 * @since 1.0.0
 */
@FunctionalInterface
public interface TeamCreator {

    /**
     * Creates a new instance from the {@link Team} interface.
     * This method uses the default capacity from the {@link TeamImpl} class.
     * @param name the name of the team
     * @param colorData the {@link ColorData} to set
     * @return the created instance
     */
    default @NotNull Team createTeam(@NotNull String name, @NotNull ColorData colorData) {
        return createTeam(name, colorData, TeamImpl.DEFAULT_CAPACITY);
    }

    /**
     * Creates a new instance from the {@link Team} interface.
     * @param name the name of the team
     * @param colorData the {@link ColorData} to set
     * @param capacity the capacity of the team
     * @return the created instance
     */
    @NotNull Team createTeam(@NotNull String name, @NotNull ColorData colorData, int capacity);
}
