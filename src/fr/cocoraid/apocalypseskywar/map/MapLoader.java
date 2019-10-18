package fr.cocoraid.apocalypseskywar.map;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.utils.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class MapLoader {

    private ApocalypseSkywar instance;
    public MapLoader(ApocalypseSkywar instance)  {
      this.instance = instance;
    }

    public  MapInfo loadMapInfo() throws IOException {
        File serverFolder = new File(Bukkit.getServer().getWorldContainer(),"world");
        File mapInfoFile = new File(serverFolder,"mapinfo.yml");
        if(!serverFolder.exists() || !mapInfoFile.exists()) {
            throw new IOException("Impossible de charger le mapinfo.yml, car il n'a pas été trouvé dans le dossier world");

        }

        final String json = FileUtils.loadContent(mapInfoFile);
        return instance.getMapSerializer().deserialize(json);
    }




}
