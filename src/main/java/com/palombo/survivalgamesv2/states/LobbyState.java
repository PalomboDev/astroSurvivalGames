package me.palombo.survivalgamesv2.states;

import me.palombo.gamelib.game.components.GameState;
import me.palombo.gamelib.game.components.arena.GameArena;
import me.palombo.gamelib.game.components.handlers.StateHandler;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 @author palombo
 * @since 3/13/17
 */

public class LobbyState implements GameState {

    private BukkitTask run1 = null;

    @Override
    public String getIdentifier() {
        return "Lobby";
    }

    @Override
    public void onStateEntered() {
        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(SG.get().getLobbyArena().getSpawns().get(0).getSpawn()));
        //SG.get().getVoteManager().setVotingEnabled(true);
    }

    @Override
    public void onStateRunning() {
        // Check the player count

        if (Bukkit.getOnlinePlayers().size() > 1) {

            if (run1 == null) {
                run1 = new BukkitRunnable() {
                    int count = 11;

                    public void run() {
                        count--;

                        if (count == 10) {
                            SG.broadcast("The minimum player count has been reached! The game will begin in &c10 &eseconds...");
                        }

                        if (count == 0) {
                            if (Bukkit.getOnlinePlayers().size() >= SG.MIN_PLAYERS) {
                                run1.cancel();

                                GameArena winningMap = new GameArena(SG.get(), "gamemap"); //SG.get().getVoteManager().getWinningMap();

                                SG.get().setMapArena(winningMap);

                                SG.broadcast(winningMap.getMapName() + " &a&lhas won the vote!");
                                SG.broadcast("Pregame will begin once map is loaded...");

                                new BukkitRunnable() {
                                    public void run() {
                                        StateHandler.get().setState(SG.get(), "Pre-Game");
                                    }
                                }.runTaskLater(SG.get(), 20L * 20);
                            } else {
                                SG.broadcast("&c&lNot enough players! &Game start failed due to people logging out...");
                                SG.broadcast("&6&oTimer restarting...");
                                count = 11;
                            }
                        }
                    }
                }.runTaskTimer(SG.get(), 20L, 20L);
            }
        }
    }

    @Override
    public void onStateExited() {
        //SG.get().getVoteManager().setVotingEnabled(false);
    }

    @Override
    public int getUpdateRate() {
        return 20;
    }

}
