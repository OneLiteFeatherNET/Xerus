package de.icevizion.xerus.api.team;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.ColorData;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

public non-sealed class TeamBuilder implements ITeam.Builder {

    private String name;
    private ColorData colorData;
    private IItem icon;
    private int capacity;

    @Override
    public ITeam.@NotNull Builder name(@NotNull String name) {
        Check.argCondition(name.trim().isEmpty(), "The name can't be empty");
        this.name = name;
        return this;
    }

    @Override
    public ITeam.@NotNull Builder colorData(@NotNull ColorData colorData) {
        this.colorData = colorData;
        return this;
    }

    @Override
    public ITeam.@NotNull Builder icon(@NotNull IItem icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public ITeam.@NotNull Builder capacity(int capacity) {
        Check.argCondition(capacity < 0, "The size can't be negative");
        this.capacity = capacity;
        return this;
    }

    @Override
    public @NotNull Team build() {
        var team = new Team(name, colorData, capacity);
        team.setIcon(icon);
        return team;
    }
}
