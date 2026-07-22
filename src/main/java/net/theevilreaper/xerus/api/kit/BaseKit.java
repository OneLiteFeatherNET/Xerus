package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.key.Key;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The {@link BaseKit} is an abstract layer implementation of the {@link Kit} interface.
 * It contains the general structure of a kit and can be used to define custom implementations.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.2.0
 **/
public abstract class BaseKit implements Kit {

    private final Map<Class<? extends ObjectComponent>, ObjectComponent> components;
    private final Key key;

    /**
     * Creates a new instance of the {@link BaseKit}.
     */
    protected BaseKit(Key key) {
        this.key = key;
        this.components = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> void add(Class<T> componentClass, T component) {
        this.components.computeIfAbsent(componentClass, k -> component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> boolean has(Class<T> componentClass) {
        return this.components.containsKey(componentClass);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public <T extends ObjectComponent> @Nullable T get(Class<T> componentClass) {
        return componentClass.cast(this.components.get(componentClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ObjectComponent> @Nullable T remove(Class<T> componentClass) {
        return componentClass.cast(this.components.remove(componentClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Key key() {
        return this.key;
    }
}
