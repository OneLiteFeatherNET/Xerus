package de.icevizion.xerus.api.team.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamImplActionTest {

    @Test
    void testAddAction() {
        assertSame(TeamEvent.Action.ADD, TeamEvent.Action.ADD);
    }

    @Test
    void testRemoveAction() {
        assertSame(TeamEvent.Action.REMOVE, TeamEvent.Action.REMOVE);
    }
}