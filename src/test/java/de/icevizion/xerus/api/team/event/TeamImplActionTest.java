package de.icevizion.xerus.api.team.event;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamImplActionTest {

    @Test
    void testAddAction() {
        assertSame(TeamAction.ADD, TeamAction.ADD);
    }

    @Test
    void testRemoveAction() {
        assertSame(TeamAction.REMOVE, TeamAction.REMOVE);
    }
}