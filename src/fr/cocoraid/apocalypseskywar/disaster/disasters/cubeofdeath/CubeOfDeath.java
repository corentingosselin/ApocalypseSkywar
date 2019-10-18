package fr.cocoraid.apocalypseskywar.disaster.disasters.cubeofdeath;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CubeOfDeath {

    private static List<Vector> cube = new ArrayList<>();
    static {
        int radius = 7;
        for(int x = 0; x < radius;x++){
            for(int y = 0; y < radius;y++) {
                for(int z = 0;z < radius;z++) {
                    if(x == 0 || x == radius-1 ||  y == 0 || y == radius-1 || z  == 0 || z == radius-1) {
                        Vector v = new Vector(x - radius / 2, y - (radius / 2) - 1, z - radius / 2);
                        cube.add(v);
                    }
                }
            }
        }
    }

    private List<Location> blockToFill = new ArrayList<>();
    private List<Location> blockToRemove = new ArrayList<>();

    private int x1, y1, z1;
    private int x2, y2, z2;


    private Location center;
    public CubeOfDeath(Location location) {
        this.center = location;
        cube.forEach(v -> {
            blockToFill.add(center.clone().add(v));
        });

        Block[] blocks = findCorners(blockToFill);
        Location l1 = blocks[0].getLocation();
        Location l2 = blocks[1].getLocation();

        x1 = Math.min((int) l1.getX(), (int)l2.getX());
        y1 = Math.min((int) l1.getY(),(int) l2.getY());
        z1 = Math.min((int) l1.getZ(),(int) l2.getZ());
        x2 = Math.max((int) l1.getX(),(int) l2.getX());
        y2 = Math.max((int) l1.getY(),(int) l2.getY());
        z2 = Math.max((int) l1.getZ(),(int) l2.getZ());


    }

    public boolean contains(int x, int y, int z) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public void fillAll() {
        Collections.shuffle(blockToFill);
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if(blockToFill.isEmpty()) {
                    this.cancel();
                    blockToRemove.forEach(b -> {
                        Bukkit.getOnlinePlayers().forEach(cur -> {
                            Location l = cur.getLocation();
                            if(contains(l.getBlockX(),l.getBlockY(),l.getBlockZ())) {
                                cur.damage(5,cur);
                            }
                        });
                        UtilLocation.setBlockSuperFast(b.getBlock(), 0,(byte) 0,false);
                    });
                    center.getWorld().playSound(center, Sound.BLOCK_GLASS_BREAK,1F,0F);
                    center.getWorld().playSound(center, Sound.ENTITY_BLAZE_HURT,0.8F,0F);
                    return;
                }
                for(int k = 0; k < 2 ; k++) {
                    Location l = blockToFill.get(i);
                    Bukkit.getOnlinePlayers().forEach(cur -> {
                        cur.sendBlockChange(l, Material.STAINED_GLASS, (byte) 15);
                    });
                    blockToFill.remove(i);
                    blockToRemove.add(l);
                }

            }
        }.runTaskTimer(ApocalypseSkywar.getInstance(),0,0);
    }


    private Block[] findCorners(List<Location> locs) {
        Block b1 = null;
        for (Location loc : locs) {
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            int bX1 = b1 == null ? -1 : b1.getLocation().getBlockX();
            int bY1 = b1 == null ? -1 : b1.getLocation().getBlockY();
            int bZ1 = b1 == null ? -1 : b1.getLocation().getBlockZ();

            if (b1 == null || x < bX1 && y < bY1 && z < bZ1) {
                b1 = loc.getBlock();
            }
        }
        Block b2 = null;
        for (Location location: locs) {
            if (b2 == null || (b1.getLocation().distanceSquared(location) > b2.getLocation().distanceSquared(b1.getLocation()))) {
                b2 = location.getBlock();
            }
        }
        return new Block[] {
                b1, b2
        };
    }



}
