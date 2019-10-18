package fr.cocoraid.apocalypseskywar.utils;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_12_R1.TileEntityChest;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

public class NMS {

    public static void playChestAction(Chest chest, boolean open) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.getBlock(), 1, open ? 1 : 0);
    }

    public static void setSky(float time) {
        PacketPlayOutGameStateChange packetA = new PacketPlayOutGameStateChange(8,80F);
        PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(7,time);
        Bukkit.getOnlinePlayers().forEach(cur -> {
            ((CraftPlayer)cur).getHandle().playerConnection.sendPacket(packetA);
            ((CraftPlayer)cur).getHandle().playerConnection.sendPacket(packet);
        });
    }
}
