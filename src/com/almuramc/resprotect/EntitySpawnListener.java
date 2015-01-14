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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class EntitySpawnListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCreatureSpawn(CreatureSpawnEvent event) {	    
		if (event.isCancelled()) {
			return;
		}
		boolean found = false;
		FileConfiguration config = Main.getInstance().getConfig();	
		FlagPermissions perms = Residence.getPermsByLoc(event.getLocation());		
		Entity ent = event.getEntity();
		// Vanilla Animals / Passive Entities
		if(ent instanceof Horse || ent instanceof Bat || ent instanceof Snowman || ent instanceof IronGolem || ent instanceof Ocelot || ent instanceof Pig || ent instanceof Sheep || ent instanceof Chicken || ent instanceof Wolf || ent instanceof Cow || ent instanceof Squid || ent instanceof Villager || ent instanceof Golem || ent instanceof Squid){			
		    found = true;
		    if(!perms.has("animals", true)){
				event.setCancelled(true);
				return;
			}
		} 
		// Vanilla Mobs
		if(ent instanceof CaveSpider || ent instanceof Creeper || ent instanceof EnderDragon || ent instanceof Enderman || ent instanceof Ghast || ent instanceof Giant || ent instanceof MagmaCube || ent instanceof PigZombie || ent instanceof Silverfish || ent instanceof Skeleton || ent instanceof Slime || ent instanceof Spider || ent instanceof Witch || ent instanceof Wither || ent instanceof Zombie){
			found = true;
		    if(!perms.has("monsters", true)){
				event.setCancelled(true);
				return;
			}
		}
		// The following class instance lookups are specifically for MoCreatures
		if (config.getBoolean("EnableMoCreaturesHooks") && config.getBoolean("EnableForgeHooks")) {
			String entityName = event.getEntity().toString();						
			// Ambient			
			if (entityName.equalsIgnoreCase("MoCreatures-Ant") || entityName.equalsIgnoreCase("MoCreatures-Bee") || entityName.equalsIgnoreCase("MoCreatures-Butterfly") || entityName.equalsIgnoreCase("MoCreatures-Crab") || entityName.equalsIgnoreCase("MoCreatures-Cricket") || entityName.equalsIgnoreCase("MoCreatures-Dragonfly") || entityName.equalsIgnoreCase("MoCreatures-Fly") || entityName.equalsIgnoreCase("MoCreatures-Maggot") || entityName.equalsIgnoreCase("MoCreatures-Roach") || entityName.equalsIgnoreCase("MoCreatures-Snail") ) {				
			    found = true;
			    if(!perms.has("mo-ambient", true)){			        
					event.setCancelled(true);					
				}
			}
			
			// Aquatic			
			if (entityName.equalsIgnoreCase("MoCreatures-Dolphin") || entityName.equalsIgnoreCase("MoCreatures-Fishy") || entityName.equalsIgnoreCase("MoCreatures-JellyFish") || entityName.equalsIgnoreCase("MoCreatures-MediumFish") || entityName.equalsIgnoreCase("MoCreatures-Piranha") || entityName.equalsIgnoreCase("MoCreatures-Ray") || entityName.equalsIgnoreCase("MoCreatures-Shark") || entityName.equalsIgnoreCase("MoCreatures-SmallFish")) {				
			    found = true;
			    if(!perms.has("mo-aquatic", true)){
					event.setCancelled(true);					
				}
			} 
			
			// Monsters
			if (entityName.equalsIgnoreCase("MoCreatures-FlameWraith") || entityName.equalsIgnoreCase("MoCreatures-BigGolem") || entityName.equalsIgnoreCase("MoCreatures-Golem") || entityName.equalsIgnoreCase("MoCreatures-HellRat") || entityName.equalsIgnoreCase("MoCreatures-HorseMob") || entityName.equalsIgnoreCase("MoCreatures-MiniGolem") || entityName.equalsIgnoreCase("MoCreatures-Ogre") || entityName.equalsIgnoreCase("MoCreatures-Rat") || entityName.equalsIgnoreCase("MoCreatures-Scorpion") || entityName.equalsIgnoreCase("MoCreatures-SilverSkeleton") || entityName.equalsIgnoreCase("MoCreatures-WWolf") || entityName.equalsIgnoreCase("MoCreatures-Werewolf") || entityName.equalsIgnoreCase("MoCreatures-Wraith")) {
			    found = true;
			    if(!perms.has("mo-monsters", true)) {
					event.setCancelled(true);					
				}
			}

			// Passive
			if (entityName.equalsIgnoreCase("MoCreatures-Bear") || entityName.equalsIgnoreCase("MoCreatures-BigCat") || entityName.equalsIgnoreCase("MoCreatures-Bird") || entityName.equalsIgnoreCase("MoCreatures-Boar") || entityName.equalsIgnoreCase("MoCreatures-Bunny") || entityName.equalsIgnoreCase("MoCreatures-Crocodile") || entityName.equalsIgnoreCase("MoCreatures-Deer") || entityName.equalsIgnoreCase("MoCreatures-Duck") || entityName.equalsIgnoreCase("MoCreatures-Elephant") || entityName.equalsIgnoreCase("MoCreatures-Fox") || entityName.equalsIgnoreCase("MoCreatures-Goat") || entityName.equalsIgnoreCase("MoCreatures-Horse") || entityName.equalsIgnoreCase("MoCreatures-Kitty") || entityName.equalsIgnoreCase("MoCreatures-Komodo") || entityName.equalsIgnoreCase("MoCreatures-Mouse") || entityName.equalsIgnoreCase("MoCreatures-Ostrich") || entityName.equalsIgnoreCase("MoCreatures-PetScorpion") || entityName.equalsIgnoreCase("MoCreatures-Raccoon") || entityName.equalsIgnoreCase("MoCreatures-Snake") || entityName.equalsIgnoreCase("MoCreatures-Turkey") || entityName.equalsIgnoreCase("MoCreatures-Turtle") || entityName.equalsIgnoreCase("MoCreatures-Wyvern")) {
			    found = true;
			    if(!perms.has("mo-passive", true)) {
					event.setCancelled(true);					
				}
			} 	        		        	
		}
		if (!(found)) {
		    System.out.println("Cancelled: " + event.isCancelled() + " Name: " + event.getEntity().toString() + " Class: " + event.getEntity().getClass());
		}
	}
}