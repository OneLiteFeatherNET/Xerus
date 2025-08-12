package net.theevilreaper.xerus.api.component.team;

import net.theevilreaper.xerus.api.ColorData;
import net.theevilreaper.xerus.api.component.ObjectComponent;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link ColorComponent} class represents a component which contains a {@link ColorData}.
 *
 * @param colorData the color data which is used to render the component.
 */
public record ColorComponent(@NotNull ColorData colorData) implements ObjectComponent {
}
