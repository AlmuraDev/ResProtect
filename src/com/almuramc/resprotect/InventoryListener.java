package com.almuramc.resprotect;

import com.bekvon.bukkit.residence.Residence;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;


public class InventoryListener implements Listener {
    
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (ResProtectConfiguration.debug) {
            Player player = (Player) event.getWhoClicked();
            if (player != null) {
                Main.getInstance().getLogger().warning("[Debug - InventoryListener.java] - Player: " + player.getName() + " tried to click: " + event.getCurrentItem().getType().name().toUpperCase());
            }
        }
        if (ResProtectConfiguration.isBlacklisted(event.getCurrentItem().getType().name())) {
            Player player = (Player) event.getWhoClicked();
            if (Residence.isResAdminOn(player)) {
                if (ResProtectConfiguration.debug) {
                    player.sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed Item: " + event.getCurrentItem().getType().name() + " which is blacklisted because your an [ADMIN].");
                }
                return;
            }
            if (player != null) {
                player.sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + "] - Item: " + event.getCurrentItem().getType().name() + " is blacklisted.");
                Main.getInstance().getLogger().severe("[InventoryListener.java] - Player: " + player.getName() + " blacklist check deleted: " + event.getCurrentItem().getType().name().toUpperCase());
            }
            event.setCurrentItem(new ItemStack(0));
        }
    }
}
