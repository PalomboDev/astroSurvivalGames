package me.palombo.survivalgamesv2.chests;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

/**
 @author palombo
 * @since 3/15/17
 */

public class ChestManager {

    public ChestManager() { }

    private final int MINIMUM_ITEMS = 4; // Minimum items per chest
    private final int MAXIMUM_ITEMS = 9; // Maximum items per chest

    private int refillRound = 0;

    public int getRefillRound() {
        return refillRound;
    }

    public void incrementRefillRound() {
        this.refillRound = refillRound + 1;
    }

    public void fillChest(Inventory inventory, boolean tier2) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        inventory.clear();

        for (int i = 0; i < getRandomWithinBounds(MINIMUM_ITEMS, MAXIMUM_ITEMS); i++) {
            int slot = random.nextInt(inventory.getSize() - 1);

            if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) {
                if (tier2) {
                    int itemIndex = random.nextInt(Tier2.values().length - 1);
                    Tier2 item = Tier2.values()[itemIndex];

                    inventory.setItem(slot, new ItemStack(item.getMaterial(), item.getQuantity()));
                } else {
                    int itemIndex = random.nextInt(Tier1.values().length - 1);
                    Tier1 item = Tier1.values()[itemIndex];

                    inventory.setItem(slot, new ItemStack(item.getMaterial(), item.getQuantity()));
                }
            }
        }
    }

    private int getRandomWithinBounds(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - min) + min;
    }

}
