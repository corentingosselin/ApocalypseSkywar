package fr.cocoraid.apocalypseskywar.disaster.disasters.cubeofdeath;

import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CubeOfDeathDisaster extends Disaster {

    private int speed_tick = 20;

    public CubeOfDeathDisaster() {
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void nextPhase() {
        super.nextPhase();
        speed_tick-=3;
    }



    @Override
    public void syncUpdate() {
        super.syncUpdate();

        if(speed_tick == 0 || time % speed_tick == 0) {
            Block b = arena.getRandomTopBlock();
            if(b != null && b.getType() != Material.AIR) {
                new CubeOfDeath(b.getLocation()).fillAll();
            }
        }

    }

    @Override
    public void asyncUpdate() {
        super.asyncUpdate();

    }
}
