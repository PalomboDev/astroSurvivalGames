package me.palombo.survivalgamesv2;

import me.palombo.gamelib.GameLib;
import me.palombo.gamelib.game.Game;
import me.palombo.gamelib.game.components.arena.ArenaType;
import me.palombo.gamelib.game.components.arena.GameArena;
import me.palombo.gamelib.game.components.handlers.ArenaHandler;
import me.palombo.gamelib.game.components.handlers.StateHandler;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.survivalgamesv2.chests.ChestManager;
import me.palombo.survivalgamesv2.commands.VoteCommand;
import me.palombo.survivalgamesv2.map.MapManager;
import me.palombo.survivalgamesv2.states.*;
import me.palombo.survivalgamesv2.teams.SpectatorTeam;
import me.palombo.survivalgamesv2.teams.TributeTeam;
import me.palombo.survivalgamesv2.votes.VoteManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class SG extends JavaPlugin {

    private static SG instance;
    private GameArena lobbyArena;
    private GameArena mapArena;

    private ChestManager chestManager;
    private MapManager mapManager;
    private VoteManager voteManager;

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 24;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        Game sg = new Game(
                "Survival Games",
                new String[] {
                        "Battle to the death in an area of 23 other players",
                        "Be the last one standing to win... May the odds ever be in your favor."
                },
                new ItemStack(Material.IRON_SWORD)
        );

        sg.setChatPrefix("§c§l[SG] §e");

        TeamHandler.get().registerTeams(
                new SpectatorTeam(),
                new TributeTeam()
        );

        StateHandler.get().registerStates(
                new LobbyState(),
                new PreGameState(),
                new InGameState(),
                new PreDeathmatchState(),
                new DeathmatchState()
        );

        {
            GameArena lobby = new GameArena(SG.get(), "lobby");
            lobby.setType(ArenaType.LOBBY_MAP);
            ArenaHandler.get().registerArenas(lobby);

            lobbyArena = lobby;
        }

        GameLib.setGame(sg);

        StateHandler.get().setState(SG.get(), "Lobby", true);

        this.chestManager = new ChestManager();
        this.mapManager = new MapManager();
        this.voteManager = new VoteManager(this.mapManager);

        sg.setRegionsEnabled(true);

        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Bukkit.broadcastMessage(player.getWorld().toString());
                }
            }
        }.runTaskTimer(this, 0L, 120L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        getCommand("vote").setExecutor(new VoteCommand());
    }

    public static SG get() {
        return instance;
    }

    public ChestManager getChestManager() {
        return chestManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public static void broadcast(String message) {
        Bukkit.broadcastMessage(GameLib.getGame().getChatPrefix() + ChatColor.translateAlternateColorCodes('&', message));
    }

    public GameArena getLobbyArena() {
        return lobbyArena;
    }

    public GameArena getMapArena() {
        return mapArena;
    }

    public void setMapArena(GameArena mapArena) {
        this.mapArena = mapArena;
    }
}
