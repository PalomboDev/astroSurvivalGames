package me.palombo.survivalgamesv2.votes;

import me.palombo.gamelib.game.components.arena.ArenaType;
import me.palombo.gamelib.game.components.arena.GameArena;
import me.palombo.gamelib.game.components.handlers.ArenaHandler;
import me.palombo.survivalgamesv2.map.MapManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 @author palombo
 * @since 3/17/17
 */

public class VoteManager {

    public VoteManager(MapManager mapManager) {
        mapManager.selectRandomMaps(5);

        ArenaHandler.get().getCollection().stream()
                .filter(arena -> arena.getType() == ArenaType.GAME_MAP)
                .forEach(arena -> votes.put(arena, 0));

        // Hopefully this should order the maps to be paired with an ID for a player to enter when voting.
        List<GameArena> keys = new ArrayList<>(votes.keySet());
        for (int i = 0; i < keys.size(); i++) {
            GameArena gameArena = keys.get(i);
            idPairedMaps.put(i, gameArena);
        }
    }

    private boolean votingEnabled;
    private Set<UUID> voted;
    private Map<GameArena, Integer> votes;
    private Map<Integer, GameArena> idPairedMaps = new HashMap<>();

    public boolean isVotingEnabled() {
        return votingEnabled;
    }

    public void setVotingEnabled(boolean votingEnabled) {
        this.votingEnabled = votingEnabled;
    }

    public Set<UUID> getVoted() {
        return voted;
    }

    public Map<GameArena, Integer> getVotes() {
        return votes;
    }

    public Set<GameArena> getLineup() {
        return votes.keySet();
    }

    public TreeMap<GameArena, Integer> getOrderedMaps() {
        VoteComparator comparator = new VoteComparator(votes);

        TreeMap<GameArena, Integer> orderedMaps = new TreeMap<>(comparator);
        orderedMaps.putAll(votes);

        return orderedMaps;
    }

    public Map<Integer, GameArena> getIdPairedMaps() {
        return idPairedMaps;
    }

    public GameArena getWinningMap() {
        return new ArrayList<>(getOrderedMaps().keySet()).get(0);
    }

    // Utility methods

    public void forceVote(GameArena arena, int quantity) {
        votes.put(arena, votes.get(arena) + quantity);
    }

    public void registerVote(Player player, GameArena gameArena) {
        if (!isVotingEnabled()) {
            player.sendMessage(ChatColor.RED + "Voting is not currently enabled.");
            return;
        }

        if (voted.contains(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You have already voted!");
            return;
        }

        forceVote(gameArena, 1);
        voted.add(player.getUniqueId());

        player.sendMessage(ChatColor.GREEN + "Your vote has been successfully registered!");
    }

    class VoteComparator implements Comparator<GameArena> {

        private Map<GameArena, Integer> input;

        public VoteComparator(Map<GameArena, Integer> input) {
            this.input = input;
        }

        @Override
        public int compare(GameArena a, GameArena b) {
            return input.get(a) - input.get(b);
        }
    }

}
