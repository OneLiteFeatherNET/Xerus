package net.theevilreaper.xerus.api.mocks;

import net.theevilreaper.xerus.api.Joinable;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class TestJoinableImpl implements Joinable {

    private final Set<Player> players;

    public TestJoinableImpl() {
        this.players = new HashSet<>();
    }

    @Override
    public void addPlayer(@NotNull Player player) {
        this.players.add(player);
    }

    @Override
    public void addPlayer(@NotNull Player player, @Nullable Consumer<Player> consumer) {
        this.players.add(player);

        if (consumer != null) {
            consumer.accept(player);
        }
    }

    @Override
    public void addPlayers(@NotNull Collection<Player> players) {
        this.players.addAll(players);
    }

    @Override
    public void addPlayers(@NotNull Collection<Player> players, @Nullable Consumer<Player> consumer) {
        this.players.addAll(players);
        if (consumer != null) {
            players.forEach(consumer);
        }
    }

    @Override
    public void removePlayer(@NotNull Player player) {
        this.players.remove(player);
    }

    @Override
    public void removePlayer(@NotNull Player player, @Nullable Consumer<Player> consumer) {
        if (this.players.remove(player) && consumer != null) {
            consumer.accept(player);
        }
    }

    @Override
    public void removePlayers(@NotNull Collection<Player> players) {
        this.players.removeAll(players);
    }

    @Override
    public void removePlayers(@NotNull Collection<Player> players, @Nullable Consumer<Player> consumer) {
        this.players.removeAll(players);
        if (consumer != null) {
            players.forEach(consumer);
        }
    }

    public Set<Player> getPlayers() {
        return players;
    }
}

