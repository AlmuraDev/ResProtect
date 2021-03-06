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

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.bekvon.bukkit.residence.protection.FlagPermissions;

public class Main extends JavaPlugin {
	private static Main instance;
	public final Logger log = Logger.getLogger("Minecraft");
	public Player pdamager;	
	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		ResProtectConfiguration.reloadConfig();
		PluginManager pm = Bukkit.getServer().getPluginManager();

		if (pm.isPluginEnabled("Residence")) {

			if ((ResProtectConfiguration.config.getBoolean("EnableSnowChangeEvents")) || (ResProtectConfiguration.config.getBoolean("PreventAllSnowForming"))) {
			    Main.getInstance().getLogger().info("- enabling Snow Listener");
			    pm.registerEvents(new SnowListener(), this);
			}
			if (ResProtectConfiguration.config.getBoolean("EnableLightningDetection")) {
			    Main.getInstance().getLogger().info("- enabling Lightning Listener");
				pm.registerEvents(new LightningListener(), this);
			}
			if (ResProtectConfiguration.config.getBoolean("EnableChatListener")) {
			    Main.getInstance().getLogger().info("- enabling Chat Listener");
				pm.registerEvents(new ChatListener(), this);
			}
			if (ResProtectConfiguration.config.getBoolean("EnableTargetListener")) {
			    Main.getInstance().getLogger().info("- enabling Target Listener");
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
			pm.registerEvents(new InventoryListener(), this);
			pm.registerEvents(new PlayerEntityInteractListener(), this);
			pm.registerEvents(new BlockListener(), this);
			pm.registerEvents(new ResidenceTPListener(), this);
			pm.registerEvents(new ResidenceCreateListener(), this);
			registerFlags();
			log.info("All ResProtect Flags added to residence.");

		} else {
			log.severe("Residence Plugin is not loaded!");
		}
	}

	public void registerFlags() {
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
		FlagPermissions.addFlag("itemframe");
		FlagPermissions.addFlag("boat");

		if (ResProtectConfiguration.config.getBoolean("EnableMoCreaturesHooks")) {
		    Main.getInstance().getLogger().info("- enabling MoCreatures Hooks");
			FlagPermissions.addFlag("mo-ambient");
			FlagPermissions.addFlag("mo-aquatic");
			FlagPermissions.addFlag("mo-monsters");
			FlagPermissions.addFlag("mo-passive");
		}

		if (ResProtectConfiguration.config.getBoolean("EnableThaumcraftHooks")) {
		    Main.getInstance().getLogger().info("- enabling Thaumcraft Hooks");
			FlagPermissions.addFlag("thaumcraft-monsters");
		}

		if (ResProtectConfiguration.config.getBoolean("EnableTConstructHooks")) {
		    Main.getInstance().getLogger().info("- enabling Tinkers Construct Hooks");
		    FlagPermissions.addFlag("tconstruct-monsters");		
		}
		
		if (ResProtectConfiguration.config.getBoolean("EnableTFHooks")) {
		    Main.getInstance().getLogger().info("- enabling Thermal Foundation Hooks");
            FlagPermissions.addFlag("tf-monsters");     
        }
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("res-protect")) {
            sender.sendMessage("[ResProtect] - missing command arguments.");
            return false;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("config")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("resprotect.config")) {
                    ResProtectConfiguration.reloadConfig();
                    sender.sendMessage("[ResProtect] - Configuration Reloaded.");
                    return true;
                } else {
                    sender.sendMessage("[ResProtect] - Insufficient Permissions.");
                    return false;
                }
            } else {
                ResProtectConfiguration.reloadConfig();
                return true;
            }
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("debug")) {
            if (sender instanceof Player) {
                if (sender.hasPermission("resprotect.debug")) {
                    ResProtectConfiguration.debug = !ResProtectConfiguration.debug;
                    sender.sendMessage("[ResProtect] - Debug Logger: " + ResProtectConfiguration.debug);
                    return true;
                } else {
                    sender.sendMessage("[ResProtect] - Insufficient Permissions.");
                    return false;
                }
            } else {
                ResProtectConfiguration.reloadConfig();
                Main.getInstance().getLogger().info("[Debug] - Logger: " + ResProtectConfiguration.debug);
                return true;
            }
        }
        return false;
    }	
}