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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class SnowListener implements Listener {

	// Melt Block Events
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockMelt(BlockFadeEvent event) {
		if (event.isCancelled()) {
			return;
		}
		ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
		if (res != null) {
			if (!res.getPermissions().has("melt", true)) {
				event.setCancelled(true);
			}
		
		} else if (!Residence.getWorldFlags().getPerms(event.getBlock().getWorld().getName()).has("melt", true)) {
			event.setCancelled(true);
		}
	}

	// Snow Formation Events
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockForm(BlockFormEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (Main.getInstance().getConfig().getBoolean("PreventAllSnowForming")) {
			event.setCancelled(true);
			return;
		}
		ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());
		if (res != null) {
			if (!res.getPermissions().has("form", true)) {
				event.setCancelled(true);
			}

		} else if (!Residence.getWorldFlags().getPerms(event.getBlock().getWorld().getName()).has("form", true)) {
			 event.setCancelled(true);
		}
	}

}