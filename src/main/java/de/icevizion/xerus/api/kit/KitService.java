package de.icevizion.xerus.api.kit;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * The interface includes all relevant method to handle the kits for a given implementation.
 * When the developer does not want to use the {@link KitServiceImpl} implementation.
 * So he must implement the interface by self and can add additional method to the service.
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public interface KitService {

    /**
     * Add a kit to the service.
     * @param kit that should be added
     */
    void add(@NotNull Kit kit);

    /**
     * Remove a kit from the service.
     * @param kit that should be removed
     */
    boolean remove(@NotNull Kit kit);

    /**
     * Remove a kit by the identifier from the kit.
     * @param identifier The identifier from the kit
     * @return True when the kit can be removed otherwise false
     */
    boolean remove(@NotNull String identifier);

    /**
     * Clears the underlying data structure.
     */
    void clear();

    /**
     * Returns the kit based on the given name.
     * @param name The name of the kit
     * @return The fetched kit in an optional
     */
    Optional<Kit> getKit(@NotNull String name);

    /**
     * Returns a list with all current available kits.
     * @return the underlying list
     */
    List<Kit> getKits();
}
