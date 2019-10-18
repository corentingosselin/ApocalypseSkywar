package fr.cocoraid.apocalypseskywar.game.state;

import fr.cocoraid.apocalypseskywar.events.EventListener;
import fr.cocoraid.apocalypseskywar.game.GameMessage;
import fr.cocoraid.apocalypseskywar.position.Point;
import fr.cocoraid.apocalypseskywar.map.MapInfo;
import fr.cocoraid.apocalypseskywar.utils.UtilLocation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameStart extends GameState {


    private BukkitTask task;

    private LinkedHashMap<Player, Location> tp = new LinkedHashMap<>();

    public GameStart() {
        int i = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Point point = mapManager.getMapInfo().getPlayerSpawnPoints().get(i);
            tp.put(p, UtilLocation.convert(gm.getWorld(),point));
            i++;
        }
    }

    @Override
    public void run() {
        isGameRunning = true;
        gm.setMove(false);
        Bukkit.getOnlinePlayers().forEach(cur -> {
            cur.setHealth(20);
            cur.setFoodLevel(20);
            cur.setGameMode(GameMode.SURVIVAL);
        });


        addEventListener(new EventListener() {
            @Override
            public void onJoin(PlayerJoinEvent e) {
                if(!missingPlayer.isEmpty()) {
                    for(int i = 0; i < tp.size(); i++) {
                        if(missingPlayer.contains(i)) {
                            Location loc = (new ArrayList<>(tp.values())).get(i);
                            e.getPlayer().teleport(loc);
                            missingPlayer.remove(i);
                        }
                    }
                    if(missingPlayer.isEmpty())
                        Bukkit.setWhitelist(false);
                }
            }

            @Override
            public void onQuit(PlayerQuitEvent e) {}

            @Override
            public void onDie(Player p) {}
        });

        startSmoothTP();

        //give kits
    }

    @Override
    public void forceEnd() {
        super.forceEnd();
        if(task != null) task.cancel();
        Location loc = UtilLocation.convert(gm.getWorld(),mapManager.getMapInfo().getSpawn());
        Bukkit.getOnlinePlayers().forEach(cur -> cur.teleport(loc));
        gm.setDamage(false);
        gm.setBlockPlaceBreak(true);
        gm.setCurrent(new GameWaiting());
        gm.getCurrent().run();

    }



    private List<Integer> missingPlayer = new ArrayList<>();
    private void startSmoothTP() {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {

                Player p = (new ArrayList<>(tp.keySet())).get(i);
                if(p.isOnline()) {
                    p.teleport(tp.get(p));
                } else {
                    if(missingPlayer.isEmpty())
                        Bukkit.setWhitelist(false);
                    missingPlayer.add(i);
                }

                i++;
                if(i > tp.size() - 1) {
                    this.cancel();
                    start();
                }
            }
        }.runTaskTimer(instance,0,3);

    }


    private void start() {


       task = new BukkitRunnable() {
            int time = 5;
            @Override
            public void run() {
                Bukkit.broadcastMessage(GameMessage.STARTING.replace("%s",String.valueOf(time)));
                time--;
                if(time <= 0) {
                    this.cancel();
                    gm.setCurrent(new GameRunning());
                    gm.getCurrent().run();
                    gm.getSelectedDisasters().forEach(d -> d.start());
                }
            }
        }.runTaskTimer(instance,0,20);
    }
}
