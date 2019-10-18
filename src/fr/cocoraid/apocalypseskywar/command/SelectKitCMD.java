package fr.cocoraid.apocalypseskywar.command;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.game.GameMessage;
import fr.cocoraid.apocalypseskywar.kit.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectKitCMD implements CommandExecutor {


    private KitManager kitManager;
    public SelectKitCMD(ApocalypseSkywar instance) {
        this.kitManager = instance.getKitManager();
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 2) {
                String kit = args[1];
                if(kitManager.getKits().containsKey(kit)) {
                    p.sendMessage(GameMessage.KIT_SELECTED.replace("%kit",kit));
                    kitManager.getKits().get(kit).give(p);
                } else {
                    p.sendMessage(GameMessage.KIT_NOT_EXISTING.replace("%kit",kit));
                }

            }
        }
        return false;
    }
}
