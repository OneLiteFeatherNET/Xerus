package net.theevilreaper.xerus.api.kit;

import net.minestom.server.item.ItemStack;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KitTest {

    @Test
    void testKitComponent() {
        Kit kit = new TestKit();
        assertInstanceOf(BaseKit.class, kit);

        kit.add(IconComponent.class, new IconComponent(ItemStack.AIR));

        assertTrue(kit.has(IconComponent.class));

        ObjectComponent component = kit.get(IconComponent.class);
        assertNotNull(component);
        assertInstanceOf(IconComponent.class, component);
    }

    @Test
    void testKitComponentRemoval() {
        Kit kit = new TestKit();
        kit.add(IconComponent.class, new IconComponent(ItemStack.AIR));

        ObjectComponent component = kit.remove(IconComponent.class);
        assertNotNull(component);
        assertInstanceOf(IconComponent.class, component);

        assertFalse(kit.has(IconComponent.class));
        assertNull(kit.get(IconComponent.class), "The component should be null after removal");
    }

    private record IconComponent(@NotNull ItemStack icon) implements ObjectComponent {

    }


}