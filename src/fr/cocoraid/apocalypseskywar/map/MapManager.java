package fr.cocoraid.apocalypseskywar.map;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MapManager implements Listener {

    private Map<UUID, WorldMap> voters = new HashMap<>();
    private boolean vote = true;
    private MapInfo mapInfo;
    private WorldMap mapSelected;
    private Arena arena;
    private ApocalypseSkywar instance;
    public MapManager(ApocalypseSkywar instance) {
        this.instance = instance;
    }


    public WorldMap selectMap() {
        WorldMap bestMap = null;
        if(voters.isEmpty()) {
            //bestMap = random
            Random r = new Random();
            return WorldMap.POISONOUS_ISLANDS;
        } else if(voters.size() == 1) {
            return voters.values().iterator().next();
        }

        Map<WorldMap, Integer> counter = new HashMap<>();
        voters.keySet().forEach(v -> {
            WorldMap w = voters.get(v);
            counter.putIfAbsent(w,0);
            int i = counter.get(w);
            i++;
            counter.put(w,i);
        });


        int best_count = 0;
        for (WorldMap worldMap : counter.keySet()) {
            if(bestMap == null)
                bestMap = worldMap;

            int count = counter.get(worldMap);
            if(count > best_count) {
                bestMap = worldMap;
                best_count = count;
            }
        }

        return bestMap;

    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void noLagOnLoad(org.bukkit.event.world.WorldInitEvent event) {
        World world = event.getWorld();
        world.setKeepSpawnInMemory(false);
    }

    @EventHandler
    public void worldLoaded(WorldLoadEvent e) {
        if(mapSelected == null) {
            System.out.println(e.getWorld().getName() + " has not been recognized ! " +
                    "Because any map has been selected from the voting system");
            return;
        }
        World w = e.getWorld();
        System.out.println("world loaded " + w.getName());
        w.setGameRuleValue("DoDaylightCycle", "false");
        w.setGameRuleValue("DoMobSpawning", "false");
        w.setDifficulty(Difficulty.PEACEFUL);
        w.setTime(0);
        w.setAutoSave(false);
        w.setKeepSpawnInMemory(false);
        instance.getGameManager().setWorld(w);
        loadMapInfo(mapSelected);
        this.arena = new Arena(w, mapInfo.getCuboid());
        instance.getGameManager().selectDisasters(arena);

    }

    @EventHandler
    public void stopSaving(WorldSaveEvent event) {
        World world = event.getWorld();
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
    }

    @EventHandler
    public void stopWorldSaving(WorldUnloadEvent event) {
        World world = event.getWorld();
        for(Chunk chunk : world.getLoadedChunks()) chunk.unload(false);
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        event.setCancelled(true);
        Bukkit.unloadWorld(world, false);

    }

    public void pasteTemplate(WorldMap map) {
        this.mapSelected = map;
        String name = map.name().toLowerCase();
        File folder = new File(Bukkit.getWorldContainer(),"skywar_template/" + name);
        File destination = new File(Bukkit.getWorldContainer(), name);
        try {
            FileUtils.copyDirectory(folder, destination);
            WorldCreator creator = new WorldCreator(name);
            World w = Bukkit.getServer().createWorld(creator);
            w.setKeepSpawnInMemory(false);
            w.setAutoSave(false);

            System.out.println("loading world " + name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorld() {
        if(mapInfo == null) return;
        File folder = new File(Bukkit.getWorldContainer(), mapInfo.getMap().name().toLowerCase());
        if(folder.exists()) {
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Arena getArena() {
        return arena;
    }

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void loadMapInfo(WorldMap map) {
        try {
            this.mapInfo = new MapLoader(instance).loadMapInfo(map);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, WorldMap> getVoters() {
        return voters;
    }

    public boolean isVoteAllowed() {
        return vote;
    }

    public void setVoteAllowed(boolean vote) {
        this.vote = vote;
    }
}
