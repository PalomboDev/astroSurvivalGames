package me.palombo.survivalgamesv2.commands;

import me.palombo.gamelib.game.components.arena.GameArena;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vote") && commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            if (args.length == 0) {
                // Show maps and what votes they have.
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Maps Lineup:");
                for (Integer id : SG.get().getVoteManager().getIdPairedMaps().keySet()) {
                    GameArena arena = SG.get().getVoteManager().getIdPairedMaps().get(id);
                    sender.sendMessage(ChatColor.GREEN + arena.getMapName()
                            + " - " + SG.get().getVoteManager().getVotes().get(arena) + " votes.");
                }
                return true;
            }

            if (SG.get().getVoteManager().isVotingEnabled()) {
                if (SG.get().getVoteManager().getVoted().contains(sender.getUniqueId())) {
                    sender.sendMessage(ChatColor.RED + "You have already voted!");
                    return true;
                }

                try {
                    GameArena gameArena = SG.get().getVoteManager().getIdPairedMaps().get(Integer.parseInt(args[0]));
                    SG.get().getVoteManager().registerVote(sender, gameArena);
                    sender.sendMessage(ChatColor.GREEN + "You have successfully voted for " + gameArena.getMapName() + " by "
                            + gameArena.getAuthor() + ". It now has " + SG.get().getVoteManager().getVotes().get(gameArena) + " votes!");
                } catch (NumberFormatException ex) {
                    sender.sendMessage(ChatColor.RED + "Please enter a valid map ID between 1 and 5.");
                }

            } else {
                sender.sendMessage(ChatColor.RED + "Voting is not currently enabled!");
                return true;
            }
        }
        return true;
    }
}