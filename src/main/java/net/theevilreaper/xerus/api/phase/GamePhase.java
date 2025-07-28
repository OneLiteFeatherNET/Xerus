package net.theevilreaper.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * The {@link GamePhase} is a specialized implementation of the {@link Phase} class that represents
 * a specific phase in the game. It is designed to handle events and manage their listeners.
 * <p>
 * Use this class when your use case involves event handling only, without the need for ticking
 * or other game-specific behaviors.
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 14.01.2020 08:51
 */
public abstract class GamePhase extends Phase {

    private EventNode<Event> phaseNode;
    private HashMap<Class<? extends Event>, EventListener<? extends Event>> listenerHashMap;

    /**
     * Creates a new instance of {@link GamePhase} with the specified name.
     *
     * @param name the name of the phase, used to identify it within the game
     */
    protected GamePhase(@NotNull String name) {
        super(name);
    }

    /**
     * Adds a new {@link EventListener} to the given phase.
     *
     * @param eventClass the class for the listener
     * @param listener   the listener its self
     * @param <T>        the class must extend from an event
     * @return the created event listener
     */
    public <T extends Event> EventListener<T> addListener(@NotNull Class<T> eventClass, @NotNull Consumer<T> listener) {
        this.verifyValueIntegrity();
        var eventListener = EventListener.of(eventClass, listener);
        this.listenerHashMap.put(eventClass, EventListener.of(eventClass, listener));
        this.phaseNode.addListener(eventListener);
        return eventListener;
    }

    /**
     * Initializes the phase node by setting it to the given {@link EventNode}.
     *
     * @param node the {@link EventNode} to set for the phase
     * @throws IllegalArgumentException if the phase node has already been set
     */
    public void initNode(@NotNull EventNode<Event> node) {
        Check.argCondition(this.phaseNode != null, "A node can only be set once");
        this.phaseNode = node;
    }

    /**
     * Removes a listener from the phase.
     *
     * @param eventClass the class, which should be removed
     * @param <T>        the event class must inherit from the {@link Event}
     * @return the removed listener
     */
    public <T extends Event> EventListener<? extends Event> removeListener(@NotNull Class<T> eventClass) {
        if (phaseNode == null || listenerHashMap == null) return null;
        var eventListener = this.listenerHashMap.remove(eventClass);
        this.phaseNode.removeListener(eventListener);
        return eventListener;
    }

    /**
     * Starts the phase by calling the overridden start method from the superclass.
     * Adds the phaseNode to the global event handler if phaseNode is not null.
     */
    @Override
    public void start() {
        super.start();

        if (phaseNode != null) {
            MinecraftServer.getGlobalEventHandler().addChild(phaseNode);
        }
    }

    /**
     * Finishes the phase by invoking the superclass's {@code finish} method.
     * If {@code phaseNode} is not {@code null}, it is removed from the global event handler.
     */
    @Override
    public void finish() {
        super.finish();
        if (phaseNode != null) {
            MinecraftServer.getGlobalEventHandler().removeChild(phaseNode);
        }
    }

    /**
     * Verifies the integrity of some important values.
     */
    private void verifyValueIntegrity() {
        if (this.phaseNode == null) {
            this.phaseNode = EventNode.all(getName() + "Node");
        }

        if (this.listenerHashMap == null) {
            this.listenerHashMap = new HashMap<>();
        }
    }
}