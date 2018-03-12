package me.palombo.survivalgamesv2.teams;

import me.palombo.gamelib.GameLib;
import me.palombo.gamelib.game.components.GameLoadout;
import me.palombo.gamelib.game.components.GameTeam;
import me.palombo.survivalgamesv2.states.LobbyState;
import me.palombo.survivalgamesv2.states.PreGameState;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.UUID;

/**
 @author palombo
 * @since 3/13/17
 */

public class TributeTeam implements GameTeam {

    private List<UUID> members = Lists.newArrayList();

    @Override
    public String getName() {
        return "Tributes";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public GameLoadout getLoadout() {
        return null;
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }

    @Override
    public boolean canJoinTeam(UUID uuid) {
        if ((GameLib.getGame().getCurrentState().equals(new LobbyState()) || GameLib.getGame().getCurrentState().equals(new PreGameState())) && getMembers().size() <= 24)
            return true;
        else
            return false;
    }

}
