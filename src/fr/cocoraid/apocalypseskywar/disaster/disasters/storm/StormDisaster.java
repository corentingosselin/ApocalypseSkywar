package fr.cocoraid.apocalypseskywar.disaster.disasters.storm;

import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class StormDisaster extends Disaster {

    private int speed_tick = 20;
    private int explosionPower = 1;
    private boolean fire = false;

    public StormDisaster() {
    }

    @Override
    public void nextPhase() {
        super.nextPhase();
        speed_tick-= 5;

        explosionPower+=1;
        if(current_phase ==  Phase.HARD)
            fire = true;
    }



    @Override
    public void syncUpdate() {
        super.syncUpdate();

        if(speed_tick == 0 || time % speed_tick == 0) {
            Block b = arena.getRandomTopBlock();
            if (b == null) {
                //should not happens
            } else {
                if(b.getType() == Material.AIR) {
                    arena.removeBlock(b);
                    return;
                }
                b.getWorld().strikeLightning(b.getLocation());
                UtilLocation.createFastExplosion(b.getLocation(),explosionPower);

            }
        }

    }

    @Override
    public void asyncUpdate() {
        super.asyncUpdate();

    }
}
