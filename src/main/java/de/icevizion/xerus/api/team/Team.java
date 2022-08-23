package de.icevizion.xerus.api.team;

import de.icevizion.aves.item.IItem;
import de.icevizion.xerus.api.ColorData;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * @author theEvilReaper
 * @since 1.0.0
 * @version 1.0.3
 */
public class Team implements ITeam {

    private static final int DEFAULT_CAPACITY = -1;
    private final Set<Player> players;
    private final String name;
    private final ColorData colorData;
    private int capacity;
    private IItem icon;

    /**
     * Creates a new instance from the team.
     * @param name for the team
     * @param colorData for the team
     * @param initialCapacity for the team
     */
    public Team(@NotNull String name, @NotNull ColorData colorData, int initialCapacity) {
        this.name = name;
        this.colorData = colorData;
        this.capacity = initialCapacity;
        this.players = new HashSet<>(initialCapacity);
    }

    /**
     * Creates a new instance from the team.
     * @param name for the team
     * @param colorData for the team
     */
    public Team(@NotNull String name, @NotNull ColorData colorData) {
        this.name = name;
        this.colorData = colorData;
        this.capacity = DEFAULT_CAPACITY;
        this.players = new HashSet<>();
    }

    /**
     * Creates a new instance from the team.
     * @param name the name of team.
     * @param colorData the {@link ColorData} for the team
     * @param capacity the size of the team.
     * @return the created object
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData, int capacity) {
        return new Team(name, colorData, capacity);
    }

    /**
     * Creates a new instance from the team.
     * @param name the name of team.
     * @param colorData the {@link ColorData} for the team
     * @return the created object
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Team of(@NotNull String name, @NotNull ColorData colorData) {
        return new Team(name, colorData);
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
        return capacity == DEFAULT_CAPACITY || players.size() != capacity;
    }

    /**
     * Override or set the icon for the team.
     * The icon must be a {@link net.minestom.server.item.ItemStack}
     * @param icon The {@link net.minestom.server.item.ItemStack} which represents the icon
     */
    public void setIcon(IItem icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return capacity == team.capacity && name.equals(team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity);
    }

    /**
     * Returns the identifier from the team.
     * @return the identifier
     */
    @Override
    public @NotNull String getIdentifier() {
        return getName(null);
    }

    /**
     * Returns the name of the team.
     * @return The name of the team
     */
    @Override
    public String getName(Locale ignored) {
        return name;
    }

    /**
     * Returns the name with the color.
     * @return The name with the color
     */
    @Override
    public String getColoredName(Locale ignored) {
        return getColorData().getChatColor() + name;
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
     * Returns the icon from the team.
     * @return The {@link net.minestom.server.item.ItemStack} which represents the icon
     */
    @Override
    public @Nullable IItem getIcon() {
        return icon;
    }

    /**
     * Returns a set with all current players in the team.
     * @return The underlying set with the players
     */
    @Override
    public @NotNull Set<Player> getPlayers() {
        return players;
    }
}