package fr.cocoraid.apocalypseskywar.game.state;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.events.EventListener;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GameState {

    protected boolean isGameRunning = false;

    protected ApocalypseSkywar instance = ApocalypseSkywar.getInstance();
    protected GameManager gm = instance.getGameManager();
    protected MapManager mapManager = instance.getMapManager();


    private List<EventListener> eventListener = new ArrayList<>();

    protected void addEventListener(EventListener evtListener) {
        this.eventListener.add(evtListener);
    }



    public GameState() {
    }

    public void run() {
        addEventListener(new EventListener() {
            @Override
            public void onJoin(PlayerJoinEvent e) {
                if(isGameRunning) e.setJoinMessage(null);
            }

            @Override
            public void onDie(Player p) {

            }

            @Override
            public void onQuit(PlayerQuitEvent e) {
                e.setQuitMessage(null);
                checkGameState();
            }
        });
    }


    public void forceEnd() {


    }
    public void checkGameState() {
        //check if more than 2 players are onlines
        if(getGamers().isEmpty()) {
            //reset game, if it has already started (gameRunning or gameEnd) restart the server
            forceEnd();
        } else if(getGamers().size() == 1) {
            //set winner go to gameEnd =>
            gm.setCurrent(new GameEnd());
            gm.getCurrent().run();
        }

    }

    public List<EventListener> getEventListener() {
        return eventListener;
    }

    protected List<Player> getGamers() {
       return Bukkit.getOnlinePlayers().stream()
               .filter(cur -> !cur.getGameMode().equals(GameMode.SPECTATOR)).collect(Collectors.toList());
    }


}
