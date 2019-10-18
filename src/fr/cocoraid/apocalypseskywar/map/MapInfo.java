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

   /*public static final Point HUB = new Point(-68,30,1421,0);

                new Point(-43,44,1473,160),
                new Point(-95,44,1473,-156),
                new Point(-127,44,1421,-90),
                new Point(-95,44,1369,-27),
                new Point(-43,44,1369,26),
                new Point(-11,44,1421,90)*/








}
