package net.theevilreaper.xerus.api.kit;

import net.theevilreaper.xerus.api.kit.event.PlayerKitChangeEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public final class KitServiceImpl implements KitService {

    private static final Logger KIT_LOGGER = LoggerFactory.getLogger(KitServiceImpl.class);
    private final List<Kit> kits;
    private final Map<Player, Kit> usedKits;

    /**
     * Creates a new instance of the {@link KitServiceImpl}.
     */
    public KitServiceImpl() {
        this.kits = new ArrayList<>();
        this.usedKits = new HashMap<>();
    }

    /**
     * Creates a new instance of the {@link KitServiceImpl} with a given capacity.
     *
     * @param capacity the size of the underlying list
     */
    public KitServiceImpl(int capacity) {
        this.kits = new ArrayList<>(capacity);
        this.usedKits = new HashMap<>();
    }

    /**
     * Add a specific player and kit to the underlying cache.
     *
     * @param player the player to add
     * @param kit    the kit to add
     */
    public void add(@NotNull Player player, @NotNull Kit kit) {
        this.usedKits.put(player, kit);
    }

    /**
     * Remove a player from the underlying cache.
     *
     * @param player the player to remove
     * @return the current {@link Kit} from the player
     */
    public Kit remove(@NotNull Player player) {
        return this.usedKits.remove(player);
    }

    /**
     * Clears the underlying map and the inventories from each who is currently in the map
     */
    public void invalidatePlayerCache() {
        if (this.usedKits.isEmpty()) return;

        for (Player player : usedKits.keySet()) {
            player.getInventory().clear();
        }
        usedKits.clear();
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
     * Changes the current kit from a player.
     *
     * @param player the player who changes his current kit
     * @param newKit the new {@link Kit}
     */
    public void changeKit(@NotNull Player player, @NotNull Kit newKit) {
        var oldKit = usedKits.remove(player);

        var event = new PlayerKitChangeEvent(player, oldKit, newKit);

        if (event.isCancelled()) return;

        this.usedKits.put(player, newKit);
        MinecraftServer.getGlobalEventHandler().call(event);
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
    public boolean remove(@NotNull Kit kit) {
        return this.kits.remove(kit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(@NotNull String identifier) {
        if (identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("The identifier can not be empty");
        }

        return this.kits.removeIf(iKit -> iKit.getIdentifier().equals(identifier) || iKit.getName().equals(identifier));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Kit> getKit(@NotNull String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The name can not be empty");
        }
        Kit kit = null;
        for (int i = 0; i < kits.size() && kit == null; i++) {
            if (kits.get(i).getIdentifier().equals(name)) {
                kit = kits.get(i);
            }
        }

        return Optional.ofNullable(kit);
    }

    /**
     * Returns a kit from a given player.
     * When the player has no active kit yet. The method returns a empty {@link Optional}
     *
     * @param player the player to determine his kit
     * @return the fetched kit.
     */
    public Optional<Kit> getKit(@NotNull Player player) {
        if (!usedKits.isEmpty()) {
            for (var entry : usedKits.entrySet()) {
                if (!entry.getKey().getUuid().equals(player.getUuid())) continue;
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<Kit> getKits() {
        return kits;
    }

    /**
     * Returns a map which contains all player and kits which are currently in use.
     *
     * @return the underlying map
     */
    public Map<Player, Kit> getUsedKits() {
        return usedKits;
    }
}