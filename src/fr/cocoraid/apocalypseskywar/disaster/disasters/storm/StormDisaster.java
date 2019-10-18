package fr.cocoraid.apocalypseskywar.disaster.disasters.storm;

import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.disaster.Disaster;
import org.bukkit.block.Block;

public class StormDisaster extends Disaster {

    public StormDisaster(Arena arena) {
        super(arena);

    }

    @Override
    public void asyncUpdate() {
        if(time % 20 * 3 == 0) {
            Block b = arena.getRandomTopBlock();
            if (b == null) {
                //should not happens
            } else {
                b.getWorld().strikeLightning(b.getLocation());
            }
        }
    }
}
