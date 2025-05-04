package net.theevilreaper.xerus.api.team;

import net.theevilreaper.xerus.api.ColorData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class that implements the {@link TeamCreator} functional interface to provide a custom way of constructing a team using the {@link Team.Builder}.
 * This class simplifies the process of creating a team and enhances its integration into custom use cases.
 *
 * <p>This implementation allows you to create a team reference from the {@link TeamImpl} class, which is part of the api.
 * It effectively reduces the complexity of the {@link TeamBuilder} class.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 */
@ApiStatus.NonExtendable
public final class DefaultTeamCreator {

    private DefaultTeamCreator() { }

    /**
     * Creates a new instance from the default team implementation which is located in this project.
     * @param name the name for the team
     * @param colorData the color data for the team
     * @param capacity the capacity for the team
     * @return the created instance of a {@link TeamImpl}
     */
    static @NotNull Team createDefaultTeam(@NotNull String name, @NotNull ColorData colorData, int capacity) {
        return new TeamImpl(name, colorData, capacity);
    }
}
