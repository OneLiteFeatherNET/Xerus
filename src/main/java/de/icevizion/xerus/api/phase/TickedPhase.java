package de.icevizion.xerus.api.phase;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 03/01/2020 21:40
 */

public abstract class TickedPhase extends GamePhase {

    protected TickedPhase(String name) {
        super(name);
    }

    public abstract void onUpdate();
}
