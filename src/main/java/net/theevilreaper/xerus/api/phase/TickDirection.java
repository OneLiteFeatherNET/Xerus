package net.theevilreaper.xerus.api.phase;

/**
 * The {@link TickDirection} enumeration contains some static values which can be used to change, how a phase should process its ticks.
 * That means that the phase can be processed in an upward or downward direction of the time value.
 *
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 05/01/2020 16:27
 */
public enum TickDirection {

    /**
     * Indicates that the time should tick upwards.
     */
    UP,
    /**
     * Indicates that the time of phase should tick downwards.
     */
    DOWN
}
