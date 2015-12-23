package com.almuramc.resprotect;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Listener;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;


public class PlayerEntityInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity ent = event.getRightClicked();
        // Item In-Hand Interact Check
        if (event.getPlayer().getItemInHand() != null) {
            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
            boolean hasPermission = false;
            if (ResProtectConfiguration.isBuildInteractBlocked(event.getPlayer().getItemInHand().getType().name().toUpperCase())) {
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true);
                if (!hasPermission) {
                    if (Residence.isResAdminOn(event.getPlayer())) {
                        if (ResProtectConfiguration.debug) {
                            event.getPlayer().sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed [Build] in this area because your an [ADMIN].");
                        }
                        return;
                    }
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "BUILD" + ChatColor.WHITE + "] flag permission required.");
                    if (ResProtectConfiguration.debug) {
                        Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " EntityInteract via BUILD itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
                    }
                    return;
                }
            }
        }
        
        if (!(ent instanceof Hanging)) {
            return;
        }

        Hanging hanging = (Hanging) ent;
        if (hanging.getType() != EntityType.ITEM_FRAME) {
            return;
        }

        FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);

        if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "itemframe", true)) {
            if (Residence.isResAdminOn(player)) {
                if (ResProtectConfiguration.debug) {
                    player.sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed interaction of Item: [Itemframe] in this area because your an [ADMIN].");
                }
                return;
            }
            event.setCancelled(true);
            event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Itemframe] residence flag permission required.");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!(event.getEntity() instanceof ItemFrame)) {
            return;
        }

        Entity ent = event.getEntity();

        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent attackevent = (EntityDamageByEntityEvent) event;
            Entity damager = attackevent.getDamager();
            if (damager instanceof Player) {
                Player player = (Player) damager;
                FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);

                if (!perms.playerHas(player.getName(), player.getWorld().getName(), "itemframe", true)) {
                    if (Residence.isResAdminOn(player)) {
                        if (ResProtectConfiguration.debug) {
                            player.sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed interaction of Item: [Itemframe] in this area because your an [ADMIN].");
                        }
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Itemframe] residence flag permission required.");
                }
            }
        }
    }
}
