package com.almuramc.resprotect;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class EntityListener implements Listener {
    
    protected Map<String, Long> lastUpdate;
    
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        long last = lastUpdate.get(player.getName());
        long now = System.currentTimeMillis();
        if (now - last < Residence.getConfigManager().getMinMoveUpdateInterval()) {
            return;
        }
        lastUpdate.put(player.getName(), now);
        if (event.getFrom().getWorld() == event.getTo().getWorld()) {
            if (event.getFrom().distance(event.getTo()) == 0) {
                return;
            }
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
        if (res != null) {
            if (!(res.getPermissions().playerHas(player.getName(),"fly", false)) && player.isFlying()) {             
                if (res.getPermissions().playerHas(player.getName(), "admin", true)) {
                    return;
                }
                player.sendMessage("[Residence] - Fly is not allowed here.");
                player.setFlying(false);
            }
        }
    }
}
