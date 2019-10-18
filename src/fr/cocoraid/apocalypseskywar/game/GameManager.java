package fr.cocoraid.apocalypseskywar.game;

import fr.cocoraid.apocalypseskywar.game.state.GameState;
import fr.cocoraid.apocalypseskywar.game.state.GameWaiting;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.bukkit.World;

public class GameManager {

    //TODO select random or vote system
    private WorldMap map = WorldMap.FROOZEN_FOREST;

    private boolean damage = false;
    private boolean move = true;
    private boolean blockPlaceBreak = false;

    private World world;
    private GameState current;

    public GameManager() {

    }

    public void enable() {
        this.current = new GameWaiting();
        current.run();
    }


    public GameState getCurrent() {
        return current;
    }

    public void setCurrent(GameState current) {
        this.current = current;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public boolean canPlayerMove() {
        return move;
    }

    public boolean isDamage() {
        return damage;
    }

    public WorldMap getMap() {
        return map;
    }

    public boolean canBlockPlaceBreak() {
        return blockPlaceBreak;
    }

    public void setBlockPlaceBreak(boolean blockPlaceBreak) {
        this.blockPlaceBreak = blockPlaceBreak;
    }
}
