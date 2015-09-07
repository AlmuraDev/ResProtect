/*
 * This file is part of Almura Forge Bridge.
 *
 * © 2015 AlmuraDev <http://www.almuradev.com/>
 * Almura Forge Bridge is licensed under the GNU General Public License.
 *
 * Almura Forge Bridge is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Almura Forge Bridge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License. If not,
 * see <http://www.gnu.org/licenses/> for the GNU General Public License.
 */
package com.almuramc.resprotect;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResProtectConfiguration {
    public static FileConfiguration config;
    public static boolean debug = false;
    private static final List<String> BLACKLISTED_ITEMS = new ArrayList<>();
    private static final List<String> BUILD_INTERACT_ITEMS = new ArrayList<>();
    private static final List<String> PVP_INTERACT_ITEMS = new ArrayList<>();
    private static final List<String> BUCKET_INTERACT_ITEMS = new ArrayList<>();
    private static final List<String> CONTAINER_BLOCK_ITEMS = new ArrayList<>();
    private static final List<String> DOOR_BLOCK_ITEMS = new ArrayList<>();

    private ResProtectConfiguration() {}    

    public static boolean isBlacklisted(String item) {
        return BLACKLISTED_ITEMS.contains(item);
    }
    
    public static boolean isPvpInteractBlocked(String item) {
        return PVP_INTERACT_ITEMS.contains(item);
    }
    
    public static boolean isBuildInteractBlocked(String item) {
        return PVP_INTERACT_ITEMS.contains(item);
    }
    
    public static boolean isBucketInteractBlocked(String item) {
        return BUCKET_INTERACT_ITEMS.contains(item);
    }
    
    public static boolean isContainerBlockBlocked(String item) {
        return CONTAINER_BLOCK_ITEMS.contains(item);
    }
    
    public static boolean isDoorBlockBlocked(String item) {
        return DOOR_BLOCK_ITEMS.contains(item);
    }

    public static void reloadConfig() {
        if (!new File(Main.getInstance().getDataFolder(), "config.yml").exists()) {
            Main.getInstance().saveDefaultConfig();
        }
        Main.getInstance().reloadConfig();
        Main.getInstance().getLogger().info("- Configuration Reloaded");
        config = Main.getInstance().getConfig();
        debug = config.getBoolean("Debug");
        
        BLACKLISTED_ITEMS.clear();
        BUILD_INTERACT_ITEMS.clear();
        PVP_INTERACT_ITEMS.clear();
        BUCKET_INTERACT_ITEMS.clear();
        CONTAINER_BLOCK_ITEMS.clear();
        DOOR_BLOCK_ITEMS.clear();

        BLACKLISTED_ITEMS.addAll(config.getStringList("blacklisted-items"));
        for (String items : BLACKLISTED_ITEMS) {
            Main.getInstance().getLogger().info("- Blacklisted Items -> " + items);
        }
        
        Main.getInstance().getLogger().info("- Loading Build Interact Items...");
        BUILD_INTERACT_ITEMS.addAll(config.getStringList("build-interact-items"));
        for (String items : BUILD_INTERACT_ITEMS) {
            Main.getInstance().getLogger().info("- Build Interact Items -> " + items);
        }  
        
        Main.getInstance().getLogger().info("- Loading PVP Interact Items...");
        PVP_INTERACT_ITEMS.addAll(config.getStringList("pvp-interact-items"));
        for (String items : PVP_INTERACT_ITEMS) {
            Main.getInstance().getLogger().info("- PVP Interact Items -> " + items);
        }
        
        Main.getInstance().getLogger().info("- Loading Bucket Interact Items...");
        BUCKET_INTERACT_ITEMS.addAll(config.getStringList("bucket-interact-items"));
        for (String items : BUCKET_INTERACT_ITEMS) {
            Main.getInstance().getLogger().info("- Bucket Interact Items -> " + items);
        }
        
        Main.getInstance().getLogger().info("- Loading Container Block Items...");
        CONTAINER_BLOCK_ITEMS.addAll(config.getStringList("container-block-items"));
        for (String items : CONTAINER_BLOCK_ITEMS) {
            Main.getInstance().getLogger().info("- Container Block Items -> " + items);
        }
        
        Main.getInstance().getLogger().info("- Loading Door Block Items...");
        DOOR_BLOCK_ITEMS.addAll(config.getStringList("door-block-items"));
        for (String items : DOOR_BLOCK_ITEMS) {
            Main.getInstance().getLogger().info("- Door Block Items -> " + items);
        } 
        
    }
}
