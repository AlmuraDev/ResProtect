package com.almuramc.resprotect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class PlayerInteractListener implements Listener {
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {        
        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().toString().equalsIgnoreCase("BIBLIOCRAFT_BIBLIOFANCYSIGN")) { // Bibliocraft Signs
                ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
                if (res != null) {
                    if (!res.getPermissions().playerHas(event.getPlayer().getName(),"place", true)) {             
                        //Todo:  send packet back to client to close GUI.
                    }
                }
            }
        }
    }
}
