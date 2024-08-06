package de.icevizion.xerus.api;

import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class EquipableTest {

    private Equipable equipable;
    private Player player;

    @BeforeAll
    void init() {
        this.player = Mockito.mock(Player.class);
        this.equipable = Mockito.mock(Equipable.class);
    }

    @Test
    void testItemSetWithLocale() {
        Mockito.verify(equipable, Mockito.never()).setEquipment(player, Locale.ENGLISH, 2,34,5);
    }

    @Test
    void testWithoutShiftedSlots() {
        Mockito.verify(equipable, Mockito.never()).setEquipment(player, Locale.US);
    }

    @Test
    void testItemSetForAPlayerWithoutLocale() {
        Mockito.verify(equipable, Mockito.never()).setEquipment(player, 2,3,4,5);
    }

    @Test
    void testItemSetForAList() {
        Mockito.verify(equipable, Mockito.never()).setEquipment(List.of(player));
    }

    @Test
    void testItemSetForAListWithShiftedSlots() {
        Mockito.verify(equipable, Mockito.never()).setEquipment(List.of(player), 2,3,4,5,6,7,8);
    }

}