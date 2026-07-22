package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.List;
import java.util.Optional;

/**
 * The interface includes all relevant method to handle the kits for a given implementation.
 * When the developer does not want to use the {@link DefaultKitService} implementation.
 * So he must implement the interface by self and can add additional method to the service.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.2.0
 **/
public interface KitService {

    /**
     * Returns a new instance of the {@link KitService}.
     *
     * @return the new instance
     */
    @Contract(pure = true)
    static KitService of() {
        return new DefaultKitService();
    }

    /**
     * Add a kit to the service.
     *
     * @param kit that should be added
     */
    void add(Kit kit);

    /**
     * Remove a kit by the identifier from the kit.
     *
     * @param identifier the identifier from the kit
     * @return true when the kit can be removed otherwise false
     */
    boolean remove(Key identifier);

    /**
     * Clears the underlying data structure.
     */
    void clear();

    /**
     * Returns the kit based on the given name.
     *
     * @param name the name of the kit
     * @return the fetched kit in an optional
     */
    Optional<Kit> getKit(Key name);

    /**
     * Returns a list with all current available kits.
     *
     * @return the underlying list
     */
    @UnmodifiableView
    @Contract(pure = true)
    List<Kit> getKits();
}
