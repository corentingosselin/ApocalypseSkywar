package fr.cocoraid.apocalypseskywar.game.state;

import fr.cocoraid.apocalypseskywar.events.EventListener;
import fr.cocoraid.apocalypseskywar.game.GameMessage;
import fr.cocoraid.apocalypseskywar.map.MapInfo;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameRunning extends GameState {

    public GameRunning() {
    }

    @Override
    public void run() {
        //send start message

        Bukkit.getOnlinePlayers().forEach(cur -> {
            cur.sendMessage(GameMessage.STARTED);
            cur.playSound(cur.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
        });

        gm.setMove(true);
        gm.setDamage(true);
        gm.setBlockPlaceBreak(true);

        addEventListener(new EventListener() {
            @Override
            public void onJoin(PlayerJoinEvent e) {
                e.getPlayer().setGameMode(GameMode.SPECTATOR);
                e.getPlayer().teleport(UtilLocation.convert(gm.getWorld(),mapManager.getMapInfo().getSpawn()));
            }

            @Override
            public void onQuit(PlayerQuitEvent e) {

            }

            @Override
            public void onDie(Player p) {
                p.setGameMode(GameMode.SPECTATOR);
                p.teleport(UtilLocation.convert(gm.getWorld(),mapManager.getMapInfo().getSpawn()));
                Bukkit.broadcastMessage(GameMessage.DEAD.replace("%killed",p.getName()));
                if(getGamers().size() <= 1) {
                    gm.setCurrent(new GameEnd());
                    gm.getCurrent().run();
                }
            }
        });
    }

    @Override
    public void forceEnd() {
        super.forceEnd();
        Bukkit.getServer().shutdown();
    }


}
