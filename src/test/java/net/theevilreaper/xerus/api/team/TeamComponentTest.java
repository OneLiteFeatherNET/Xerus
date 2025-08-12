package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import net.theevilreaper.xerus.api.component.team.ColorComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeamComponentTest {

    @Test
    void testComponentAddition() {
        Team team = Team.of(Key.key("xerus", "team_a"));

        ColorComponent colorComponent = new ColorComponent(ColorData.AQUA);

        team.add(ColorComponent.class, colorComponent);

        ColorComponent givenComponent = team.get(ColorComponent.class);

        assertNotNull(givenComponent);
        assertEquals(colorComponent, givenComponent);
    }

    @Test
    void testComponentRemoval() {
        Team team = Team.of(Key.key("xerus", "team_a"));

        ObjectComponent component = team.remove(ColorComponent.class);
        assertNull(component);

        ColorComponent colorComponent = new ColorComponent(ColorData.AQUA);

        team.add(ColorComponent.class, colorComponent);

        ColorComponent givenComponent = team.remove(ColorComponent.class);

        assertNotNull(givenComponent);
        assertEquals(colorComponent, givenComponent);
    }


    @Test
    void testHasComponent() {
        Team team = Team.of(Key.key("xerus", "team_a"));
        assertFalse(team.has(ColorComponent.class));

        ColorComponent colorComponent = new ColorComponent(ColorData.AQUA);
        team.add(ColorComponent.class, colorComponent);
        assertTrue(team.has(ColorComponent.class));
    }
}
