package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestKit extends BaseKit {

    public TestKit() {
        super(Key.key("xerus", "test_kit"));
    }

    public TestKit(@NotNull Key key) {
        super(key);
    }

    @Override
    public void apply(@NotNull Player player) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}