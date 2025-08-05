package net.theevilreaper.xerus.api.kit;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minestom.server.entity.Player;
import net.theevilreaper.aves.item.IItem;
import net.theevilreaper.aves.util.Players;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

/**
 * The {@link KitImpl} class represents a default implementation of the {@link Kit} interface.
 *
 * @author theEvilReaper
 * @version 1.1.0
 * @since 1.2.0
 **/
public class KitImpl extends BaseKit {

    private final Component name;
    private final Component description;

    /**
     * Creates a new instance from the kit.
     * @param name the name of the kit
     * @param description the description of the kit
     * @param armorItems whether the kit should contain armor items
     */
    public KitImpl(@NotNull Component name, @Nullable Component description, boolean armorItems) {
        super(armorItems);
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void setEquipment(@NotNull Player player, Locale locale, int... shiftedSlots) {
        Players.updateArmorItems(player, armorItems.values().toArray(IItem[]::new), locale);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitImpl kit = (KitImpl) o;
        return Objects.equals(name, kit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Component getName(@Nullable Locale ignored) {
        return name;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Component getDescription(@Nullable Locale ignored) {
        return description;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public String getIdentifier() {
        return PlainTextComponentSerializer.plainText().serialize(getName(null));
    }
}