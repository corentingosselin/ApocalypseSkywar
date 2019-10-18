package fr.cocoraid.apocalypseskywar.arena;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class Arena {

    private static ApocalypseSkywar instance = ApocalypseSkywar.getInstance();

    private World w;
    private Cuboid cuboid;
    private Set<Block> blocks = new HashSet<>();
    public Arena(World w, Cuboid cuboid) {
        this.w = w;
        this.cuboid = cuboid;

        generatorBlocks();
    }

    public void placeBlock(Block b) {
        blocks.add(b);
    }

    public void removeBlock(Block b) {
        blocks.remove(b);
    }

    public Block getRandomTopBlock() {
        List<Block> list = blocks.stream().filter(b ->
                b.getRelative(BlockFace.UP).getType() == Material.AIR)
                .collect(Collectors.toList());

        if(list.isEmpty())
            return null;
        else {
            Random rand = new Random();
            return list.get(rand.nextInt(list.size()));
        }
    }


    public void generatorBlocks() {
        Iterator<Chunk> iterator = cuboid.getChunks().iterator();
        new BukkitRunnable() {
            int chunksSize = cuboid.getChunks().size();
            int i = 0;
            @Override
            public void run() {

                if (iterator.hasNext()) {
                    Chunk chunk = iterator.next();
                    System.out.println("chunks left " + chunksSize--);

                    int bx = chunk.getX() << 4;
                    int bz = chunk.getZ() << 4;
                    for (int xx = bx; xx < bx + 16; xx++) {
                        for (int zz = bz; zz < bz + 16; zz++) {
                            for (int yy = 0; yy < 128; yy++) {
                                Block b = w.getBlockAt(xx,yy,zz);
                                if(b.getType() != Material.AIR)
                                    blocks.add(b);
                            }
                        }
                    }
                    i++;
                } else {
                    System.out.println("Operation performed in " + (i / 20) + " seconds and " + (i % 20) + " ticks" );
                    System.out.println("blocks found: " + blocks.size());
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(instance,0,0);
    }

}
