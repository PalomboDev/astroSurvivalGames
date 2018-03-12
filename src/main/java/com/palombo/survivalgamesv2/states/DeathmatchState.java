package me.palombo.survivalgamesv2.states;

import me.palombo.gamelib.game.components.GameState;
import me.palombo.gamelib.game.components.handlers.RegionHandler;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.gamelib.game.components.regions.GameRegion;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

/**
 @author palombo
 * @since 3/14/17
 */

public class DeathmatchState implements GameState {

    Thread blockRemovalThread;
    GameRegion deathmatchWall;

    @Override
    public String getIdentifier() {
        return "Deathmatch";
    }

    @Override
    public void onStateEntered() {
        deathmatchWall = RegionHandler.get().getRegion("deathmatchWall");

        if (deathmatchWall == null) {
            SG.broadcast("&cERROR! There was an error getting the &6deathmatchWall &cregion information.");
            SG.broadcast("&cPlease contact a &3DEVELOPER (@codenameflip/@Palombo) &cand let them know the following:");
            SG.broadcast("&7'The deathmatchWall GameRegion was not registered properly from the map file. (on the " + SG.get().getMapArena().getMapName() + " map)'");
        } else {
            new BukkitRunnable() {
                int[] prettyColors = { 14, 1, 4, 5, 3, 9, 11, 2 };
                int iterations = 0;
                final int MAX_ITERATIONS = 5;

                @Override
                public void run() {
                    if (iterations == MAX_ITERATIONS) {
                        this.cancel();

                        deathmatchWall.getBlocksInRegion().forEach(block -> {
                            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
                        });
                    }

                    colorWall(deathmatchWall, Material.STAINED_GLASS, prettyColors[ThreadLocalRandom.current().nextInt(prettyColors.length - 1)]);

                    iterations++;
                }
            }.runTaskTimerAsynchronously(SG.get(), 0, 20 * 2);


            blockRemovalThread = new Thread(() -> TeamHandler.get().getTeam("Tributes").getMembers().forEach(uuid -> {
                Player player = Bukkit.getPlayer(uuid);

                surroundPlayer(player, Material.AIR);
            }));

            final int[] countdown = {6};
                new BukkitRunnable() {
                    public void run() {
                        countdown[0]--;

                        SG.broadcast("§c§l" + countdown[0] + "...");

                        if (countdown[0] == 0) {
                            blockRemovalThread.interrupt();

                            blockRemovalThread.start();
                        }
                    }

                }.runTaskTimer(SG.get(), 20L, 20L * 7);

        }
    }

    @Override
    public void onStateRunning() {
        blockRemovalThread.interrupt();

        if (TeamHandler.get().getTeam("Tributes").getMembers().size() == 2) {
            for (Block block : deathmatchWall.getBlocksInRegion()) block.setType(Material.AIR);

            SG.broadcast("§c§lWall Dropped!");
        }

        if (TeamHandler.get().getTeam("Tributes").getMembers().size() == 1) {
            SG.broadcast("&d" + Bukkit.getPlayer(TeamHandler.get().getTeam("Tributes").getMembers().get(0)).getName() + "&ehas won the round! &&&lGG!");
            SG.broadcast("&7(Server rebooting in &c&l15 seconds...&7)");

            new BukkitRunnable(){
                @Override
                public void run() {
                    Bukkit.shutdown();
                }
            }.runTaskLater(SG.get(), 20 * 15);
        }
    }

    @Override
    public void onStateExited() {

    }

    @Override
    public int getUpdateRate() {
        return 0;
    }

    /**
     * Sets the wall's glass color
     * @param material The material you would like to set the wall to
     * @param data The block data value you would like the block
     */
    private void colorWall(GameRegion region, Material material, int data) {
        for (Block block : region.getBlocksInRegion()) {
            block.setType(material);
            block.setData((byte) data);

            block.getLocation().getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        }
    }

    private void surroundPlayer(Player player, Material type) {
        // The four blocks around the player's feet
        player.getLocation().getBlock().getRelative(BlockFace.NORTH).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.EAST).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.SOUTH).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.WEST).setType(type);

        // The four blocks around the player's head
        player.getLocation().getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.UP).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).setType(type);
        player.getLocation().getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.UP).setType(type);
    }

}
