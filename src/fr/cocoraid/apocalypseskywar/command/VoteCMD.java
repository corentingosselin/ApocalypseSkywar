package fr.cocoraid.apocalypseskywar.command;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCMD implements CommandExecutor {



    public VoteCMD(ApocalypseSkywar instance) {

    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 2) {
                String map = args[1];


            }
        }
        return false;
    }
}
