package de.icevizion.xerus.api;

import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JoinableTest {

    TestJoinableImpl joinable;

    Player debugPlayer;

    Set<Player> players;

    @BeforeAll
    void init() {
        this.debugPlayer = Mockito.mock(Player.class);
        this.players = Set.of(Mockito.mock(Player.class), Mockito.mock(Player.class));
        this.joinable = new TestJoinableImpl();
    }

    @Order(1)
    @Test
    void testSingleAdd() {
        joinable.addPlayer(debugPlayer);
        assertEquals(1, joinable.getPlayers().size());
    }

    @Order(2)
    @Test
    void testMultiAdd() {
        joinable.addPlayers(players);
        assertEquals(3, joinable.getPlayers().size());
    }

    @Order(3)
    @Test
    void testSingleRemove() {
        joinable.removePlayer(debugPlayer);
        assertEquals(2, joinable.getPlayers().size());
    }

    @Order(4)
    @Test
    void testMultiRemove() {
        joinable.removePlayers(players);
        assertEquals(0, joinable.getPlayers().size());
    }
}