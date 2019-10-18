package fr.cocoraid.apocalypseskywar.kit.kits;

import fr.cocoraid.apocalypseskywar.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ClassicKit extends Kit {

    public ClassicKit() {
        super("classic");
        items.add(new ItemStack(Material.DIAMOND_SWORD));
        armor[0] = new ItemStack(Material.DIAMOND_HELMET);
    }

}
