package de.icevizion.xerus.api.team;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.ColorData;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

public non-sealed class TeamBuilder implements Team.Builder {

    private String name;
    private ColorData colorData;
    private IItem icon;
    private int capacity = TeamImpl.DEFAULT_CAPACITY;

    @Override
    public Team.@NotNull Builder name(@NotNull String name) {
        Check.argCondition(name.trim().isEmpty(), "The name can't be empty");
        this.name = name;
        return this;
    }

    @Override
    public Team.@NotNull Builder colorData(@NotNull ColorData colorData) {
        this.colorData = colorData;
        return this;
    }

    @Override
    public Team.@NotNull Builder icon(@NotNull IItem icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public Team.@NotNull Builder capacity(int capacity) {
        Check.argCondition(capacity < 0, "The size can't be negative");
        this.capacity = capacity;
        return this;
    }

    @Override
    public @NotNull TeamImpl build() {
        var team = new TeamImpl(name, colorData, capacity);
        team.setIcon(icon);
        return team;
    }
}
