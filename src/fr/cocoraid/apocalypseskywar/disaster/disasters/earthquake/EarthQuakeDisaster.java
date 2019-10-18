package fr.cocoraid.apocalypseskywar.disaster.disasters.earthquake;

import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthQuakeDisaster extends Disaster {

    private Map<Location,Integer> targets = new HashMap<>();


    @Override
    public void nextPhase() {
        super.nextPhase();

    }

    public EarthQuakeDisaster() {

    }


    @Override
    public void syncUpdate() {
        super.syncUpdate();

            Block b = arena.getRandomTopBlock();
            if (b != null && !targets.containsKey(b.getLocation())) {
                //add for 3 seconds
                targets.put(b.getLocation(), 60);
            }

        if(time % 5 == 0) {
            targets.keySet().forEach(t -> {
                t.getWorld().playSound(t, Sound.BLOCK_GRASS_BREAK,0.1F,0.2f);
            });
        }

        targets.keySet().removeIf(t -> {
           int i = targets.get(t);
           if(i <= 0) {
               fallQuake(t.getBlock());
               return true;
           } else {
               i--;
               targets.put(t, i);
               return false;
           }
        });

    }

    private void fallQuake(Block b) {
        Location first = b.getLocation();
        MaterialData data = b.getState().getData();
        Location l = b.getLocation();
        List<Location> toRemove = new ArrayList<>();
        while (l.getBlock().getType() != Material.AIR) {
            toRemove.add(l.clone());
            l.setY(l.getY() - 1.0);
        }
        toRemove.forEach(loc -> {
            UtilLocation.setBlockSuperFast(loc.getBlock(),0,(byte) 0,true);
            arena.removeBlock(loc.getBlock());
        });
        FallingBlock fb = first.getWorld().spawnFallingBlock(first.add(0.5,0,0.5),data);
        fb.setDropItem(false);

    }



    @Override
    public void asyncUpdate() {
        super.asyncUpdate();


        if(time % 3 == 0)
            targets.keySet().forEach(t -> {
                UtilLocation.displayParticle(t.clone().add(0.5,1,0.5), Particle.BLOCK_CRACK,t.getBlock().getState().getData(),4,0.01,0.02,0.01);
            });




    }

}
