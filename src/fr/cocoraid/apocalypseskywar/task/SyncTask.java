package fr.cocoraid.apocalypseskywar.task;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncTask extends BukkitRunnable {

    private GameManager gm;
    public SyncTask(ApocalypseSkywar instance) {
        this.gm = instance.getGameManager();
    }

    @Override
    public void run() {
        gm.getSelectedDisasters().stream().filter(d -> d.isStarted()).forEach(d -> d.syncUpdate());

    }
}
