package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The default implementation of the {@link TeamService} interface.
 *
 * @author theEvilReaper
 * @version 1.3.0
 * @since 1.0.1
 */
public final class StandardTeamService implements TeamService {

    private final Map<Key, Team> teams;

    /**
     * Creates a new instance from the {@link StandardTeamService}.
     */
    StandardTeamService() {
        this.teams = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Team team) {
        this.teams.put(team.key(), team);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Team team) {
        this.teams.remove(team.key());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Key identifier) {
        this.teams.remove(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        if (this.teams.isEmpty()) return;

        for (Team team : this.teams.values()) {
            team.clearPlayers();
        }

        this.teams.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<@Nullable Team> getTeam(Key identifier) {
        return Optional.ofNullable(this.teams.get(identifier));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(Key identifier) {
        return this.teams.containsKey(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<@Nullable Team> getTeam(Player player) {
        for (Team team : this.teams.values()) {
            if (team.hasPlayer(player)) {
                return Optional.of(team);
            }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<@Nullable Team> getSmallestTeam() {
        return this.teams.values().stream().min(Comparator.comparingInt(Team::getCurrentSize));
    }

    /**
     * {@inheritDoc}
     */
    @Contract(pure = true)
    @Override
    public @UnmodifiableView List<Team> getTeams() {
        return List.copyOf(this.teams.values());
    }
}