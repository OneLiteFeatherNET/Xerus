package de.icevizion.xerus.api.kit;

import de.icevizion.xerus.api.kit.event.PlayerKitChangeEvent;
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
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public final class KitServiceImpl implements KitService {

    private static final Logger KIT_LOGGER = LoggerFactory.getLogger(KitServiceImpl.class);

    private final List<IKit> kits;

    private final Map<Player, IKit> usedKits;

    public KitServiceImpl() {
        this.kits = new ArrayList<>();
        this.usedKits = new HashMap<>();
    }

    public KitServiceImpl(int capacity) {
        this.kits = new ArrayList<>(capacity);
        this.usedKits = new HashMap<>();
    }

    /**
     * Add a specific player and kit to the underlying cache.
     * @param player The player to add
     * @param kit The kit to add
     */
    public void add(@NotNull Player player, @NotNull IKit kit) {
        this.usedKits.put(player, kit);
    }

    /**
     * Remove a player from the underlying cache.
     * @param player The player to remove
     * @return The current {@link IKit} from the player
     */
    public IKit remove(@NotNull Player player) {
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
     * @param player The player who changes his current kit
     * @param newKit The new {@link IKit}
     */
    public void changeKit(@NotNull Player player, @NotNull IKit newKit) {
        var oldKit = usedKits.remove(player);

        var event = new PlayerKitChangeEvent(player, oldKit, newKit);

        if (event.isCancelled()) return;

        this.usedKits.put(player, newKit);
        MinecraftServer.getGlobalEventHandler().call(event);
    }

    /**
     * Add a kit to the service.
     * @param kit that should be added
     */
    @Override
    public void add(@NotNull IKit kit) {
        if (kits.contains(kit)) {
            KIT_LOGGER.info("Overwriting existing kit!");
        }
        this.kits.add(kit);
    }

    /**
     * Remove a kit from the service.
     * @param kit that should be removed
     */
    @Override
    public boolean remove(@NotNull IKit kit) {
        return this.kits.remove(kit);
    }

    @Override
    public boolean remove(@NotNull String identifier) {
        if (identifier.trim().isEmpty()) {
            throw new IllegalArgumentException("The identifier can not be empty");
        }

        return this.kits.removeIf(iKit -> iKit.getIdentifier().equals(identifier) || iKit.getName().equals(identifier));
    }

    /**
     * Returns the kit based on the given name.
     * @param name The name of the kit
     * @return The fetched kit in an optional
     */
    @Override
    public Optional<IKit> getKit(@NotNull String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("The name can not be empty");
        }
        IKit kit = null;
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
     * @param player The player to determine his kit
     * @return the fetched kit.
     */
    public Optional<IKit> getKit(@NotNull Player player) {
        if (!usedKits.isEmpty()) {
            for (var entry : usedKits.entrySet()) {
                if (!entry.getKey().getUuid().equals(player.getUuid())) continue;
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    /**
     * Returns a list with all current available kits.
     * @return the underlying list
     */
    @Override
    public List<IKit> getKits() {
        return kits;
    }

    /**
     * Returns a map which contains all player and kits which are currently in use.
     * @return the underlying map
     */
    public Map<Player, IKit> getUsedKits() {
        return usedKits;
    }
}