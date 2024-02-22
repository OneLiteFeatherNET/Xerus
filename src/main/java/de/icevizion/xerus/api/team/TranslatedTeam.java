package de.icevizion.xerus.api.team;

import at.rxcki.strigiformes.MessageProvider;
import at.rxcki.strigiformes.TranslatedObjectCache;
import de.icevizion.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import static de.icevizion.xerus.api.team.TeamImpl.DEFAULT_CAPACITY;

/**
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.0.13
 **/
public class TranslatedTeam implements Team {

    private final MessageProvider messageProvider;
    private final TranslatedObjectCache<String> nameCache;
    private final Set<Player> players;
    private final ColorData colorData;
    private final String key;
    private int capacity;

    /**
     * Creates a new instance from the team.
     * @param messageProvider Valid instance to an {@link MessageProvider}
     * @param initialCapacity for the team
     * @param key for the name of the team
     * @param colorData for the team
     */
    public TranslatedTeam(MessageProvider messageProvider, String key, int initialCapacity, ColorData colorData) {
        this.messageProvider = messageProvider;
        this.key = key;
        this.colorData = colorData;
        this.capacity = initialCapacity;
        if (initialCapacity == DEFAULT_CAPACITY) {
            this.players = new HashSet<>();
        } else {
            this.players = HashSet.newHashSet(initialCapacity);
        }
        this.nameCache = createCache();
    }

    /**
     * Creates a new instance from the team.
     * @param messageProvider Valid instance to an {@link MessageProvider}
     * @param key for the name of the team
     * @param colorData for the team
     */
    public TranslatedTeam(MessageProvider messageProvider, String key, ColorData colorData) {
        this.messageProvider = messageProvider;
        this.key = key;
        this.colorData = colorData;
        this.capacity = -1;
        this.players = new HashSet<>();
        this.nameCache = createCache();
    }

    /**
     * Creates a new instance from the team.
     * @param messageProvider Valid instance to an {@link MessageProvider}
     * @param key for the name of the team
     * @param capacity the size of the team.
     * @param colorData the {@link ColorData} for the team
     * @return the created object
     */
    @Contract("_, _, _, _ -> new")
    public static @NotNull TranslatedTeam of(MessageProvider messageProvider, String key, int capacity, ColorData colorData) {
        return new TranslatedTeam(messageProvider, key, capacity, colorData);
    }

    /**
     * Creates a new instance from the team.
     * @param messageProvider Valid instance to an {@link MessageProvider}
     * @param key for the name of the team
     * @param colorData the {@link ColorData} for the team
     * @return the created object
     */
    @Contract("_, _, _ -> new")
    public static @NotNull TranslatedTeam of(MessageProvider messageProvider, String key, ColorData colorData) {
        return new TranslatedTeam(messageProvider, key, colorData);
    }

    /**
     * Creates the cache for the name.
     * @return the created cache instance
     */
    @Contract(value = " -> new", pure = true)
    private @NotNull TranslatedObjectCache<String> createCache() {
        return new TranslatedObjectCache<>(locale -> messageProvider.getTextProvider().getString(key, locale));
    }

    /**
     * Set / overwrite the capacity of the team.
     * @param capacity The capacity to set
     */
    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Checks if a player can join a team or not.
     * @return true when a player can join otherwise false
     */
    @Override
    public boolean canJoin() {
        return capacity == -1 || players.size() < capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslatedTeam team = (TranslatedTeam) o;
        return capacity == team.capacity &&
                key.equals(team.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, capacity);
    }

    /**
     * Returns the identifier of the team.
     * @return the underlying value
     */
    @Override
    @NotNull
    public String getIdentifier() {
        return key;
    }

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    @Override
    public String getName(Locale locale) {
        return nameCache.get(locale);
    }

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    @Override
    public String getColoredName(Locale locale) {
        return getColorData().getColor() + nameCache.get(locale);
    }

    /**
     * Returns the given color data from the team.
     * @return the color data
     */
    @Override
    public @NotNull ColorData getColorData() {
        return colorData;
    }

    /**
     * Returns the maximum capacity of the team.
     * @return -1 when no capacity is set otherwise the capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the current size of the team.
     * @return the current size
     */
    @Override
    public int getCurrentSize() {
        return players.size();
    }

    /**
     * Returns a set with all current players in the team.
     * @return The underlying set with the players
     */
    @NotNull
    public Set<Player> getPlayers() {
        return players;
    }
}
