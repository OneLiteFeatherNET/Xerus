package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The class represents a default implementation of the {@link KitService} interface.
 * It has some basic functionality for managing kits and which players are currently using them.
 * If your use case doesn't fit into this please implement your own {@link KitService} implementation.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.2.0
 **/
public final class DefaultKitService implements KitService {

    private static final Logger KIT_LOGGER = LoggerFactory.getLogger(DefaultKitService.class);
    private final List<Kit> kits;

    /**
     * Creates a new instance of the {@link DefaultKitService}.
     */
    DefaultKitService() {
        this.kits = new ArrayList<>();
    }

    /**
     * Clears the underlying cache for the kits.
     */
    @Override
    public void clear() {
        if (this.kits.isEmpty()) return;
        this.kits.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@NotNull Kit kit) {
        if (kits.contains(kit)) {
            KIT_LOGGER.info("Overwriting existing kit!");
        }
        this.kits.add(kit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(@NotNull Key identifier) {
        return this.kits.removeIf(iKit -> iKit.key().equals(identifier));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<@Nullable Kit> getKit(@NotNull Key name) {
        Kit kit = null;
        for (int i = 0; i < kits.size() && kit == null; i++) {
            if (kits.get(i).key().equals(name)) {
                kit = kits.get(i);
            }
        }

        return Optional.ofNullable(kit);
    }

    /**
     * {@inheritDoc}
     */
    @Contract(pure = true)
    @Override
    public @NotNull @UnmodifiableView List<Kit> getKits() {
        return Collections.unmodifiableList(this.kits);
    }
}
