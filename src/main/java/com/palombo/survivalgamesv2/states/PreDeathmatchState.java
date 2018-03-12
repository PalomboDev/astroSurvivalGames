package me.palombo.survivalgamesv2.states;

import me.palombo.gamelib.game.components.GameState;
import me.palombo.gamelib.game.components.handlers.StateHandler;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.survivalgamesv2.SG;
import me.palombo.survivalgamesv2.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 @author palombo
 * @since 3/14/17
 */

public class PreDeathmatchState implements GameState {

    @Override
    public String getIdentifier() {
        return "Pre-Deathmatch";
    }

    private boolean dmPos1Occupied;
    private boolean dmPos2Occupied;
    private boolean dmPos3Occupied;
    private boolean dmPos4Occupied;

    private Thread blockPlacingThread;

    @Override
    public void onStateEntered() {
        dmPos1Occupied = false;
        dmPos2Occupied = false;
        dmPos3Occupied = false;
        dmPos4Occupied = false;

        Location dmPos1 = MapManager.getDMPos1();
        Location dmPos2 = MapManager.getDMPos2();
        Location dmPos3 = MapManager.getDMPos3();
        Location dmPos4 = MapManager.getDMPos4();

        for (UUID players : TeamHandler.get().getTeam("Tributes").getMembers()) {
            Player player = Bukkit.getPlayer(players);
            if (dmPos1Occupied == false) {
                player.teleport(dmPos1);
                dmPos1Occupied = true;
                return;
            }

            if (dmPos2Occupied == false) {
                player.teleport(dmPos2);
                dmPos2Occupied = true;
                return;
            }

            if (dmPos3Occupied == false) {
                player.teleport(dmPos3);
                dmPos3Occupied = true;
                return;
            }

            if (dmPos4Occupied == false) {
                player.teleport(dmPos4);
                dmPos4Occupied = true;
                return;
            }
        }

        blockPlacingThread = new Thread(() -> TeamHandler.get().getTeam("Tributes").getMembers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            surroundPlayer(player, Material.STAINED_GLASS);
        }));

        blockPlacingThread.start();

        StateHandler.get().setState(SG.get(), "Deathmatch");
    }

    @Override
    public void onStateRunning() {

    }

    @Override
    public void onStateExited() {

    }

    @Override
    public int getUpdateRate() {
        return 0;
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
