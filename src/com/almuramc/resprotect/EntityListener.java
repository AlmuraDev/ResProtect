package com.almuramc.resprotect;

import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class EntityListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player == null || !player.isFlying()) { 
            return;
        }

        if (!Residence.getPlayerListener().moveUpdate) {
            if (ResProtectConfiguration.debug) {
                Main.getInstance().getLogger().warning("[Debug - EntityListener.java] - Player: " + event.getPlayer().getName() + " tried to fly but check was skipped due to check timing");        
            }
            return;
        }
        
        if (player.getWorld().getName().equalsIgnoreCase("DIM1") && !player.hasPermission("guardian.title")) {
            player.sendMessage("[" + ChatColor.DARK_AQUA + "Mother says..." + ChatColor.WHITE + " - Hi los ni vos wah bo het...");
            player.setFlying(false);
        }
        
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
        boolean hasPermission = false;
        
        if (res != null && perms != null) {
            hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "fly", true);
            if (!hasPermission) {
                if (Residence.isResAdminOn(event.getPlayer())) {
                    if (ResProtectConfiguration.debug) {
                        event.getPlayer().sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed [Build] in this area because your an [ADMIN].");
                    }
                    return;
                }
                if (ResProtectConfiguration.debug) {
                    Main.getInstance().getLogger().warning("[Debug - EntityListener.java] - Player: " + event.getPlayer().getName() + " tried to fly and was denied.");        
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
                    if (Residence.isResAdminOn(event.getPlayer())) {
                        if (ResProtectConfiguration.debug) {
                            event.getPlayer().sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed [Build] in this area because your an [ADMIN].");
                        }
                        return;
                    }
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Container] residence flag permission required.");
                    return;
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() == null) {
            //Catch Stupid
        }
        Boolean cancel = false;
        if (cancel) {
            event.setCancelled(true);
            if (event.getEntity() != null) {
                event.getEntity().remove();
            }
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
