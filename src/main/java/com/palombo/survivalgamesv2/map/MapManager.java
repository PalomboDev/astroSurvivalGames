package me.palombo.survivalgamesv2.map;

import me.palombo.gamelib.game.components.arena.ArenaType;
import me.palombo.gamelib.game.components.arena.GameArena;
import me.palombo.gamelib.game.components.handlers.ArenaHandler;
import me.palombo.survivalgamesv2.SG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



public class MapManager {

    public MapManager() { }

    public void loadMaps() {
        File mapsFolder = Bukkit.getWorldContainer().getAbsoluteFile();

        if (mapsFolder.isDirectory()) {
            for (String child : mapsFolder.list()) {
                // This is the name of each file contained within the folder
                if (child.equalsIgnoreCase("lobby"))
                    continue; // If the map is a lobby map, then that will be registered outside of the automatic map loading

                GameArena arena = new GameArena(SG.get(), child);
                arena.setType(ArenaType.GAME_MAP);

                ArenaHandler.get().registerArenas(arena);
            }
        }
    }

    public void selectRandomMaps(int quantity) {
        File mapsDirectory = new File(SG.get().getDataFolder(), "maps");

        if (mapsDirectory.isDirectory() && mapsDirectory.listFiles() != null) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            List<File> chosenMaps = new ArrayList<>();

            if (quantity > mapsDirectory.listFiles().length)
                throw new IndexOutOfBoundsException("Cannot load more maps than the ones that are in the maps folder.");

            if (mapsDirectory.listFiles().length == quantity) {
                Arrays.stream(mapsDirectory.listFiles()).forEach(file -> {
                    if (!file.getName().contains("lobby"))
                        chosenMaps.add(file);
                });

                registerArenaFromFile(chosenMaps);
                return;
            }

            for (int i = 0; i < quantity; i++) {
                File file = mapsDirectory.listFiles()[random.nextInt(mapsDirectory.listFiles().length - 1)];

                if (chosenMaps.contains(file) || file.getName().contains("lobby"))
                    i--;

                chosenMaps.add(file);
            }

            registerArenaFromFile(chosenMaps);
        }
    }

    private void registerArenaFromFile(List<File> arenas) {
        arenas.forEach(files -> {
            GameArena arena = new GameArena(SG.get(), files.getName());
            arena.setType(ArenaType.GAME_MAP);

            ArenaHandler.get().registerArenas(arena);
        });
    }

    public static Location getDMPos1() {
        File mapConfigFile = new File(Bukkit.getWorldContainer().getAbsolutePath().replace(".", "") + SG.get().getMapArena().getMapName() + "/map.yml");
        FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapConfigFile);

        Location DMPos1 = new Location(
                Bukkit.getWorld(mapConfig.getString("dm-podiums.1.world")),
                mapConfig.getDouble("dm-podiums.1.x"),
                mapConfig.getDouble("dm-podiums.1.y"),
                mapConfig.getDouble("dm-podiums.1.z")
        );

        return DMPos1;
    }

    public static Location getDMPos2() {
        File mapConfigFile = new File(Bukkit.getWorldContainer().getAbsolutePath().replace(".", "") + SG.get().getMapArena().getMapName() + "/map.yml");
        FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapConfigFile);

        Location DMPos2 = new Location(
                Bukkit.getWorld(mapConfig.getString("dm-podiums.2.world")),
                mapConfig.getDouble("dm-podiums.2.x"),
                mapConfig.getDouble("dm-podiums.2.y"),
                mapConfig.getDouble("dm-podiums.2.z")
        );

        return DMPos2;
    }

    public static Location getDMPos3() {
        File mapConfigFile = new File(Bukkit.getWorldContainer().getAbsolutePath().replace(".", "") + SG.get().getMapArena().getMapName() + "/map.yml");
        FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapConfigFile);

        Location DMPos3 = new Location(
                Bukkit.getWorld(mapConfig.getString("dm-podiums.3.world")),
                mapConfig.getDouble("dm-podiums.3.x"),
                mapConfig.getDouble("dm-podiums.3.y"),
                mapConfig.getDouble("dm-podiums.3.z")
        );

        return DMPos3;
    }

    public static Location getDMPos4() {
        File mapConfigFile = new File(Bukkit.getWorldContainer().getAbsolutePath().replace(".", "") + SG.get().getMapArena().getMapName() + "/map.yml");
        FileConfiguration mapConfig = YamlConfiguration.loadConfiguration(mapConfigFile);

        Location DMPos4 = new Location(
                Bukkit.getWorld(mapConfig.getString("dm-podiums.4.world")),
                mapConfig.getDouble("dm-podiums.4.x"),
                mapConfig.getDouble("dm-podiums.4.y"),
                mapConfig.getDouble("dm-podiums.4.z")
        );

        return DMPos4;
    }

}
