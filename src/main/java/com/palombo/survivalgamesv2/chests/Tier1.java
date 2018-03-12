package me.palombo.survivalgamesv2.chests;

import org.bukkit.Material;

/**
 @author palombo
 * @since 3/15/17
 */

public enum Tier1 {

    LEATHER_HELMET(1, Material.LEATHER_HELMET, 1),
    LEATHER_CHESTPLATE(1, Material.LEATHER_CHESTPLATE, 1),
    LEATHER_PANTS(1, Material.LEATHER_LEGGINGS, 1),
    LEATHER_BOOTS(1, Material.LEATHER_BOOTS, 1),
    FISHING_ROD(1, Material.FISHING_ROD, 1),
    WOODEN_SWORD(1, Material.WOOD_SWORD, 1),
    WOODEN_AXE(1, Material.WOOD_AXE, 1),
    BOW(1, Material.BOW, 1),
    ARROW(4, Material.ARROW, 1),
    RAW_PORKCHOP(1, Material.PORK, 1),
    RAW_BEEF(1, Material.RAW_BEEF, 1),
    FEATHERS(5, Material.FEATHER, 1),
    FLINT(1, Material.FLINT, 1),
    STICK(1, Material.STICK, 1),
    STONE_AXE(1, Material.STONE_AXE, 1),
    STONE_SWORD(1, Material.STONE_SWORD, 1),
    APPLE(1, Material.APPLE, 1),
    CARROT(1, Material.CARROT, 1),
    GOLDEN_INGOT(1, Material.GOLD_INGOT, 1),
    IRON_INGOT(1, Material.IRON_INGOT, 1),
    BREAD(1, Material.BREAD, 1),
    MUSHROOM_STEW(1, Material.MUSHROOM_SOUP, 1),
    RAW_FISH(1, Material.RAW_FISH, 1),
    COOKIE(2, Material.COOKIE, 1),
    BAKED_POTATO(1, Material.BAKED_POTATO, 1)
    ;

    private int quantity;
    private Material material;
    private int dataValue;

    Tier1(int quantity, Material material, int dataValue) {
        this.quantity = quantity;
        this.material = material;
        this.dataValue = dataValue;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material getMaterial() { return material; }

    public int getDataValue() {
        return dataValue;
    }

}
