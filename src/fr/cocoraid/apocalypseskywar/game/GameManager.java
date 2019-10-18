package fr.cocoraid.apocalypseskywar.game;

import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import fr.cocoraid.apocalypseskywar.disaster.disasters.cubeofdeath.CubeOfDeathDisaster;
import fr.cocoraid.apocalypseskywar.disaster.disasters.earthquake.EarthQuakeDisaster;
import fr.cocoraid.apocalypseskywar.disaster.disasters.storm.StormDisaster;
import fr.cocoraid.apocalypseskywar.game.state.GameState;
import fr.cocoraid.apocalypseskywar.game.state.GameWaiting;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private List<Disaster> disasters = new ArrayList<>();
    private List<Disaster> selectedDisasters = new ArrayList<>();

    private boolean damage = false;
    private boolean move = true;
    private boolean blockPlaceBreak = false;

    private World world;
    private GameState current;

    public GameManager() {
        disasters.add(new CubeOfDeathDisaster());
        disasters.add(new StormDisaster());
        disasters.add(new EarthQuakeDisaster());
    }

    public void selectDisasters(Arena arena) {
        //TODO select and add probabilities with map that are suit for

        selectedDisasters.add(disasters.get(1));


        //setup arena object for selected disasters
        selectedDisasters.forEach(s -> s.setArena(arena));
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

    public boolean canBlockPlaceBreak() {
        return blockPlaceBreak;
    }

    public void setBlockPlaceBreak(boolean blockPlaceBreak) {
        this.blockPlaceBreak = blockPlaceBreak;
    }

    public List<Disaster> getSelectedDisasters() {
        return selectedDisasters;
    }

    public List<Disaster> getDisasters() {
        return disasters;
    }
}
