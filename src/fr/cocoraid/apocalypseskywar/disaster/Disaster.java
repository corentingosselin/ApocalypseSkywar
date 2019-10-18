package fr.cocoraid.apocalypseskywar.disaster;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Disaster implements Listener {

    protected enum Phase {
        EASY(30),MEDIUM(40),DIFFICULT(120),HARD(0);

        private int delay;
        Phase(int delay) {
            this.delay = delay;
        }
    }

    protected WorldMap[] specific_maps;
    protected boolean started = false;
    protected Arena arena;
    protected World world;
    protected int time = 0;
    private int current_phase_id = -1;
    protected Phase current_phase;

    public Disaster() {

    }

    private int random_start = 0;
    public void start() {
        Bukkit.getPluginManager().registerEvents(this, ApocalypseSkywar.getInstance());


        int max = 20 * 60 * 5;
        int min = 20 * 60;
        random_start = ThreadLocalRandom.current().nextInt(min, max + 1);
        this.random_start = 15;
        Bukkit.broadcastMessage("§cdisaster start in " + (random_start / 20) + " seconds");
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage("disaster is starting...");
                current_phase_id = 1;
                current_phase = Phase.EASY;
                nextPhase();
                started = true;
            }
        }.runTaskLaterAsynchronously(ApocalypseSkywar.getInstance(), random_start);


    }

    public void end() {
        started = false;
    }

    public void asyncUpdate() {
        if(current_phase == null) return;
    }

    public void nextPhase() {
        if(current_phase == Phase.HARD) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                current_phase_id++;
                current_phase = Phase.values()[current_phase_id];
                nextPhase();
            }
        }.runTaskLater(ApocalypseSkywar.getInstance(),current_phase.delay);



    }

    public void syncUpdate() {
        if(current_phase == null) return;

        time++;
    }

    public boolean isStarted() {
        return started;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
        this.world = arena.getWorld();
    }
}
