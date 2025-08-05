package net.theevilreaper.xerus.api.event;

import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * This event signals the end of a game when a specific defined condition is met.
 * The event supports various end-game conditions through a generic structure.
 * If the game has a more advanced use case, this event can be extended by other classes.
 *
 * @param <T> the type of the end-game condition
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 */
public class GameFinishEvent<T> implements Event {

    private final @NotNull T reason;

    /**
     * Creates a new instance of the {@link GameFinishEvent}.
     *
     * @param reason the reason why the game has ended
     */
    public GameFinishEvent(@NotNull T reason) {
        this.reason = reason;
    }

    /**
     * Returns the reason why the game has ended.
     *
     * @return the reason why the game has ended
     */
    public @NotNull T getReason() {
        return reason;
    }
}
