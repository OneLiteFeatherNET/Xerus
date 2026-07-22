package net.theevilreaper.xerus.api.team;

import net.kyori.adventure.key.Key;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.validate.Check;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class represents a default implementation of the {@link Team} interface.
 * It provides a generic team which should work for most use cases.
 * If a case doesn't fit into this, it is recommended to create a custom implementation or inherit from this class.
 *
 * @author theEvilReaper
 * @version 2.0.0
 * @since 1.0.0
 */
public class DefaultTeam implements Team {

    /**
     * Default capacity for the team. It indicates that the team can include a high number of players.
     */
    protected static final int DEFAULT_CAPACITY = -1;
    private final Map<Class<? extends ObjectComponent>, ObjectComponent> components;
    private final Set<Player> players;
    private final Key key;
    private int capacity;

    /**
     * Creates a new instance from the given parameters.
     *
     * @param key             the key for the team name
     * @param initialCapacity the initial capacity for the team
     */
    protected DefaultTeam(Key key, int initialCapacity) {
        this.key = key;
        this.capacity = initialCapacity;
        this.players = ConcurrentHashMap.newKeySet();
        this.components = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}}
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
     * {@inheritDoc}
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
    public void setCapacity(int capacity) {
        Check.argCondition(capacity < 0 && capacity != DEFAULT_CAPACITY, "The capacity of the team can't be negative");
        this.capacity = capacity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canJoin() {
        return capacity == DEFAULT_CAPACITY || players.size() != capacity;
    }

    @Override
    public int compare(Team o1, Team o2) {
        return o1.key().compareTo(o2.key());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultTeam that)) return false;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Key key() {
        return this.key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentSize() {
        return players.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Player> getPlayers() {
        return players;
    }
}