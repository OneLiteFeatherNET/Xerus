package de.icevizion.xerus;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.Event;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

public abstract class Game extends Extension {

    private static final CommandManager COMMAND_MANAGER = MinecraftServer.getCommandManager();
    private static final GlobalEventHandler EVENT_HANDLER = MinecraftServer.getGlobalEventHandler();

    @Override
    public void initialize() {
        this.onInitialize();
    }

    @Override
    public void terminate() {
        this.onTerminate();
    }

    public abstract void onInitialize();

    public abstract void onTerminate();

    /**
     * Creates an event call for the give event.
     * @param event The event to call
     */

    public void callEvent(@NotNull Event event) {
        EVENT_HANDLER.call(event);
    }


    /**
     * Register a command to the plugin.
     * @param commandExecutor The executor for the command
     */

    public void registerCommand(@NotNull Command commandExecutor) {
        COMMAND_MANAGER.register(commandExecutor);
    }
}