package de.icevizion.xerus.api.phase;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 14.01.2020 08:51
 */
public abstract class GamePhase extends Phase {

    private EventNode<Event> phaseNode;
    private HashMap<Class<Event>, EventListener<Event>> listenerHashMap;

    protected GamePhase(@NotNull String name) {
        super(name);
    }

    public void addEvent(@NotNull Class<Event> eventClass, Consumer<Event> listener) {
        if (phaseNode == null && listenerHashMap == null) {
            phaseNode = EventNode.all(getName() + "Node");
            this.listenerHashMap = new HashMap<>();
        }
        this.listenerHashMap.put(eventClass, EventListener.of(eventClass,listener));
    }

    public void removeEvent(@NotNull Class<Event> eventClass) {
        if (phaseNode == null || listenerHashMap == null) return;

        var listener = listenerHashMap.get(eventClass);

        if (listener == null) return;
        this.phaseNode.removeListener(listener);
    }


    @Override
    public void start() {
        super.start();

        MinecraftServer.getGlobalEventHandler().addChild(phaseNode);
    }

    @Override
    public void finish() {
        super.finish();

        MinecraftServer.getGlobalEventHandler().removeChild(phaseNode);
    }
}