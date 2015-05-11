package com.almuramc.resprotect;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (event.getBlock() != null) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"build", true)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        // Bibliocraft Signs
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOFANCYSIGN")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"build", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // Railcraft Machines
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("RAILCRAFT_MACHINEGAMMA")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // Thermal Expansion Machines
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_MACHINE")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // IC2 Block Machines
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKMACHINE")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // IC2 Block Generators
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKGENERATOR")) {
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            if (res != null) {
                if (!res.getPermissions().playerHas(event.getPlayer().getName(),"container", true)) {             
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
}
