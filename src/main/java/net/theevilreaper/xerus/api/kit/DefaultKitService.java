package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class represents a default implementation of the {@link KitService} interface.
 * It has some basic functionality for managing kits and which players are currently using them.
 * If your use case doesn't fit into this please implement your own {@link KitService} implementation.
 *
 * @author theEvilReaper
 * @version 1.2.0
 * @since 1.2.0
 **/
public final class DefaultKitService implements KitService {

    private static final Logger KIT_LOGGER = LoggerFactory.getLogger(DefaultKitService.class);
    private final Map<Key, Kit> kits;

    /**
     * Creates a new instance of the {@link DefaultKitService}.
     */
    DefaultKitService() {
        this.kits = new ConcurrentHashMap<>();
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
    public void add(Kit kit) {
        if (this.kits.containsKey(kit.key())) {
            KIT_LOGGER.info("Overwriting existing kit!");
        }
        this.kits.put(kit.key(), kit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Key identifier) {
        return this.kits.remove(identifier) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Kit> getKit(Key name) {
        return Optional.ofNullable(this.kits.get(name));
    }

    /**
     * {@inheritDoc}
     */
    @Contract(pure = true)
    @Override
    public @UnmodifiableView List<Kit> getKits() {
        return List.copyOf(this.kits.values());
    }
}
