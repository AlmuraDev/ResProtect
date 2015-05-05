package com.almuramc.resprotect;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class EntityListener implements Listener {

    protected Map<String, Long> lastUpdate;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player == null) {
            return;
        }

        if (!player.isFlying()) {
            return;
        }

        if (!Residence.getPlayerListener().moveUpdate) {           
            return;
        }

        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
        if (res != null) {

            if (!(res.getPermissions().playerHas(player.getName(),"fly", false)) && player.isFlying()) {             
                if (res.getPermissions().playerHas(player.getName(), "admin", true)) {
                    return;
                }
                player.sendMessage(ChatColor.RED + "Flying is not allowed here.");
                player.setFlying(false);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType().getName().equalsIgnoreCase("MinecartRideable")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getRightClicked().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }

    }
}
