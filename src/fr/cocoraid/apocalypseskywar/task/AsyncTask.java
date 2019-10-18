package fr.cocoraid.apocalypseskywar.task;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncTask extends BukkitRunnable {

    private GameManager gm;
    public AsyncTask(ApocalypseSkywar instance) {
        this.gm = instance.getGameManager();
    }

    @Override
    public void run() {
        gm.getSelectedDisasters().stream().filter(d -> d.isStarted()).forEach(d -> d.asyncUpdate());
    }
}
