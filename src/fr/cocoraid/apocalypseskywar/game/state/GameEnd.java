package fr.cocoraid.apocalypseskywar.game.state;

import fr.cocoraid.apocalypseskywar.game.GameMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEnd extends  GameState {

    public GameEnd() {

    }

    @Override
    public void forceEnd() {
        super.forceEnd();
        Bukkit.getServer().shutdown();
    }

    @Override
    public void run() {
        Player winner = getGamers().get(0);
        Bukkit.broadcastMessage(GameMessage.WINNER.replace("%player",winner.getName()));
        new BukkitRunnable(){
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        } .runTaskLater(instance,20*10);
    }
}
