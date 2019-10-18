package fr.cocoraid.apocalypseskywar.disaster.disasters.storm;

import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import fr.cocoraid.apocalypseskywar.utils.NMS;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;

public class StormDisaster extends Disaster {

    private int speed_tick = 20;
    private int explosionPower = 1;


    public StormDisaster() {
    }

    @Override
    public void start() {
        super.start();
        NMS.setSky(0.05F);
    }

    @Override
    public void nextPhase() {
        super.nextPhase();
        speed_tick -= 4;
        explosionPower+=1;
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
                b.getWorld().playSound(b.getLocation(), Sound.ENTITY_LIGHTNING_IMPACT,1F,1F);
                b.getWorld().playSound(b.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER,0.1F,1F);
                b.getWorld().strikeLightningEffect(b.getLocation());
                UtilLocation.createFastExplosion(b.getLocation(),explosionPower);

            }
        }

    }

    @Override
    public void asyncUpdate() {
        super.asyncUpdate();

    }
}
