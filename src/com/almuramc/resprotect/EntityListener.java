package com.almuramc.resprotect;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;
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
                player.sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Fly] residence flag permission required.");
                player.setFlying(false);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if ("MinecartRideable".equalsIgnoreCase(event.getRightClicked().getType().getName())) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getRightClicked().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Container] residence flag permission required.");
                    return;
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() == null)
            return;
        Boolean cancel = false;
        if (cancel) {
            event.setCancelled(true);
            event.getEntity().remove();
        } else {
            List<Block> preserve = new ArrayList<Block>();
            for (Block block : event.blockList()) {
                FlagPermissions blockperms = Residence.getPermsByLoc(block.getLocation());
                if (!blockperms.has("explode", true)) {
                    if (block != null) {
                        preserve.add(block);
                    }
                }
            }
            for (Block block : preserve) {
                event.blockList().remove(block);
            }
        }
    }
}
