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

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class Main extends JavaPlugin {
	private static Main instance;
	public final Logger log = Logger.getLogger("Minecraft");
	public Player pdamager;
	public FileConfiguration config; 
	public static Main getInstance() {
		return instance;
	}
	
	

	@Override
	public void onEnable() {
		instance = this;
		FileConfiguration config = this.getConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();		
		config.addDefault("EnableSnowChangeEvents", false);
		config.addDefault("PreventAllSnowForming", false);
		config.addDefault("EnableLightningDetection", true);
		config.addDefault("EnableChatListener", true);
		config.addDefault("EnableTargetListener", true);
		config.addDefault("EnableForgeHooks", false);
		config.addDefault("EnableMoCreaturesHooks", false);
		config.addDefault("EnableThaumcraftHooks", false);
		config.addDefault("EnableTConstructHooks", false);
		config.options().copyDefaults(true);
		saveConfig();

		if (pm.isPluginEnabled("Residence")) {

			if ((config.getBoolean("EnableSnowChangeEvents")) || (config.getBoolean("PreventAllSnowForming"))) {
				pm.registerEvents(new SnowListener(), this);
			}
			if (config.getBoolean("EnableLightningDetection")) {
				pm.registerEvents(new LightningListener(), this);
			}
			if (config.getBoolean("EnableChatListener")) {
				pm.registerEvents(new ChatListener(), this);
			}
			if (config.getBoolean("EnableTargetListener")) {
				pm.registerEvents(new EntityTargetListener(), this);
			}
			pm.registerEvents(new PortalListener(), this);
			pm.registerEvents(new PlayerInteractListener(), this);
			pm.registerEvents(new EggHatchListener(), this);
			pm.registerEvents(new ShearListener(), this);
			pm.registerEvents(new EntityDamageListener(), this);
			pm.registerEvents(new EntitySpawnListener(), this);
			pm.registerEvents(new EntityTradeListener(), this);
			pm.registerEvents(new EntityListener(), this);
			pm.registerEvents(new SoilListener(), this);
			registerFlags();
			log.info("All ResProtect Flags added to residence.");

		} else {
			log.severe("Residence Plugin is not loaded!");
		}
	}

	public void registerFlags() {
		FileConfiguration config = this.getConfig();	
		FlagPermissions.addFlag("butcher");
		FlagPermissions.addFlag("mayor");
		FlagPermissions.addFlag("chat");
		FlagPermissions.addFlag("stormdamage");
		FlagPermissions.addFlag("portal");
		FlagPermissions.addFlag("egghatch");
		FlagPermissions.addFlag("shear");
		FlagPermissions.addFlag("form");
		FlagPermissions.addFlag("npctrade");
		FlagPermissions.addFlag("melt");
		FlagPermissions.addFlag("fly");
		FlagPermissions.addFlag("soil");
		FlagPermissions.addFlag("safezone");

		if (config.getBoolean("EnableMoCreaturesHooks")) {
			FlagPermissions.addFlag("mo-ambient");
			FlagPermissions.addFlag("mo-aquatic");
			FlagPermissions.addFlag("mo-monsters");
			FlagPermissions.addFlag("mo-passive");
		}

		if (config.getBoolean("EnableThaumcraftHooks")) {
			FlagPermissions.addFlag("thaumcraft-monsters");
		}

		if (config.getBoolean("EnableTConstructHooks")) {
		    FlagPermissions.addFlag("tconstruct-monsters");		
		}
	}
}