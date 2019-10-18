package fr.cocoraid.apocalypseskywar.disaster;

import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.position.WorldMap;

public abstract class Disaster {

    protected WorldMap[] specific_maps;
    protected boolean started = false;
    protected Arena arena;
    protected int time = 0;

    public Disaster(Arena arena) {
        this.arena = arena;
    }

    public void start() {
        started = true;
    }

    public void end() {
        started = false;
    }

    public void asyncUpdate() {


        time++;
        time%=1000;
    }

    public void syncUpdate() {

    }
}
