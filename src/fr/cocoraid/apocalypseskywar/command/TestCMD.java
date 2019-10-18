package fr.cocoraid.apocalypseskywar.command;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCMD implements CommandExecutor {


    private MapManager mapManager;
    public TestCMD(ApocalypseSkywar instance) {
        this.mapManager = instance.getMapManager();
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                Location l = p.getLocation();

            }

        }
        return false;
    }
}
