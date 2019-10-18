package fr.cocoraid.apocalypseskywar;

import fr.cocoraid.apocalypseskywar.kit.Kit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SKPlayer {

    private static Map<UUID,SKPlayer> players = new HashMap<>();

    private UUID uuid;
    private Kit kit;
    private int kills;

    public SKPlayer(UUID uuid) {
        this.uuid = uuid;
        players.put(uuid,this);
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void addKill() {
        kills++;
    }
}
