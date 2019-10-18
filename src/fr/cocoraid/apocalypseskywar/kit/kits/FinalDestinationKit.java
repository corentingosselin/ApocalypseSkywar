package fr.cocoraid.apocalypseskywar.kit.kits;

import fr.cocoraid.apocalypseskywar.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FinalDestinationKit extends Kit {

    public FinalDestinationKit() {
        super("destination finale");
        items.add(new ItemStack(Material.DIAMOND_SWORD));
        armor[0] = new ItemStack(Material.DIAMOND_HELMET);
    }


}
