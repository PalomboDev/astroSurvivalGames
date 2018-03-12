package me.palombo.survivalgamesv2.states;

import me.palombo.gamelib.game.components.GameState;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.Date;

/**
 @author palombo
 * @since 3/13/17
 */

public class InGameState implements GameState {

    private Date refill1;
    private Date refill2;

    @Override
    public String getIdentifier() {
        return "Ingame";
    }

    @Override
    public void onStateEntered() {
        refill1 = new Date();
        refill2 = new Date();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(refill1);
        calendar.add(Calendar.MINUTE, 13);
        refill1 = calendar.getTime();

        calendar.setTime(refill2);
        calendar.add(Calendar.MINUTE, 26);
        refill2 = calendar.getTime();
    }

    @Override
    public void onStateRunning() {
        if (isRefillTime()) {
            SG.get().getChestManager().incrementRefillRound();

            SG.broadcast("&e&lAll chests have been refilled!");
        }

        int tributeCount = TeamHandler.get().getTeam("Tributes").getMembers().size();

        if (tributeCount <= 1) {
            SG.broadcast("&d" + Bukkit.getPlayer(TeamHandler.get().getTeam("Tributes").getMembers().get(0)).getName() + "&ehas won the round! &&&lGG!");
            /*SG.broadcast("Deathmatch is imminent... (Starting in 30 seconds)");
            SG.broadcast("There are &c&l4 players &eremaining ");

            new BukkitRunnable(){
                @Override
                public void run() {
                    if (TeamHandler.get().getTeam("Tributes").getMembers().size() != 1)
                        StateHandler.get().setState(SG.get(), "Pre-Deathmatch");
                }
            }.runTaskLater(SG.get(), 20 * 30);*/
        }
    }

    @Override
    public void onStateExited() {

    }

    @Override
    public int getUpdateRate() {
        return 20;
    }

    public boolean isRefillTime() {
        Date date = new Date();

        return date.equals(refill1) || date.equals(refill2);
    }

}
