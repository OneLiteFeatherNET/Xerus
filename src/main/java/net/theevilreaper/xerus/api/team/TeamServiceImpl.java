package net.theevilreaper.xerus.api.team;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The default implementation of the {@link TeamService} interface.
 *
 * @author theEvilReaper
 * @version 1.0.3
 * @since 1.0.1
 */
@ApiStatus.NonExtendable
public final class TeamServiceImpl implements TeamService {

    private final List<Team> teams;

    /**
     * Creates a new instance from the {@link TeamServiceImpl}.
     */
    public TeamServiceImpl() {
        this.teams = new ArrayList<>();
    }

    /**
     * Creates a new instance from the {@link TeamServiceImpl} with the given parameter.
     *
     * @param capacity The size of the underlying list
     */
    public TeamServiceImpl(int capacity) {
        this.teams = new ArrayList<>(capacity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@NotNull Team team) {
        this.teams.add(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(@NotNull Team team) {
        this.teams.remove(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(@NotNull String identifier) {
        this.teams.removeIf(team -> team.getIdentifier().equals(identifier));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        if (this.teams.isEmpty()) return;

        for (int i = 0; i < this.teams.size(); i++) {
            this.teams.get(i).clearPlayers();
        }

        this.teams.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<@Nullable Team> getTeam(@NotNull String identifier) {
        int i = 0;

        while (i < teams.size() && !teams.get(i).getIdentifier().equals(identifier)) {
            i++;
        }

        return Optional.ofNullable(teams.get(i));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(@NotNull String identifier) {
        if (teams.isEmpty()) {
            return false;
        }

        int i = 0;

        while (i < teams.size() && !teams.get(i).getIdentifier().equals(identifier)) {
            i++;
        }

        return i != teams.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<@Nullable Team> getTeam(@NotNull Player player) {
        Team team = null;

        for (int i = 0; i < getTeams().size() && team == null; i++) {
            if (getTeams().get(i).hasPlayer(player)) {
                team = getTeams().get(i);
            }
        }

        return Optional.ofNullable(team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<@Nullable Team> getSmallestTeam() {
        if (!teams.isEmpty()) {
            int i = 1;
            var team = teams.getFirst();

            while (i < teams.size() && teams.get(i).getCurrentSize() < team.getCurrentSize()) {
                team = teams.get(i);
                i++;
            }

            return Optional.ofNullable(team);
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Contract(pure = true)
    @Override
    public @NotNull @UnmodifiableView List<Team> getTeams() {
        return Collections.unmodifiableList(teams);
    }
}