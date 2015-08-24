package com.almuramc.resprotect;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftItemFrame;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.entity.EntityDamageEvent;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.event.Listener;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

        if (!(ent instanceof Hanging)) {
            return;
        }

        Hanging hanging = (Hanging) ent;
        if (hanging.getType() != EntityType.ITEM_FRAME) {
            return;
        }

        FlagPermissions perms = Residence.getPermsByLocForPlayer(ent.getLocation(), player);

        if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "itemframe", true)) {
            event.setCancelled(true);
            System.out.println("Blocked Item Frame Interaction.");
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
                    event.setCancelled(true);
                }
            }
        }
    }
}
