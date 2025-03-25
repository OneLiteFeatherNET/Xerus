package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import de.icevizion.xerus.api.team.event.MultiPlayerTeamEvent;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamEnvTest {

    static final int TEST_AMOUNT = 5;
    Instance instance;
    Set<Player> testPlayers = new HashSet<>();
    Team team;

    @BeforeAll
    void initTeam(@NotNull Env env) {
        this.instance = env.createFlatInstance();
        this.team = Team.builder().name("TestTeam").colorData(ColorData.BLACK).capacity(5).build();
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
        this.team.addPlayers(new HashSet<>(this.testPlayers), player -> {
            player.setGameMode(GameMode.CREATIVE);
        });
        for (Player testPlayer : this.testPlayers) {
            assertEquals(GameMode.CREATIVE, testPlayer.getGameMode());
        }
    }

    @Test
    void testPlayersRemoveWithConsumer(@NotNull Env env) {
        this.team.getPlayers().addAll(testPlayers);
        this.team.removePlayers(new HashSet<>(this.testPlayers), player -> player.setGameMode(GameMode.ADVENTURE));

        for (Player testPlayer : this.testPlayers) {
            assertEquals(GameMode.ADVENTURE, testPlayer.getGameMode());
        }
    }

    @Test
    void testPlayersAddWithGivenPlayers(@NotNull Env env) {
        var additionalPlayers = new HashSet<Player>();
        for (int i = 0; i < 2; i++) {
            additionalPlayers.add(env.createPlayer(instance, Pos.ZERO));
        }
        this.team.addPlayers(additionalPlayers);
        Set<Player> playersToAdd = new HashSet<>(additionalPlayers);
        playersToAdd.addAll(additionalPlayers);
        playersToAdd.addAll(new HashSet<>(this.testPlayers));

        assertEquals(7, playersToAdd.size());
        var listener = env.listen(MultiPlayerTeamEvent.class);

        listener.followup(event -> {
            assertEquals(this.testPlayers.size(), event.getPlayers().size());
            assertTrue(playersToAdd.containsAll(this.testPlayers));
        });
        this.team.addPlayers(playersToAdd);
        for (Player additionalPlayer : additionalPlayers) {
            additionalPlayer.remove();
        }
        additionalPlayers.clear();
    }
}
