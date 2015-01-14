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
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class EntityDamageListener implements Listener {
    public Player pdamager;
    
    @EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Entity ent = event.getEntity();
		ClaimedResidence area = Residence.getResidenceManager().getByLoc(ent.getLocation());
		
		if (area != null) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent attackevent = (EntityDamageByEntityEvent) event;
				Entity damager = attackevent.getDamager();
				
				@SuppressWarnings("unused")
				ClaimedResidence srcarea = null;
				if (damager != null) {
					srcarea = Residence.getResidenceManager().getByLoc(damager.getLocation());
				}
				
				ent = attackevent.getEntity();

				if ((ent instanceof Villager && damager instanceof Player)
						|| ((ent instanceof Villager)
								&& damager instanceof Arrow && (((Arrow) damager)
										.getShooter() instanceof Player))) {
					if (damager instanceof Arrow) {
						pdamager = (Player) ((Arrow) damager).getShooter();
					} else {
						pdamager = (Player) damager;
					}
					if (!area.getPermissions().playerHas(
							pdamager.getName().toString(), "mayor", true)) {
						Player attacker = null;
						if (damager instanceof Player) {
							attacker = (Player) damager;
							attacker.sendMessage(ChatColor.GREEN
									+ "[ResProtect]"
									+ ChatColor.WHITE
									+ " You are not allowed to harm Villagers here.");
						} else {
							if (damager instanceof Arrow)
								attacker = (Player) ((Arrow) damager)
								.getShooter();
							attacker.sendMessage(ChatColor.GREEN
									+ "[ResProtect]"
									+ ChatColor.WHITE
									+ " You are not allowed to harm Villagers here.");
						}
						event.setCancelled(true);
					}
				}
				if ((ent instanceof Animals && damager instanceof Player)
						|| ((ent instanceof Animals)
								&& damager instanceof Arrow && (((Arrow) damager)
										.getShooter() instanceof Player))) {
					if (damager instanceof Arrow) {
						pdamager = (Player) ((Arrow) damager).getShooter();
					} else {
						pdamager = (Player) damager;
					}
					if (!area.getPermissions().playerHas(
							pdamager.getName().toString(), "butcher", true)) {
						Player attacker = null;
						if (damager instanceof Player) {
							attacker = (Player) damager;
							attacker.sendMessage(ChatColor.GREEN
									+ "[ResProtect]"
									+ ChatColor.WHITE
									+ " You are not allowed to to harm Animals here.");
						} else {
							if (damager instanceof Arrow)
								attacker = (Player) ((Arrow) damager)
								.getShooter();
							attacker.sendMessage(ChatColor.GREEN
									+ "[ResProtect]"
									+ ChatColor.WHITE
									+ " You are not allowed to to harm Animals here.");
						}
						event.setCancelled(true);
					}
				}
			}
		}
	}
}