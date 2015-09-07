package com.almuramc.resprotect;

import org.bukkit.ChatColor;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class PlayerInteractListener implements Listener {

    boolean debug = true;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {        
        if (event.getBlock() == null) return;
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlock().getLocation(), event.getPlayer());
        if (perms == null) return;        
        if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH) // Do not use ignoreCancelled = true because Forge cancels click air events by default, how stupid.
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Debug
        if (ResProtectConfiguration.debug && event.getPlayer().getItemInHand() != null) {
            Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
        }
        if (ResProtectConfiguration.debug && event.getClickedBlock() != null) {
            Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " clickedBlock: " + event.getClickedBlock().getType().name().toUpperCase());
        }
        // Item In-Hand Interact Check
        if (event.getPlayer().getItemInHand() != null) {
            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            boolean hasPermission = false;

            if (res != null && perms != null) {
                if (ResProtectConfiguration.isPvpInteractBlocked(event.getPlayer().getItemInHand().getType().name().toUpperCase())) {
                    hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "pvp", true);
                    if (!hasPermission) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "PVP" + ChatColor.WHITE + "] flag permission required.");
                        if (ResProtectConfiguration.debug) {
                            Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " Blocked PVP itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
                        }
                        return;
                    }
                }

                if (ResProtectConfiguration.isBuildInteractBlocked(event.getPlayer().getItemInHand().getType().name().toUpperCase())) {
                    hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true);
                    if (!hasPermission) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "BUILD" + ChatColor.WHITE + "] flag permission required.");
                        if (ResProtectConfiguration.debug) {
                            Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " Blocked BUILD itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
                        }
                        return;
                    }
                }

                if (ResProtectConfiguration.isBucketInteractBlocked(event.getPlayer().getItemInHand().getType().name().toUpperCase())) {
                    hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true);
                    if (!hasPermission) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "BUCKET" + ChatColor.WHITE + "] flag permission required.");
                        if (ResProtectConfiguration.debug) {
                            Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " Blocked BUCKET itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
                        }
                        return;
                    }
                }
            }
        }

        // Block Clicked Check
        if (event.getClickedBlock() != null) {
            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getClickedBlock().getLocation(), event.getPlayer());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            boolean hasPermission = false;

            if (res == null || perms == null) {
                return;
            }

            // Container Check
            if (ResProtectConfiguration.isContainerBlockBlocked(event.getClickedBlock().getType().name().toUpperCase())) {
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true);
                if (!hasPermission) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "CONTAINER" + ChatColor.WHITE + "] flag permission required.");
                    if (ResProtectConfiguration.debug) {
                        Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " Blocked Container clickedBlock: " + event.getClickedBlock().getType().name().toUpperCase());
                    }
                    return;
                }
            }

            // Door Check
            if (ResProtectConfiguration.isDoorBlockBlocked(event.getClickedBlock().getType().name().toUpperCase())) {
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true);
                if (!hasPermission) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "DOOR" + ChatColor.WHITE + "] flag permission required.");
                    if (ResProtectConfiguration.debug) {
                        Main.getInstance().getLogger().warning("[Debug - PlayerInteractListener.java] - Player: " + event.getPlayer().getName() + " Blocked Door clickedBlock: " + event.getClickedBlock().getType().name().toUpperCase());
                    }
                    return;
                }
            }
        }
    }
}