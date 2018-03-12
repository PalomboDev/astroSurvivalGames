package me.palombo.survivalgamesv2.states;

import me.palombo.gamelib.game.components.GameState;
import me.palombo.gamelib.game.components.handlers.StateHandler;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 @author palombo
 * @since 3/13/17
 */

public class PreGameState implements GameState {

    private BukkitTask run1 = null;

    @Override
    public String getIdentifier() {
        return "Pre-Game";
    }

    @Override
    public void onStateEntered() {
        // Teleport all players to random arena locations

        Thread blockPlacingThread = new Thread(() -> TeamHandler.get().getTeam("Tributes").getMembers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            surroundPlayer(player, Material.STAINED_GLASS);
        }));

        for (Player player : Bukkit.getOnlinePlayers()) {
            TeamHandler.get().setTeam(player, TeamHandler.get().getTeam("Tributes"));

            //player.teleport(SG.get().getMapArena().getRandomSpawnLocation());
            player.teleport(SG.get().getMapArena().getPos1());
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 0, true, false));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                blockPlacingThread.start();
            }
        }.runTaskLater(SG.get(), 20L);

        if (run1 == null) {
            run1 = new BukkitRunnable() {
                int count = 16;

                public void run() {
                    count--;

                    if (count > 0) {
                        SG.broadcast("The game will begin in &a&l" + count + " &aseconds");
                    }

                    if (count == 0) {
                        run1.cancel();

                        StateHandler.get().setState(SG.get(), "Ingame");

                        SG.broadcast("&6&lTHE GAME HAS BEGUN! &aMay the odds ever be in your favor...");

                        Thread blockRemovalThread = new Thread(() -> TeamHandler.get().getTeam("Tributes").getMembers().forEach(uuid -> {
                            Player player = Bukkit.getPlayer(uuid);

                            surroundPlayer(player, Material.AIR);
                        }));

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                        blockRemovalThread.start();

                        TeamHandler.get().getTeam("Tributes").getMembers().forEach(player -> Bukkit.getPlayer(player).getLocation().getWorld().strikeLightningEffect(Bukkit.getPlayer(player).getLocation().add(0, 2, 0)));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                blockPlacingThread.interrupt();
                                blockRemovalThread.interrupt();
                            }
                        }.runTaskLater(SG.get(), 20 * 30);
                    }
                }
            }.runTaskTimer(SG.get(), 20L, 20L);
        }
    }

    @Override
    public void onStateRunning() { }

    @Override
    public void onStateExited() { }

    @Override
    public int getUpdateRate() {
        return 20 * 30;
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
