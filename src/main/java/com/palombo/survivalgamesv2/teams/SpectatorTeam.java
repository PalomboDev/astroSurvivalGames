package me.palombo.survivalgamesv2.teams;

import me.palombo.gamelib.game.components.GameLoadout;
import me.palombo.gamelib.game.components.GameTeam;
import me.palombo.gamelib.game.components.handlers.TeamHandler;
import me.palombo.gamelib.util.ItemStackBuilder;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 @author palombo
 * @since 3/13/17
 */

public class SpectatorTeam implements GameTeam {

    private List<UUID> members = Lists.newArrayList();

    @Override
    public String getName() {
        return "Spectators";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.RED;
    }

    @Override
    public GameLoadout getLoadout() {
        return new GameLoadout() {
            @Override
            public String getName() {
                return "Spectator Items";
            }

            @Override
            public String[] getDescription() {
                return new String[]{ "The default spectator items" };
            }

            @Override
            public List<ItemStack> getItems() {
                return Stream.of(
                        new ItemStackBuilder(Material.COMPASS).withName("&c&lSpectate Players &7(Right Click)").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.STAINED_GLASS_PANE).withDurability(DyeColor.BLACK.getData()).withName("&8 ").build(),
                        new ItemStackBuilder(Material.DARK_OAK_DOOR).withName("&e&lReturn to Hub").withLore("&7Leaving so soon? :^(").build()
                ).collect(Collectors.toList());
            }

            @Override
            public ItemStack getHelmet() {
                return null;
            }

            @Override
            public ItemStack getChestplate() {
                return null;
            }

            @Override
            public ItemStack getLeggings() {
                return null;
            }

            @Override
            public ItemStack getBoots() {
                return null;
            }

            @Override
            public boolean isPaid() {
                return false;
            }

            @Override
            public int getPrice() {
                return 0;
            }
        };
    }

    @Override
    public List<UUID> getMembers() {
        return members;
    }

    @Override
    public boolean canJoinTeam(UUID uuid) {
        return !TeamHandler.get().getTeam(uuid).equals(new TributeTeam());
    }

}
