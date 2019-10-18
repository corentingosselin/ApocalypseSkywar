package fr.cocoraid.apocalypseskywar.events;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CancelEvent implements Listener {

    private GameManager gm;
    private ApocalypseSkywar instance;
    private Arena arena;
    public CancelEvent(ApocalypseSkywar instance) {
        this.instance = instance;
        this.gm = instance.getGameManager();
    }



    @EventHandler
    public void foodLevel(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void placeblock(BlockPlaceEvent e) {
        if(!gm.canBlockPlaceBreak()) e.setCancelled(true);
        else {
            if (arena != null)
                arena.placeBlock(e.getBlock());
        }
    }

    @EventHandler
    public void breakblock(BlockBreakEvent e) {
        if(!gm.canBlockPlaceBreak()) e.setCancelled(true);
        else {
            if (arena != null)
                arena.removeBlock(e.getBlock());
        }
    }

    @EventHandler
    public void damage(EntityDamageEvent e) {
        if(!gm.isDamage()) {
            e.setCancelled(true);
            return;
        }
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if ((p.getHealth() - e.getFinalDamage()) <= 0) {
                gm.getCurrent().getEventListener().forEach(ev -> ev.onDie(p));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if(!gm.canPlayerMove() && e.getFrom().getX() != e.getTo().getX() && e.getFrom().getZ() != e.getTo().getZ())
            e.setCancelled(true);
    }

}
