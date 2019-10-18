package fr.cocoraid.apocalypseskywar.map;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MapManager implements Listener {

    private Map<UUID, WorldMap> voters = new HashMap<>();

    private MapInfo mapInfo;
    private WorldMap map = WorldMap.FROOZEN_FOREST;

    private ApocalypseSkywar instance;
    public MapManager(ApocalypseSkywar instance) {
        this.instance = instance;
    }


    //TODO at 10 seconds before starting we must disable vote system
    public WorldMap selectMap() {
        WorldMap bestMap = null;
        if(voters.isEmpty()) {
            //bestMap = random
            Random r = new Random();

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

    @EventHandler
    public void worldloaded(WorldLoadEvent e) {
        System.out.println("world loaded " + e.getWorld());
    }


    @EventHandler
    public void worldinit(WorldInitEvent e) {
        System.out.println("world inited " + e.getWorld());
    }

    public void pasteTemplate(WorldMap map) {
        String name = map.name().toLowerCase();
        File folder = new File(Bukkit.getWorldContainer(),"skywar_template/" + name);
        try {
           /* if(!folder.exists()){
                folder.mkdir();
            }*/
            FileUtils.copyDirectory(Bukkit.getServer().getWorldContainer(), folder);
            new WorldCreator(name)
                    .environment(World.Environment.NORMAL)
                    .createWorld();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void loadMap() {
        try {
            this.mapInfo = new MapLoader(instance).loadMapInfo();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
