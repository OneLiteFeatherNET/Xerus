package de.icevizion.xerus.api.team;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.ColorData;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class is the implementation for {@link Team.Builder} interface.
 * @author theEvilReaper
 * @since 1.2.0
 * @version 1.0.0
 */
public non-sealed class TeamBuilder implements Team.Builder {

    private String name;
    private ColorData colorData;
    private IItem icon;
    private int capacity = TeamImpl.DEFAULT_CAPACITY;
    private final TeamCreator teamCreator;

    protected TeamBuilder(@Nullable TeamCreator teamCreator) {
        this.teamCreator = teamCreator;
    }

    /**
     * Set the name for a team.
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
     * @param colorData the {@link ColorData} to set
     * @return the builder instance
     */
    @Override
    public Team.@NotNull Builder colorData(@NotNull ColorData colorData) {
        this.colorData = colorData;
        return this;
    }

    /**
     * Set the icon for the team.
     * @param icon the icon to set
     * @return the builder instance
     */
    @Override
    public Team.@NotNull Builder icon(@NotNull IItem icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Set the initial capacity for the underlying structure which holds the players from a team.
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
     * @return the created instance
     */
    @Override
    public @NotNull Team build() {
        if (this.teamCreator != null) {
            return this.buildWithCreator();
        }
        var team = new TeamImpl(name, colorData, capacity);
        team.setIcon(icon);
        return team;
    }

    private @NotNull Team buildWithCreator() {
        return this.teamCreator.createTeam(name, colorData, capacity);
    }
}
