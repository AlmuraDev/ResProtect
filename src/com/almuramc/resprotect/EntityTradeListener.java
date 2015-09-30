/*
 * This file is part of resprotect.
 *
 * Copyright (c) 2012, AlmuraDev <http://www.almuramc.com/>
 * resprotect is licensed under the Almura Development License version 1.
 *
 * resprotect is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * As an exception, all classes which do not reference GPL licensed code
 * are hereby licensed under the GNU Lesser Public License, as described
 * in Almura Development License version 1.
 *
 * resprotect is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * the GNU Lesser Public License (for classes that fulfill the exception)
 * and the Almura Development License version 1 along with this program. If not, see
 * <http://www.gnu.org/licenses/> for the GNU General Public License and
 * the GNU Lesser Public License.
 */
package com.almuramc.resprotect;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class EntityTradeListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
		if(event.isCancelled())
			return;
		Entity npc = event.getRightClicked();
		if(npc.getType() == EntityType.VILLAGER){
			ClaimedResidence res = Residence.getResidenceManager().getByLoc(npc.getLocation());
			if (res != null) {
				if(!res.getPermissions().playerHas(event.getPlayer().getName(), "npctrade", true)) {
				    if (Residence.isResAdminOn(event.getPlayer())) {
				        if (ResProtectConfiguration.debug) {
				            event.getPlayer().sendMessage("[" + ChatColor.LIGHT_PURPLE + "ResProtect" + ChatColor.WHITE + "] - Allowed [NPCTrade] in this area because your an [ADMIN].");
				        }
                        return;
                    }
				    event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [NPCTrade] residence flag permission required.");
					event.setCancelled(true);
				}
			}
		}
	}
}