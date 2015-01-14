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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class SoilListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockFade(BlockFadeEvent event) {
		final Block fading = event.getBlock();
		//Don't do a lookup if it isn't soil.
		if (fading.getType() != Material.SOIL) {
			return;
		}

		FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
		if (perms.has("soil", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEntityInteract(EntityInteractEvent event) {
		final Block block = event.getBlock();
		if (block.getType() != Material.SOIL) {
			return;
		}

		FlagPermissions perms = Residence.getPermsByLoc(event.getBlock().getLocation());
		if (perms.has("soil", true)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();

		if (block.getType() != Material.SOIL || event.getAction() != Action.PHYSICAL) {
			return;
		}				 
		
		FlagPermissions perms = Residence.getPermsByLoc(block.getLocation());

		if (perms.has("soil", true)) {
			event.setCancelled(true);
		}		
	}	 
}