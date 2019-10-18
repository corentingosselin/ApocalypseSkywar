package fr.cocoraid.apocalypseskywar.utils;

import fr.cocoraid.apocalypseskywar.position.Point;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.IBlockData;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;

public class UtilLocation {

    public static Location convert(World w, Point point) {
        Location l =  new Location(w, point.x ,point.y,point.z);
        Location finalLoc =  l.getBlock().getLocation().add(0.5,0.5,0.5);
        finalLoc.setYaw(point.yaw);
        finalLoc.setPitch(point.pitch);
        return l;
    }

    public void setBlockSuperFast(Block b, int blockId, byte data, boolean applyPhysics) {
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

}
