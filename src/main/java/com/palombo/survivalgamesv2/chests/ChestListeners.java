package me.palombo.survivalgamesv2.chests;

import me.palombo.gamelib.GameLib;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * @author palombo
 * @since 3/15/17
 */

public class ChestListeners implements Listener {

    @EventHandler
    public void onInteractWithBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();

            if (TeamHandler.get().getTeam(player).getName().equalsIgnoreCase("Spectators")) {
                event.setCancelled(true);
                return;
            }

            if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                if (block.hasMetadata("opened")) {
                    // If the block has already been opened, check if it should be refilled...

                    int currentRefillID = block.getMetadata("refill").get(0).asInt();

                    if (currentRefillID != SG.get().getChestManager().getRefillRound()) {
                        // If the block's refill round is not aligned with the games updated round, then fill.

                        handleChestFilling(block, block.getType() == Material.TRAPPED_CHEST, false);
                    }
                } else {
                    // If the block has not been opened, then fill it...

                    handleChestFilling(block, block.getType() == Material.TRAPPED_CHEST, true);
                }
            }

        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();

        int x = chunk.getX() << 4;
        int z = chunk.getZ() << 4;

        World world = chunk.getWorld();

        new BukkitRunnable() {
            @Override
            public void run() {
                for(int xx = x; xx < x + 16; xx++) {
                    for(int zz = z; zz < z + 16; zz++) {
                        for(int yy = 0; yy < 256; yy++) {
                            Block block = world.getBlockAt(xx, yy, zz);

                            if (!GameLib.getGame().getCurrentState().getIdentifier().equalsIgnoreCase("Lobby") && block.getType() == Material.ENDER_CHEST)
                                block.setType(Material.TRAPPED_CHEST);
                        }
                    }
                }
            }
        }.runTaskAsynchronously(SG.get());
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        Chunk chunk = event.getChunk();

        Arrays.stream(chunk.getEntities())
                .filter(entity -> entity.getType() == EntityType.DROPPED_ITEM)
                .forEach(Entity::remove);
    }

    private void handleChestFilling(Block block, boolean tier2, boolean newChest) {
        Chest chest = (Chest) block.getState();
        Inventory chestInventory = chest.getInventory();

        SG.get().getChestManager().fillChest(chestInventory, tier2);

        if (newChest) {
            block.setMetadata("opened", new FixedMetadataValue(SG.get(), true));
            block.setMetadata("refill", new FixedMetadataValue(SG.get(), 0));
        } else {
            int refillRound = block.getMetadata("refill").get(0).asInt();

            block.removeMetadata("refill", SG.get());
            block.setMetadata("refill", new FixedMetadataValue(SG.get(), refillRound + 1));
        }
    }

}
