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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameWaiting extends GameState {

    private final int MAX_PLAYER = 6;
    private final int MIN_PLAYER = 4;

    private BukkitTask taskBeforeStart;

    private int timeBeforeStart = 30;
    private int playerCount = 0;


    public GameWaiting() {

    }

    @Override
    public void run() {
        super.run();
        gm.setDamage(false);


        addEventListener(new EventListener() {
            @Override
            public void onJoin(PlayerJoinEvent e) {
                e.getPlayer().teleport(instance.getLobby());
                e.getPlayer().setGameMode(GameMode.SURVIVAL);
                e.getPlayer().getInventory().clear();
                e.getPlayer().setHealth(20);
                e.setJoinMessage(GameMessage.JOIN_PLAYER.replace("%player",e.getPlayer().getName()));
                playerCount++;
                if(playerCount >= MAX_PLAYER) {
                    Bukkit.setWhitelist(true);
                }

                if(playerCount >= MIN_PLAYER) {
                    if(taskBeforeStart == null || (taskBeforeStart != null && taskBeforeStart.isCancelled()))
                        startBegin();
                }
            }

            @Override
            public void onDie(Player p) {

            }

            @Override
            public void onQuit(PlayerQuitEvent e) {

                playerCount--;
                if(playerCount < MAX_PLAYER)
                    Bukkit.setWhitelist(false);
                if(playerCount <= MIN_PLAYER) {
                    if(taskBeforeStart != null && !taskBeforeStart.isCancelled()) {
                        Bukkit.broadcastMessage(GameMessage.INTERRUPTION);
                        taskBeforeStart.cancel();
                        timeBeforeStart = 10;
                    }
                }
            }
        });
    }


    public void startBegin() {
        if(mapManager.getMapInfo() == null) {
            mapManager.setVoteAllowed(false);
           mapManager.pasteTemplate(mapManager.selectMap());
        }
        taskBeforeStart = new BukkitRunnable() {
            @Override
            public void run() {
                String msg = GameMessage.STARTING.replace("%s",String.valueOf(timeBeforeStart));
                if(timeBeforeStart % 10 == 0) {
                    Bukkit.getOnlinePlayers().forEach(cur -> {
                        cur.sendMessage(msg);
                        cur.playSound(cur.getLocation(), Sound.BLOCK_NOTE_PLING,1,1);
                    });

                } else if(timeBeforeStart <= 10) {
                    Bukkit.broadcastMessage(msg);
                }
                timeBeforeStart--;
                if(timeBeforeStart <= 0) {
                    this.cancel();
                    gm.setCurrent(new GameStart());
                    gm.getCurrent().run();
                }
            }
        }.runTaskTimer(instance,0L,20L);

    }
}
