package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.EnvTest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@EnvTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamEnvTest {

    static final int TEST_AMOUNT = 5;
    Instance instance;
    Set<Player> testPlayers;
    Team team;

    @BeforeAll
    void initTeam(@NotNull Env env) {
        this.instance = env.createFlatInstance();
        this.team = Team.builder().name("TestTeam").colorData(ColorData.BLACK).capacity(5).build();
        this.testPlayers = new HashSet<>();
        for (int i = 0; i < TEST_AMOUNT; i++) {
            this.testPlayers.add(env.createPlayer(instance, Pos.ZERO));
        }
    }

    @BeforeEach
    void cleanUp() {
        this.team.clearPlayers();
        this.testPlayers.forEach(player -> player.setGameMode(GameMode.SURVIVAL));
    }

    @AfterAll
    void destroy(@NotNull Env env) {
        this.testPlayers.forEach(Player::remove);
        env.destroyInstance(instance);
    }

    @Test
    void testPlayersAddWithConsumer(@NotNull Env env) {
        assertTrue(this.team.isEmpty());
        this.team.addPlayers(testPlayers, player -> player.setGameMode(GameMode.CREATIVE));
        for (Player testPlayer : this.testPlayers) {
            assertEquals(GameMode.CREATIVE, testPlayer.getGameMode());
        }
    }

    @Test
    void testPlayersRemoveWithConsumer(@NotNull Env env) {
        this.team.getPlayers().addAll(testPlayers);
        this.team.removePlayers(testPlayers, player -> player.setGameMode(GameMode.ADVENTURE));

        for (Player testPlayer : this.testPlayers) {
            assertEquals(GameMode.CREATIVE, testPlayer.getGameMode());
        }
    }
}
