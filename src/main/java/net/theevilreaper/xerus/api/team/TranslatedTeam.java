package net.theevilreaper.xerus.api.team;

import net.theevilreaper.xerus.api.ColorData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.translation.GlobalTranslator;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static net.theevilreaper.xerus.api.team.TeamImpl.DEFAULT_CAPACITY;

/**
 * The {@link TranslatedTeam} class represents a team which acts in a translation context.
 * It contains the same methods as the non translated context but requires in most case the usage of {@link Locale}s.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.0.13
 **/
public class TranslatedTeam implements Team {

    private final Map<Locale, Component> nameCache;
    private final Set<Player> players;
    private final ColorData colorData;
    private final String key;
    private int capacity;

    /**
     * Creates a new instance from the team.
     *
     * @param initialCapacity for the team
     * @param key             for the name of the team
     * @param colorData       for the team
     */
    public TranslatedTeam(@NotNull String key, @NotNull ColorData colorData, int initialCapacity) {
        this.key = key;
        this.colorData = colorData;
        this.capacity = initialCapacity;
        if (initialCapacity == DEFAULT_CAPACITY) {
            this.players = new HashSet<>();
        } else {
            this.players = HashSet.newHashSet(initialCapacity);
        }
        this.nameCache = new HashMap<>();
    }

    /**
     * Creates a new instance from the team.
     *
     * @param key       for the name of the team
     * @param colorData for the team
     */
    public TranslatedTeam(@NotNull String key, @NotNull ColorData colorData) {
        this.key = key;
        this.colorData = colorData;
        this.capacity = -1;
        this.players = new HashSet<>();
        this.nameCache = new HashMap<>();
    }

    /**
     * Creates a new instance from the team.
     *
     * @param key       for the name of the team
     * @param capacity  the size of the team.
     * @param colorData the {@link ColorData} for the team
     * @return the created object
     */
    @Contract("_, _, _ -> new")
    public static @NotNull TranslatedTeam of(@NotNull String key, @NotNull ColorData colorData, int capacity) {
        return new TranslatedTeam(key, colorData, capacity);
    }

    /**
     * Creates a new instance from the team.
     *
     * @param key       for the name of the team
     * @param colorData the {@link ColorData} for the team
     * @return the created object
     */
    @Contract("_, _ -> new")
    public static @NotNull TranslatedTeam of(@NotNull String key, @NotNull ColorData colorData) {
        return new TranslatedTeam(key, colorData);
    }

    /**
     * Set / overwrite the capacity of the team.
     *
     * @param capacity The capacity to set
     */
    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Checks if a player can join a team or not.
     *
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
     *
     * @return the underlying value
     */
    @Override
    @NotNull
    public String getIdentifier() {
        return key;
    }

    /**
     * Returns the name of the team.
     *
     * @return The name of the team
     */
    @Override
    public Component getName(Locale locale) {
        return nameCache.computeIfAbsent(locale, locale1 -> {
            final Component rawComponent = Component.translatable(key);
            return GlobalTranslator.render(rawComponent, locale1);
        });
    }

    /**
     * Returns the name of the team with its color applied in a {@link Component}.
     *
     * @return the name with the color
     */
    @Override
    public Component getColoredName(Locale locale) {
        final Component name = getName(locale);
        return name.style(Style.style(colorData.getChatColor()));
    }

    /**
     * Returns the given color data from the team.
     *
     * @return the color data
     */
    @Override
    public @NotNull ColorData getColorData() {
        return colorData;
    }

    /**
     * Returns the maximum capacity of the team.
     *
     * @return -1 when no capacity is set otherwise the capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns the current size of the team.
     *
     * @return the current size
     */
    @Override
    public int getCurrentSize() {
        return players.size();
    }

    /**
     * Returns a set with all current players in the team.
     *
     * @return the underlying set with the players
     */
    public @NotNull Set<Player> getPlayers() {
        return players;
    }
}
