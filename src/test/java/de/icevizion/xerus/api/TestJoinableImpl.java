package de.icevizion.xerus.api;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

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
    public void addPlayers(@NotNull Set<Player> players) {
        this.players.addAll(players);
    }

    @Override
    public void removePlayer(@NotNull Player paramPlayer) {
        this.players.remove(paramPlayer);
    }

    @Override
    public void removePlayers(@NotNull Set<Player> players) {
        this.players.removeAll(players);
    }

    public Set<Player> getPlayers() {
        return players;
    }
}

