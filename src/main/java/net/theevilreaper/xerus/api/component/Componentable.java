package net.theevilreaper.xerus.api.component;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@link Componentable} interface is used to manage components within a structure.
 * It allows adding, checking, retrieving, and removing components of a specific type.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.8.0
 */
public interface Componentable {

    /**
     * Adds a component to a structure.
     *
     * @param componentClass the class of the component to add
     * @param component      the component to add
     * @param <T>            the type of the component
     */
    <T extends ObjectComponent> void add(@NotNull Class<T> componentClass, @NotNull T component);

    /**
     * Checks if a structure has a specific component.
     *
     * @param componentClass the class of the component to check
     * @param <T>            the type of the component
     * @return true if the room has the component, false otherwise
     */
    <T extends ObjectComponent> boolean has(@NotNull Class<T> componentClass);

    /**
     * Get a component of the specified class.
     *
     * @param componentClass the class of the component to remove
     * @param <T>            the type of the component
     * @return the component if it was present, null otherwise
     */
    <T extends ObjectComponent> @Nullable T get(@NotNull Class<T> componentClass);

    /**
     * Remoeves a component of the specified class from a structure.
     *
     * @param componentClass the class of the component to remove
     * @param <T>            the type of the component
     * @return the removed component if it was present, null otherwise
     */
    <T extends ObjectComponent> @Nullable T remove(@NotNull Class<T> componentClass);
}
