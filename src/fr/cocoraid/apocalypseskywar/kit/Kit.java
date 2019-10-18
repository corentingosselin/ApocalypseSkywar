package fr.cocoraid.apocalypseskywar.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public abstract class Kit {

    protected List<ItemStack> items = new ArrayList<>();
    protected ItemStack[] armor = new ItemStack[4];


    private String name;
    public Kit(String name) {
        this.name = name;
    }

    public void give(Player player) {
        PlayerInventory inv = player.getInventory();
        int i = 0;
        for (ItemStack item : items) {
            inv.setItem(i,item);
            i++;
        }
        inv.setArmorContents(armor);
    }


}
