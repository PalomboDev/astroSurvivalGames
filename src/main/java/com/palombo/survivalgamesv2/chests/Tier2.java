package me.palombo.survivalgamesv2.chests;

import org.bukkit.Material;

/**
 @author palombo
 * @since 3/15/17
 */

public enum Tier2 {

    STONE_SWORD(1, Material.STONE_SWORD, 1),
    IRON_HELMET(1, Material.IRON_HELMET, 1),
    IRON_CHESTPLATE(1, Material.IRON_CHESTPLATE, 1),
    IRON_PANTS(1, Material.IRON_LEGGINGS, 1),
    IRON_BOOTS(1, Material.IRON_BOOTS, 1),
    GOLD_HELMET(1, Material.GOLD_HELMET, 1),
    GOLD_CHESTPLATE(1, Material.GOLD_CHESTPLATE, 1),
    GOLD_PANTS(1, Material.GOLD_CHESTPLATE, 1),
    GOLD_BOOTS(1, Material.GOLD_BOOTS, 1),
    CHAIN_HELMET(1, Material.CHAINMAIL_HELMET, 1),
    CHAIN_CHESTPLATE(1, Material.CHAINMAIL_CHESTPLATE, 1),
    CHAIN_PANTS(1, Material.CHAINMAIL_LEGGINGS, 1),
    CHAIN_BOOTS(1, Material.CHAINMAIL_BOOTS, 1),
    BOW(1, Material.BOW, 1),
    ARROWS(5, Material.ARROW, 1),
    GOLDEN_APPLE(1, Material.GOLDEN_APPLE, 0),
    GOLDEN_CARROT(1, Material.GOLDEN_CARROT, 1),
    COOKED_PORKCHOP(1, Material.GRILLED_PORK, 1),
    STEAK(1, Material.COOKED_BEEF, 1),
    FISHING_ROD(1, Material.FISHING_ROD, 1),
    IRON_INGOT(2, Material.IRON_INGOT, 1),
    DIAMOND(1, Material.DIAMOND, 1),
    EXP_BOTTLE(1, Material.EXP_BOTTLE, 1),
    BOAT(1, Material.BOAT, 1),
    ;

    private int quantity;
    private Material material;
    private int dataValue;

    Tier2(int quantity, Material material, int dataValue) {
        this.quantity = quantity;
        this.material = material;
        this.dataValue = dataValue;
    }

    public int getQuantity() {
        return quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public int getDataValue() {
        return dataValue;
    }

}
