package fr.cocoraid.apocalypseskywar.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface EventListener {


    void onJoin(PlayerJoinEvent e);

    void onQuit(PlayerQuitEvent e);

    void onDie(Player p);

}