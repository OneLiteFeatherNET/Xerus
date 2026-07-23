package net.theevilreaper.xerus.api.component.team;

import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.component.ObjectComponent;

/**
 * The {@link ColorComponent} class represents a component which contains a {@link ColorData}.
 *
 * @param colorData the color data which is used to render the component.
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.8.0
 */
public record ColorComponent(ColorData colorData) implements ObjectComponent {
}
