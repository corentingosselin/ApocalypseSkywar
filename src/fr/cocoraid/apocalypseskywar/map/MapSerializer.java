package fr.cocoraid.apocalypseskywar.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapSerializer {

    private Gson gson;

    public MapSerializer() {
        this.gson = createGsonInstance();
    }

    private Gson createGsonInstance() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    }

    public String serialize(MapInfo map) {
        return this.gson.toJson(map);
    }

    public MapInfo deserialize(String json) {
        return this.gson.fromJson(json,MapInfo.class);
    }

}
