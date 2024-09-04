package de.icevizion.xerus.api;

import de.icevizion.xerus.api.mocks.TestJoinableImpl;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class JoinableTest {

    private TestJoinableImpl joinable;

    @BeforeEach
    void init() {
        this.joinable = new TestJoinableImpl();
    }

    @Test
    void testSingleOperation(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        this.joinable.addPlayer(player);
        assertNotEquals(0, this.joinable.getPlayers().size());
        this.joinable.removePlayer(player);
        assertEquals(0, this.joinable.getPlayers().size());
    }

    @Test
    void testMultipleOperation(Env env) {
        final Instance instance = env.createFlatInstance();
        final Set<Player> players = new HashSet<>() {
            {
                add(env.createPlayer(instance, Pos.ZERO));
                add(env.createPlayer(instance, Pos.ZERO));
                add(env.createPlayer(instance, Pos.ZERO));
            }
        };
        assertTrue(this.joinable.getPlayers().isEmpty());
        this.joinable.addPlayers(players);
        assertEquals(3, this.joinable.getPlayers().size());
        this.joinable.removePlayers(players);
        assertTrue(this.joinable.getPlayers().isEmpty());
    }
}
