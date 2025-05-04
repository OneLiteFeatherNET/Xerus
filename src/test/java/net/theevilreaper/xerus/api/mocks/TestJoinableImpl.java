package net.theevilreaper.xerus.api.mocks;

import net.theevilreaper.xerus.api.Joinable;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class TestJoinableImpl implements Joinable {

    private final Set<Player> players;

    public TestJoinableImpl() {
        this.players = new HashSet<>();
    }

    @Override
    public void addPlayer(@NotNull Player paramPlayer) {
        this.players.add(paramPlayer);
    }

    @Override
    public void addPlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer) {
        this.players.add(paramPlayer);

        if (consumer != null) {
            consumer.accept(paramPlayer);
        }
    }

    @Override
    public void addPlayers(@NotNull Set<Player> players) {
        this.players.addAll(players);
    }

    @Override
    public void addPlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {

    }

    @Override
    public void removePlayer(@NotNull Player paramPlayer) {
        this.players.remove(paramPlayer);
    }

    @Override
    public void removePlayer(@NotNull Player paramPlayer, @Nullable Consumer<Player> consumer) {

    }

    @Override
    public void removePlayers(@NotNull Set<Player> players) {
        this.players.removeAll(players);
    }

    @Override
    public void removePlayers(@NotNull Set<Player> players, @Nullable Consumer<Player> consumer) {

    }

    public Set<Player> getPlayers() {
        return players;
    }
}

