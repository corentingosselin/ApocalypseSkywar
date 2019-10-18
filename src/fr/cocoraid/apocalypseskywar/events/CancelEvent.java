package fr.cocoraid.apocalypseskywar.events;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Arena;
import fr.cocoraid.apocalypseskywar.chest.ChestBonus;
import fr.cocoraid.apocalypseskywar.game.GameManager;
import fr.cocoraid.apocalypseskywar.game.state.GameStart;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class CancelEvent implements Listener {

    private GameManager gm;
    private MapManager mapManager;
    private ApocalypseSkywar instance;
    public CancelEvent(ApocalypseSkywar instance) {
        this.instance = instance;
        this.gm = instance.getGameManager();
        this.mapManager = instance.getMapManager();
    }



    @EventHandler
    public void foodLevel(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }


    @EventHandler
    public void detectChest(PlayerInteractEvent e) {
        if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                e.setCancelled(true);
                if(gm.getCurrent() instanceof GameStart) {
                    return;
                }
                new ChestBonus(e.getClickedBlock()).open();
            }

        }
    }

    @EventHandler
    public void fallblock(EntityChangeBlockEvent e) {
        Arena arena = mapManager.getArena();
        if (arena != null)
            arena.placeBlock(e.getBlock());
    }

    @EventHandler
    public void placeblock(BlockPlaceEvent e) {
        if(!gm.canBlockPlaceBreak()) e.setCancelled(true);
        else {
            Arena arena = mapManager.getArena();
            if (arena != null)
                arena.placeBlock(e.getBlock());
        }
    }

    @EventHandler
    public void breakblock(BlockBreakEvent e) {
        if(!gm.canBlockPlaceBreak()) e.setCancelled(true);
        else {
            if (mapManager.getArena() != null) {
                mapManager.getArena().removeBlock(e.getBlock());
            }
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
        if(!gm.canPlayerMove() ) {
            Location to = e.getFrom();
            to.setPitch(e.getTo().getPitch());
            to.setYaw(e.getTo().getYaw());
            e.setTo(to);
        }
    }

}
