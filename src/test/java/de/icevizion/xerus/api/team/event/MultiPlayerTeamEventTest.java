package de.icevizion.xerus.api.team.event;

import de.icevizion.xerus.api.ColorData;
import de.icevizion.xerus.api.team.Team;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MicrotusExtension.class)
class MultiPlayerTeamEventTest {

    private Team team;

    @BeforeEach
    void init() {
        this.team = Team.of("TestTeam", ColorData.BLUE);
    }

    @AfterEach
    void cleanUp() {
        this.team.clearPlayers();
    }

    @Test
    void testEventConstructor(Env env) {
        final Instance instance = env.createFlatInstance();
        final Player player = env.createPlayer(instance, Pos.ZERO);
        var event = MultiPlayerTeamEvent.addEvent(this.team, Set.of(player));
        event.setCancelled(true);
        assertTrue(event.isCancelled());
    }

    @Test
    void testPlayerMultiTeamEvent(Env env) {
        final Instance instance = env.createFlatInstance();
        final Set<Player> players = HashSet.newHashSet(3);
        for (int i = 0; i <= 3; i++) {
            players.add(env.createPlayer(instance, Pos.ZERO));
        }
        final MultiPlayerTeamEvent<Team> event = MultiPlayerTeamEvent.addEvent(this.team, players);
        assertNotSame(3, event.getPlayers().size());
        assertSame(TeamEvent.Action.ADD, event.getAction());
        assertSame(this.team, event.getTeam());

        event.setCancelled(true);

        assertTrue(event.isCancelled());
    }
}