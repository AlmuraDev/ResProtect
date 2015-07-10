package com.almuramc.resprotect;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {        
        if (event.getBlock() == null) return;

        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlock().getLocation(), event.getPlayer());
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());

        if (res != null) return;

        if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;        

        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getClickedBlock().getLocation(), event.getPlayer());
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());

        if (res == null || perms == null) return;        

        // Bibliocraft Signs
        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOFANCYSIGN")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOTABLE")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOTHECA")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIORACK")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOSHELF")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }


        // Railcraft Machines
        if (event.getClickedBlock().getType() == Material.getMaterial("RAILCRAFT_MACHINEGAMMA")) {           
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {             
                event.setCancelled(true);
                return;
            }           
        }

        // Thermal Expansion Machines
        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_MACHINE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        // Thermal Expansion Machines
        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_CACHE")) {                        
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        // IC2 Block Machines
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKMACHINE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        // IC2 Block Generators
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKGENERATOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
     // IC2 Block Generators
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("CARPENTERSBLOCKS_BLOCKCARPENTERSAFE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
