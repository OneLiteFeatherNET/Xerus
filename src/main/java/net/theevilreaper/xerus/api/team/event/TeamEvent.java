package net.theevilreaper.xerus.api.team.event;

import net.theevilreaper.xerus.api.team.Team;
import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class TeamEvent<T extends Team> implements Event {

    private final T team;
    private final Action action;

    protected TeamEvent(@NotNull T team, @NotNull Action action) {
        this.team = team;
        this.action = action;
    }

    public @NotNull T getTeam() {
        return team;
    }

    public @NotNull Action getAction() {
        return action;
    }

    public enum Action {

        ADD, REMOVE
    }
}
