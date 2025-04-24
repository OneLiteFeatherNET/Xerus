package de.icevizion.xerus.api.team;

import de.icevizion.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.testing.Env;
import net.minestom.testing.extension.MicrotusExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MicrotusExtension.class)
public class TeamIntegrationTest {

    static Team team;

    @BeforeAll
    static void init() {
        team = Team.builder()
                .name("Team A")
                .colorData(ColorData.AQUA)
                .build();
    }

    @Test
    void testHasPlayer(@NotNull Env env) {
        Instance instance = env.createFlatInstance();
        Player player = env.createPlayer(instance);
        assertFalse(team.hasPlayer(player));

        env.destroyInstance(instance, true);
    }
}
