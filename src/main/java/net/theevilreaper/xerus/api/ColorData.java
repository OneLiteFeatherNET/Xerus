package net.theevilreaper.xerus.api;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.Color;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

/**
 * The class combines all colors of DyeColor, ChatColor and the wool blocks into enum values.
 * So for each color you have all the matching values.
 *
 * @author theEvilReaper
 * @version 1.5.0
 * @since 1.0.0
 */
@SuppressWarnings("java:S3252")
public enum ColorData {

    AQUA(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_WOOL, "colorAqua"),
    DARK_AQUA(NamedTextColor.DARK_AQUA, DyeColor.CYAN, Material.CYAN_WOOL, "colorCyan"),
    BLACK(NamedTextColor.BLACK, DyeColor.BLACK, Material.BLACK_WOOL, "colorBlack"),
    BLUE(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE, Material.BLUE_WOOL, "colorBlue"),
    DARK_BLUE(NamedTextColor.DARK_BLUE, DyeColor.BLUE, Material.BLUE_WOOL, "colorDarkBlue"),
    GOLD(NamedTextColor.GOLD, DyeColor.ORANGE, Material.ORANGE_WOOL, "colorOrange"),
    GRAY(NamedTextColor.GRAY, DyeColor.GRAY, Material.LIGHT_GRAY_WOOL, "colorLightGray"),
    DARK_GREY(NamedTextColor.DARK_GRAY, DyeColor.GRAY, Material.GRAY_WOOL, "colorDarkGray"),
    GREEN(NamedTextColor.DARK_GREEN, DyeColor.GREEN, Material.GREEN_WOOL, "colorGreen"),
    LIGHT_GREEN(NamedTextColor.GREEN, DyeColor.LIME, Material.LIME_WOOL, "colorLime"),
    PURPLE(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE, Material.PURPLE_WOOL, "colorPurple"),
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA, Material.MAGENTA_WOOL, "colorMagenta"),
    RED(NamedTextColor.RED, DyeColor.RED, Material.RED_WOOL, "colorRed"),
    DARK_RED(NamedTextColor.DARK_RED, DyeColor.RED, Material.RED_WOOL, "colorDarkRed"),
    YELLOW(NamedTextColor.YELLOW, DyeColor.YELLOW, Material.YELLOW_WOOL, "colorYellow"),
    WHITE(NamedTextColor.WHITE, DyeColor.WHITE, Material.WHITE_WOOL, "colorWhite")

    ;

    //Reduce defensive copies from the array, because values() returns each call a new array!
    private static final ColorData[] VALUES = values();

    private final NamedTextColor chatColor;
    private final DyeColor dyeColor;
    private final Material material;
    private final String translateKey;

    /**
     * Creates a new instance for an enum entry.
     *
     * @param chatColor    the chat color for the wool
     * @param dyeColor     the dye color for the wool
     * @param material     the material for the wool
     * @param translateKey the key for the translation
     */
    ColorData(
            @NotNull NamedTextColor chatColor,
            @NotNull DyeColor dyeColor,
            @NotNull Material material,
            @NotNull String translateKey
    ) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
        this.material = material;
        this.translateKey = translateKey;
    }

    /**
     * Returns the {@link DyeColor} from an enum entry.
     *
     * @return the underlying dyeColor
     */
    public @NotNull DyeColor getDyeColor() {
        return dyeColor;
    }

    /**
     * Returns the {@link Material} from an enum entry.
     *
     * @return the underlying material
     */
    public @NotNull Material getMaterial() {
        return material;
    }

    /**
     * Returns the {@link NamedTextColor} from an enum entry.
     *
     * @return the underlying chatColor
     */
    public @NotNull NamedTextColor getChatColor() {
        return chatColor;
    }

    /**
     * Returns the key for the translation.
     *
     * @return the underlying translation key
     */
    public @NotNull String getTranslateKey() {
        return translateKey;
    }

    /**
     * Returns the {@link Color} from an enum entry.
     *
     * @return the underlying color
     */
    public @NotNull Color getColor() {
        return this.dyeColor.color();
    }

    /**
     * Returns an array which contains all given color data values.
     * Please use this method because its reduce defensive copies from the array,
     * because values() returns each call a new array!
     *
     * @return an array with the color data values
     */
    public static @NotNull ColorData[] getValues() {
        return VALUES;
    }
}
