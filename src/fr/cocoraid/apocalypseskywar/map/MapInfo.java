package fr.cocoraid.apocalypseskywar.map;

import fr.cocoraid.apocalypseskywar.arena.Cuboid;
import fr.cocoraid.apocalypseskywar.position.Point;
import fr.cocoraid.apocalypseskywar.position.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class MapInfo {


    private WorldMap map;
    private Point spawn;
    private List<Point> playerSpawnPoints = new ArrayList<>();
    private Cuboid cuboid;


    public MapInfo() {

    }

    public void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
    }

    public void setSpawnPoint(Point spawn) {
        this.spawn = spawn;
    }

    public void setMap(WorldMap map) {
        this.map = map;
    }

    public WorldMap getMap() {
        return map;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public List<Point> getPlayerSpawnPoints() {
        return playerSpawnPoints;
    }

    public Point getSpawn() {
        return spawn;
    }








}
