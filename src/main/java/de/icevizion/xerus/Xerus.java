package de.icevizion.xerus;

import net.minestom.server.extensions.Extension;

public class Xerus extends Extension {

    @Override
    public void initialize() {
        getLogger().info("""

                __   __                  \s
                \\ \\ / /                  \s
                 \\ V / ___ _ __ _   _ ___\s
                  > < / _ \\ '__| | | / __|
                 / . \\  __/ |  | |_| \\_ \\
                /_/ \\_\\___|_|   \\__,_|___/""");
        getLogger().info("Starting Xerus v{}", getOrigin().getVersion());
    }

    @Override
    public void terminate() {
        // Nothing to do
    }
}