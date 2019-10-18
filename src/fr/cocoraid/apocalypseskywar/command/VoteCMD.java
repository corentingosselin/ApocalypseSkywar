package fr.cocoraid.apocalypseskywar.command;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCMD implements CommandExecutor {


    private MapManager mapManager;
    public VoteCMD(ApocalypseSkywar instance) {
        this.mapManager = instance.getMapManager();
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                String name = args[0];

                if(!mapManager.isVoteAllowed()) {
                    p.sendMessage("§cVous ne pouvez plus voter...");
                    return false;
                }

                WorldMap map = null;
                for (WorldMap value : WorldMap.values()) {
                    if(value.name().equalsIgnoreCase(name)) {
                        map = value;
                        break;
                    }
                }
                if(map == null) {
                    p.sendMessage("§cLe nom de map n'a pas été reconnu" +
                            "\n§cVoici une liste de noms: ");
                    for (WorldMap value : WorldMap.values()) {
                        p.sendMessage(" §6- " + value.name());
                    }
                    return false;
                }

                mapManager.getVoters().put(p.getUniqueId(),map);
                p.sendMessage("§bVous avez voté pour la map: " + "§6" + map.name());



            }
        }
        return false;
    }
}
