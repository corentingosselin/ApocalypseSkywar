package fr.cocoraid.apocalypseskywar.utils;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import fr.cocoraid.apocalypseskywar.position.Point;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class UtilLocation {

    private static MapManager mapManager = ApocalypseSkywar.getInstance().getMapManager();

    public static Location convert(World w, Point point) {
        Location l =  new Location(w, point.x ,point.y,point.z);
        Location finalLoc =  l.getBlock().getLocation().add(0.5,0.5,0.5);
        finalLoc.setYaw(point.yaw);
        finalLoc.setPitch(point.pitch);
        return l;
    }

    public static void createFastExplosion(Location l, int radius) {
        l.getWorld().spawnParticle(Particle.EXPLOSION_HUGE,l,2,0.5,0.5,0.5,0.1F);
        l.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,l,20,0.5,0.5,0.5,0.1F);
        l.getWorld().playSound(l, Sound.ENTITY_GENERIC_EXPLODE,1F,1F);
        sphere(l,radius).stream().filter(b -> b.getType() != Material.AIR).forEach(b -> {
           setBlockSuperFast(b,0,(byte) 0, true);
           mapManager.getArena().removeBlock(b);

        });

        getPlayersNearby(l,((int) (radius * 1.5))).forEach(cur -> {
            cur.damage(3);
        });
    }

    public static ArrayList<Block> sphere(final Location center, final int radius) {
        ArrayList<Block> sphere = new ArrayList<>();
        for (int Y = -radius; Y < radius; Y++)
            for (int X = -radius; X < radius; X++)
                for (int Z = -radius; Z < radius; Z++)
                    if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                        final Block block = center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(), Z + center.getBlockZ());
                        sphere.add(block);
                    }
        return sphere;
    }

    public static List<Block> cube(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static void setBlockSuperFast(Block b, int blockId, byte data, boolean applyPhysics) {
        net.minecraft.server.v1_12_R1.World w = ((CraftWorld) b.getWorld()).getHandle();
        net.minecraft.server.v1_12_R1.Chunk chunk = w.getChunkAt(b.getX() >> 4, b.getZ() >> 4);
        BlockPosition bp = new BlockPosition(b.getX(), b.getY(), b.getZ());
        int combined = blockId + (data << 12);
        IBlockData ibd = net.minecraft.server.v1_12_R1.Block.getByCombinedId(combined);
        if (applyPhysics) {
            w.setTypeAndData(bp, ibd, 3);
        } else {
            w.setTypeAndData(bp, ibd, 2);
        }
        chunk.a(bp, ibd);
    }

    public static List<Player> getPlayersNearby(Location loc, int distance) {
        List<Player> res = new ArrayList<>();
        int d2 = distance * distance;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld() == loc.getWorld() && p.getLocation().distanceSquared(loc) <= d2) {
                res.add(p);
            }
        }
        return res;
    }

    public static void displayParticle(Location point, Particle particle, MaterialData material, int amount, double x, double y, double z){
        point.getWorld().spawnParticle(
                particle,
                point,
                amount,
                x, y, z,
                material
        );
    }


}
