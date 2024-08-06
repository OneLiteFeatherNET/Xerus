package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * The class represents the implementation of the {@link Team.Builder} interface.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 */
@ApiStatus.NonExtendable
public final class TeamBuilder implements Team.Builder {

    private final TeamCreator teamCreator;
    private String name;
    private ColorData colorData;
    private int capacity = TeamImpl.DEFAULT_CAPACITY;

    /**
     * Creates a new instance from the {@link TeamBuilder}.
     *
     * @param teamCreator the creator for the team
     */
    TeamBuilder(@NotNull TeamCreator teamCreator) {
        this.teamCreator = teamCreator;
    }

    /**
     * Creates a new instance from the {@link TeamBuilder} with the default implementation of the {@link TeamCreator} interface.
     */
    TeamBuilder() {
        this(DefaultTeamCreator::createDefaultTeam);
    }

    /**
     * Set the name for a team.
     *
     * @param name the new name set
     * @return the builder instance
     */
    @Override
    public Team.@NotNull Builder name(@NotNull String name) {
        Check.argCondition(name.trim().isEmpty(), "The name can't be empty");
        this.name = name;
        return this;
    }

    /**
     * Set the {@link ColorData} for the team.
     *
     * @param colorData the {@link ColorData} to set
     * @return the builder instance
     */
    @Override
    public Team.@NotNull Builder colorData(@NotNull ColorData colorData) {
        this.colorData = colorData;
        return this;
    }

    /**
     * Set the initial capacity for the underlying structure which holds the players from a team.
     *
     * @param capacity the capacity to set
     * @return the builder instance
     */
    @Override
    public Team.@NotNull Builder capacity(int capacity) {
        Check.argCondition(capacity < 0, "The size can't be negative");
        this.capacity = capacity;
        return this;
    }

    /**
     * Returns a new instance from a {@link Team}.
     *
     * @return the created instance
     */
    @Override
    public @NotNull Team build() {
        return this.teamCreator.createTeam(name, colorData, capacity);
    }
}
