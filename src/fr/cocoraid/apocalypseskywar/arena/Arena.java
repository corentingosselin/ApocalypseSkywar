package fr.cocoraid.apocalypseskywar.arena;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class Arena {

    private static ApocalypseSkywar instance = ApocalypseSkywar.getInstance();

    private World w;
    private Cuboid cuboid;
    private Set<Location> blocks = new HashSet<>();
    public Arena(World w, Cuboid cuboid) {
        this.w = w;
        this.cuboid = cuboid;

        generatorBlocks();
    }


    public void placeBlock(Block b) {
        blocks.add(b.getLocation());
    }

    public void removeBlock(Block b) {
        blocks.remove(b.getLocation());
    }

    public Block getRandomTopBlock() {
        List<Location> list = blocks.stream().filter(b ->
                b.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
                .collect(Collectors.toList());

        if(list.isEmpty())
            return null;
        else {
            Random rand = new Random();
            return list.get(rand.nextInt(list.size())).getBlock();
        }
    }

    /**
     *
     * 500 items = 100%
     * 10% => items(size) * iterator_percent / 100
     *
     * current_index = 0
     * current_index +=
     *
     */

    public void generateTopBlock() {
        List<Location> locs = getTopBlocks();
        locs.forEach(loc -> {
            UtilLocation.setBlockSuperFast(loc.getBlock(),2,(byte) 0,false);
        });

    }

    public void ungenTopBlock() {
        List<Location> locs = getTopBlocks();
        locs.forEach(loc -> {
            UtilLocation.setBlockSuperFast(loc.getBlock(),0,(byte) 0,false);
        });
        blocks.removeAll(locs);

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
                                    blocks.add(b.getLocation());
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
        }.runTaskTimer(instance,0,2);
    }

    public List<Location> getTopBlocks() {
        List<Location> list = blocks.stream().filter(b ->
                b.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
                .collect(Collectors.toList());
        return list;
    }


    public Set<Location> getBlocks() {
        return blocks;
    }

    public World getWorld() {
        return w;
    }
}
