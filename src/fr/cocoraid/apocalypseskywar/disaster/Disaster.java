package fr.cocoraid.apocalypseskywar.disaster;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public abstract class Disaster implements Listener {

    protected enum Phase {
        EASY(60),MEDIUM(60),DIFFICULT(200),HARD(0);

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
    private int current_phase_id = 0;
    protected Phase current_phase = Phase.EASY;

    public Disaster() {

    }

    private int random_start = 0;
    public void start() {
        Bukkit.getPluginManager().registerEvents(this, ApocalypseSkywar.getInstance());
        started = true;

        Random r = new Random();
        int max = 20 * 60 * 5;
        int min = 20 * 60;
        this.random_start = r.nextInt((max - min) + 1) + min;
        Bukkit.broadcastMessage("§cdisaster start in " + (random_start / 20) + "secponds");
        new BukkitRunnable() {
            @Override
            public void run() {
                nextPhase();
            }
        }.runTaskLaterAsynchronously(ApocalypseSkywar.getInstance(),random_start);


    }

    public void end() {
        started = false;
    }

    public void asyncUpdate() {

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
        }.runTaskLaterAsynchronously(ApocalypseSkywar.getInstance(),current_phase.delay);



    }

    public void syncUpdate() {

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
