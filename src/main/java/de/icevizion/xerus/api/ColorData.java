package de.icevizion.xerus.api;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.Color;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

/**
 * The class combines all colors of DyeColor, ChatColor and the wool blocks into enum values.
 * So for each color you have all the matching values.
 * @author theEvilReaper
 * @version 1.0.1
 * @since 1.0.0
 */
@SuppressWarnings("java:S3252")
public enum ColorData {

    AQUA(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE,
            ItemStack.builder(Material.LIGHT_BLUE_WOOL).build(), "colorAqua", DyeColor.LIGHT_BLUE.color()),
    DARK_AQUA(NamedTextColor.DARK_AQUA, DyeColor.CYAN,
            ItemStack.builder(Material.CYAN_WOOL).build(), "colorCyan", DyeColor.CYAN.color()),
    BLACK(NamedTextColor.BLACK, DyeColor.BLACK,
            ItemStack.builder(Material.BLACK_WOOL).build(), "colorBlack", DyeColor.BLACK.color()),
    BLUE(NamedTextColor.BLUE,  DyeColor.LIGHT_BLUE,
            ItemStack.builder(Material.BLUE_WOOL).build(), "colorBlue", DyeColor.BLUE.color()),
    DARK_BLUE(NamedTextColor.DARK_BLUE,  DyeColor.BLUE,
            ItemStack.builder(Material.BLUE_WOOL).build(), "colorDarkBlue", DyeColor.BLUE.color()),
    GOLD(NamedTextColor.GOLD, DyeColor.ORANGE,
            ItemStack.builder(Material.ORANGE_WOOL).build(), "colorOrange", DyeColor.ORANGE.color()),
    GRAY(NamedTextColor.GRAY, DyeColor.GRAY,
            ItemStack.builder(Material.LIGHT_GRAY_WOOL).build(), "colorLightGray", DyeColor.GRAY.color()),
    DARK_GREY(NamedTextColor.DARK_GRAY, DyeColor.GRAY,
            ItemStack.builder(Material.GRAY_WOOL).build(), "colorDarkGray", DyeColor.GRAY.color()),
    GREEN(NamedTextColor.DARK_GREEN, DyeColor.GREEN,
            ItemStack.builder(Material.GREEN_WOOL).build() , "colorGreen", DyeColor.GREEN.color()),
    LIGHT_GREEN(NamedTextColor.GREEN, DyeColor.LIME,
            ItemStack.builder(Material.LIME_WOOL).build(), "colorLime", DyeColor.LIME.color()),
    PURPLE(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE,
            ItemStack.builder(Material.PURPLE_WOOL).build(), "colorPurple", DyeColor.PURPLE.color()),
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA,
            ItemStack.builder(Material.MAGENTA_WOOL).build(), "colorMagenta", DyeColor.MAGENTA.color()),
    RED(NamedTextColor.RED, DyeColor.RED,
            ItemStack.builder(Material.RED_WOOL).build(), "colorRed", DyeColor.RED.color()),
    DARK_RED(NamedTextColor.DARK_RED, DyeColor.RED,
            ItemStack.builder(Material.RED_WOOL).build(), "colorDarkRed", DyeColor.RED.color()),
    YELLOW(NamedTextColor.YELLOW, DyeColor.YELLOW,
            ItemStack.builder(Material.YELLOW_WOOL).build(), "colorYellow", DyeColor.YELLOW.color()),
    WHITE(NamedTextColor.WHITE, DyeColor.WHITE,
            ItemStack.builder(Material.WHITE_WOOL).build(), "colorWhite", DyeColor.WHITE.color());

    //Reduce defensive copies from the array, because values() returns each call a new array!
    private static final ColorData[] VALUES = values();

    private final NamedTextColor chatColor;
    private final DyeColor dyeColor;
    private final ItemStack stack;
    private final Color color;
    private final String translateKey;

    ColorData(NamedTextColor chatColor, DyeColor dyeColor, ItemStack stack, String translateKey, Color color) {
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
        this.stack = stack;
        this.translateKey = translateKey;
        this.color = color;
    }

    /**
     * Returns the {@link DyeColor} from an enum entry.
     * @return the underlying dyeColor
     */
    public DyeColor getDyeColor() {
        return dyeColor;
    }

    /**
     * Returns the {@link ItemStack} from an enum entry.
     * @return the underlying stack
     */
    public ItemStack getStack() {
        return stack;
    }

    /**
     * Returns the {@link NamedTextColor} from an enum entry.
     * @return the underlying chatColor
     */
    public NamedTextColor getChatColor() {
        return chatColor;
    }

    /**
     * Returns the key for the translation.
     * @return the underlying translation key
     */
    public String getTranslateKey() {
        return translateKey;
    }

    /**
     * Returns the {@link Color} from an enum entry.
     * @return the underlying color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns an array which contains all given color data values.
     * Please use this method because its reduce defensive copies from the array,
     * because values() returns each call a new array!
     * @return an array with the color data values
     */
    public static ColorData[] getValues() { return VALUES; }
}
