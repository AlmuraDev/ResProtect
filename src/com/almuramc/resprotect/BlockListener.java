package com.almuramc.resprotect;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();      
        Material mat = event.getBlock().getType();
        String world = event.getBlock().getWorld().getName();
        String group = Residence.getPermissionManager().getGroupNameByPlayer(player);
        if (Residence.getItemManager().isIgnored(mat, group, world)) {
            return;
        }
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
        if (Residence.getConfigManager().enabledRentSystem()) {
            if (res != null) {
                String resname = res.getName();
                if (Residence.getConfigManager().preventRentModify() && Residence.getRentManager().isRented(resname)) {
                    player.sendMessage(ChatColor.RED + Residence.getLanguage().getPhrase("RentedModifyDeny"));
                    event.setCancelled(true);
                    return;
                }
            }
        }
        String pname = player.getName();
        if (res != null) {
            if (!res.getItemBlacklist().isAllowed(mat)) {
                player.sendMessage(ChatColor.RED + Residence.getLanguage().getPhrase("ItemBlacklisted"));
                event.setCancelled(true);
                return;
            }
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlock().getLocation(), player);
        boolean hasplace = perms.playerHas(pname, player.getWorld().getName(), "destroy", true);
        if (!hasplace) {
            if (Residence.isResAdminOn(event.getPlayer())) {
                if (ResProtectConfiguration.debug) {
                    event.getPlayer().sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed [Destroy] in this area because your an [ADMIN].");
                }
                return;
            }
            event.setCancelled(true);
            if (ResProtectConfiguration.debug && event.getPlayer().getItemInHand() != null) {
                Main.getInstance().getLogger().warning("[Debug - BlockListener.java] - BlockDamage - Player: " + event.getPlayer().getName() + " itemInHand: " + event.getPlayer().getItemInHand().getType().name().toUpperCase());
            }
            event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Action blocked.  [" + ChatColor.GREEN + "DESTROY" + ChatColor.WHITE + "] flag permission required.");
            return;
        }
    }
}
