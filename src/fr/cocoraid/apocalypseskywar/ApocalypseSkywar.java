package fr.cocoraid.apocalypseskywar;

import fr.cocoraid.apocalypseskywar.command.PositionsCMD;
import fr.cocoraid.apocalypseskywar.command.SelectKitCMD;
import fr.cocoraid.apocalypseskywar.command.TestCMD;
import fr.cocoraid.apocalypseskywar.command.VoteCMD;
import fr.cocoraid.apocalypseskywar.events.CancelEvent;
import fr.cocoraid.apocalypseskywar.events.JoinQuitEvent;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import fr.cocoraid.apocalypseskywar.kit.KitManager;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import fr.cocoraid.apocalypseskywar.map.MapSerializer;
import fr.cocoraid.apocalypseskywar.task.AsyncTask;
import fr.cocoraid.apocalypseskywar.task.SyncTask;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ApocalypseSkywar extends JavaPlugin {

    private static ApocalypseSkywar instance;
    private GameManager gameManager;
    private KitManager kitManager;
    private MapManager mapManager;
    private MapSerializer mapSerializer;
    private Location lobby;

    @Override
    public void onEnable() {

        instance = this;
        this.mapSerializer = new MapSerializer();
        this.mapManager = new MapManager(this);
        Bukkit.getPluginManager().registerEvents(mapManager,this);

        gameManager = new GameManager();
        gameManager.enable();
        registerEvents();

        this.kitManager = new KitManager();

        new BukkitRunnable() {
            public void run() {
                World w = Bukkit.getWorld("world");
                w.setGameRuleValue("DoDaylightCycle", "false");
                w.setGameRuleValue("DoMobSpawning", "false");
                w.setDifficulty(Difficulty.PEACEFUL);
                w.setTime(0);
                lobby = new Location(w,191.5, 39 ,195.5,-180F,0F);
            }
        }.runTaskLater(this, 1L);

        registerCommands();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvent(this),this);
        Bukkit.getPluginManager().registerEvents(new CancelEvent(this),this);

        new AsyncTask(this).runTaskTimerAsynchronously(this,0,0);
        new SyncTask(this).runTaskTimer(this,0,0);
    }

    private void registerCommands() {
        this.getCommand("sk").setExecutor(new PositionsCMD(this));
        this.getCommand("vote").setExecutor(new VoteCMD(this));
        this.getCommand("test").setExecutor(new TestCMD(this));
        this.getCommand("kit").setExecutor(new SelectKitCMD(this));

    }

    @Override
    public void onDisable() {
        mapManager.deleteWorld();
    }

    public Location getLobby() {
        return lobby;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public MapSerializer getMapSerializer() {
        return mapSerializer;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public static ApocalypseSkywar getInstance() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
