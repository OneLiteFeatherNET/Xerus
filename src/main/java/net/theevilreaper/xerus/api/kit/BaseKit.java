package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link BaseKit} is an abstract layer implementation of the {@link Kit} interface.
 * It contains the general structure of a kit and can be used to define custom implementations.
 *
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 **/
public abstract class BaseKit implements Kit {

    private final Map<Class<? extends ObjectComponent>, ObjectComponent> components;
    private final Key key;

    /**
     * Creates a new instance of the {@link BaseKit}.
     */
    protected BaseKit(@NotNull Key key) {
        this.key = key;
        this.components = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> void add(@NotNull Class<T> componentClass, @NotNull T component) {
        this.components.computeIfAbsent(componentClass, k -> component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> boolean has(@NotNull Class<T> componentClass) {
        return this.components.containsKey(componentClass);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public <T extends ObjectComponent> @Nullable T get(@NotNull Class<T> componentClass) {
        return componentClass.cast(this.components.get(componentClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> @Nullable T remove(@NotNull Class<T> componentClass) {
        return componentClass.cast(this.components.remove(componentClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Key key() {
        return this.key;
    }
}
