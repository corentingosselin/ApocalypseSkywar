package fr.cocoraid.apocalypseskywar.chest;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.utils.NMS;
import fr.cocoraid.apocalypseskywar.utils.RandomCollection;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class ChestBonus {

    private Block b;
    public ChestBonus(Block b) {
        this.b = b;
    }

    private static MaterialData[] poppers = new MaterialData[] {
            new MaterialData(Material.CONCRETE,(byte) 1),
            new MaterialData(Material.CONCRETE,(byte) 2),
            new MaterialData(Material.CONCRETE,(byte) 3),
            new MaterialData(Material.CONCRETE,(byte) 4),
            new MaterialData(Material.CONCRETE,(byte) 5),
            new MaterialData(Material.CONCRETE,(byte) 10),
            new MaterialData(Material.CONCRETE,(byte) 14),
    };


    public void open() {
        Chest chest = (Chest) b.getState();
        NMS.playChestAction(chest,true);
        chest.getWorld().playSound(b.getLocation(), Sound.BLOCK_CHEST_OPEN,1F,1F);
        new BukkitRunnable() {
            @Override
            public void run() {
                b.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,b.getLocation().add(0.5,0.5,0.5),10,0.5,0.5,0.5,0.1F);
                for (MaterialData popper : poppers) {
                    UtilLocation.displayParticle(b.getLocation().add(0.5,0.7,0.5), Particle.BLOCK_CRACK,popper,20,0.001,0.002,0.001 );
                }

                b.getWorld().playSound(b.getLocation(),Sound.ITEM_ARMOR_EQUIP_CHAIN,2F,0F);
                b.getWorld().playSound(b.getLocation(),Sound.ENTITY_CHICKEN_EGG,1F,1F);
                b.setType(Material.AIR);
                ApocalypseSkywar.getInstance().getMapManager().getArena().removeBlock(b);
                drop();
            }
        }.runTaskLater(ApocalypseSkywar.getInstance(),10);

    }


    private void drop() {
        int randomNum = ThreadLocalRandom.current().nextInt(2, 4 + 1);
        for(int i = 0; i < randomNum; i++) {
            b.getWorld().dropItemNaturally(b.getLocation().add(0.5,0.7,0.5),stuff.next());
        }
    }

    private static RandomCollection<ItemStack> stuff = new RandomCollection<>();
    static {

        stuff.add(0.1,  new ItemStack(Material.DIAMOND_SWORD));
        stuff.add(1.0,  new ItemStack(Material.STONE_SWORD));
        stuff.add(0.5,  new ItemStack(Material.IRON_SWORD));

        stuff.add(0.1,  new ItemStack(Material.DIAMOND_HELMET));
        stuff.add(0.5,  new ItemStack(Material.IRON_HELMET));

        stuff.add(0.1,  new ItemStack(Material.DIAMOND_CHESTPLATE));
        stuff.add(0.5,  new ItemStack(Material.IRON_CHESTPLATE));

        stuff.add(0.1,  new ItemStack(Material.DIAMOND_LEGGINGS));
        stuff.add(0.5,  new ItemStack(Material.IRON_LEGGINGS));

        stuff.add(0.1,  new ItemStack(Material.DIAMOND_BOOTS));
        stuff.add(0.5,  new ItemStack(Material.IRON_BOOTS));

        stuff.add(0.1,  new ItemStack(Material.GOLDEN_APPLE));

        stuff.add(0.2,  new ItemStack(Material.TNT));
        stuff.add(0.2,  new ItemStack(Material.FLINT_AND_STEEL));

        stuff.add(0.5,  new ItemStack(Material.BOW));
        stuff.add(0.7,  new ItemStack(Material.ARROW,10));

        stuff.add(0.5,  new ItemStack(Material.FISHING_ROD));
        stuff.add(1,  new ItemStack(Material.WOOD, 20));

        stuff.add(0.05,  new ItemStack(Material.ELYTRA));
        stuff.add(0.6,  new ItemStack(Material.EXP_BOTTLE, 20));

        stuff.add(0.7,  new ItemStack(Material.SNOW_BALL,10));
        stuff.add(0.7,  new ItemStack(Material.LAVA_BUCKET));

        stuff.add(0.7,  new ItemStack(Material.WEB,4));
    }


}
