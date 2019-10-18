package fr.cocoraid.apocalypseskywar.events;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import fr.cocoraid.apocalypseskywar.game.state.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvent implements Listener {


    private GameManager gm;
    public JoinQuitEvent(ApocalypseSkywar instance) {
        this.gm = instance.getGameManager();
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        GameState state = gm.getCurrent();
        state.getEventListener().forEach(ev -> ev.onJoin(e));



    }


    @EventHandler
    public void quit(PlayerQuitEvent e) {
        GameState state = gm.getCurrent();
        state.getEventListener().forEach(ev -> ev.onQuit(e));

    }
}
