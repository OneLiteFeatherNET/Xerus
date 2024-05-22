package de.icevizion.xerus.api.event;

import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

/**
 * This event signals the end of a game when a specific defined condition is met.
 * The event supports various end-game conditions through a generic structure (<T>).
 * If the game has a more advanced use case, this event can be extended by other classes.
 * Example usage:
 * Suppose you have a game with a custom end-game condition involving player scores.
 * You can create a subclass of this event as follows:
 * {@code
 * public class CustomGameEndEvent extends GameEndEvent<CustomGameData> {
 * // Additional customizations for your game's end event
 * }
 * @param <T> The generic structure representing the end-game condition data
 * @author theEvilReaper
 * @version 1.0.0
 * @since 1.2.0
 */
public record GameFinishEvent<T>(@NotNull T reason) implements Event {
}
