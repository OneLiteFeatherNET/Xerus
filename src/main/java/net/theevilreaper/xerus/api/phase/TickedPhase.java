package net.theevilreaper.xerus.api.phase;

/**
 * The {@code TickedPhase} class represents a game phase updated on each tick.
 * This phase can't tick by itself and must be updated by a ticking mechanism, such as a game loop or scheduler.
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0.1
 * @since 03/01/2020 21:40
 */
public abstract class TickedPhase extends GamePhase {

    /**
     * Constructs a new {@code TickedPhase} with the given name.
     *
     * @param name the name of this ticked phase
     */
    protected TickedPhase(String name) {
        super(name);
    }

    /**
     * Handles the tick update for this phase.
     */
    public abstract void onUpdate();
}
