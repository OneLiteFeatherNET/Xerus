package net.theevilreaper.xerus.api;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.Color;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mapping of visual color variants using text color, dye color, block material, and a string identifier.
 * Each constant aligns with a specific base color and can be used to harmonize visual elements across different systems.
 *
 * @author theEvilReaper
 * @version 1.5.0
 * @since 1.0.0
 */
@SuppressWarnings("java:S3252")
public enum ColorData {

    /** Aligns with the aqua color. */
    AQUA(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE, Material.LIGHT_BLUE_WOOL, "colorAqua"),

    /** Aligns with the dark aqua color. */
    DARK_AQUA(NamedTextColor.DARK_AQUA, DyeColor.CYAN, Material.CYAN_WOOL, "colorCyan"),

    /** Aligns with the black color. */
    BLACK(NamedTextColor.BLACK, DyeColor.BLACK, Material.BLACK_WOOL, "colorBlack"),

    /** Aligns with the blue color. */
    BLUE(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE, Material.BLUE_WOOL, "colorBlue"),

    /** Aligns with the dark blue color. */
    DARK_BLUE(NamedTextColor.DARK_BLUE, DyeColor.BLUE, Material.BLUE_WOOL, "colorDarkBlue"),

    /** Aligns with the brown/gold color. */
    GOLD(NamedTextColor.GOLD, DyeColor.ORANGE, Material.ORANGE_WOOL, "colorOrange"),

    /** Aligns with the gray color. */
    GRAY(NamedTextColor.GRAY, DyeColor.GRAY, Material.LIGHT_GRAY_WOOL, "colorLightGray"),

    /** Aligns with the dark gray color. */
    DARK_GREY(NamedTextColor.DARK_GRAY, DyeColor.GRAY, Material.GRAY_WOOL, "colorDarkGray"),

    /** Aligns with the green color. */
    GREEN(NamedTextColor.DARK_GREEN, DyeColor.GREEN, Material.GREEN_WOOL, "colorGreen"),

    /** Aligns with the light green color. */
    LIGHT_GREEN(NamedTextColor.GREEN, DyeColor.LIME, Material.LIME_WOOL, "colorLime"),

    /** Aligns with the purple color. */
    PURPLE(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE, Material.PURPLE_WOOL, "colorPurple"),

    /** Aligns with the light purple color. */
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA, Material.MAGENTA_WOOL, "colorMagenta"),

    /** Aligns with the red color. */
    RED(NamedTextColor.RED, DyeColor.RED, Material.RED_WOOL, "colorRed"),

    /** Aligns with the dark red color. */
    DARK_RED(NamedTextColor.DARK_RED, DyeColor.RED, Material.RED_WOOL, "colorDarkRed"),

    /** Aligns with the yellow color. */
    YELLOW(NamedTextColor.YELLOW, DyeColor.YELLOW, Material.YELLOW_WOOL, "colorYellow"),

    /** Aligns with the white color. */
    WHITE(NamedTextColor.WHITE, DyeColor.WHITE, Material.WHITE_WOOL, "colorWhite");

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
     * Please use this method because it reduces defensive copies from the array,
     * because values() returns each call a new array!
     *
     * @return an array with the color data values
     */
    public static @NotNull ColorData[] getValues() {
        return VALUES;
    }
}
