package com.almuramc.resprotect;

import org.bukkit.event.EventPriority;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
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

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {    
        // Prevents Tesseract from being placed in Othala.
        if (!event.getBlockPlaced().getWorld().getName().equalsIgnoreCase("Othala")) return;        
        if (event.getBlockPlaced().getType() == Material.getMaterial("THERMALEXPANSION_TESSERACT")) {
            event.setCancelled(true);
            System.out.println("[ResProtect] - Prevented Block Placement: Tesseract by: " + event.getPlayer().getName() + " in world: " + event.getBlockPlaced().getWorld().getName());
        }
    }

    @EventHandler(priority = EventPriority.HIGH) // Do not use ignoreCancelled = true because Forge cancels click air events by default, how stupid.
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() != null) {

            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            boolean hasPermission = false;
            
            if (res != null && perms != null) {
                switch (event.getPlayer().getItemInHand().getType().name().toUpperCase()) {
                    // Build / Place
                    case "BUILDCRAFTTRANSPORT_PIPEPLUG":
                    case "BUILDCRAFTTRANSPORT_PIPEGATE":
                    case "BUILDCRAFTTRANSPORT_PIPEFACADE":
                    case "BUILDCRAFT_WRENCHITEM":
                    case "IC2_ITEMTOOLMININGLASER":
                    case "IC2_ITEMDYNAMITE":
                    case "IC2_ITEMREMOTE":
                    case "IC2_ITEMTOOLWRENCH":
                    case "IC2_PLASMALAUNCHER":
                    case "IC2_ITEMTOOLPAINTER":
                    case "IC2_ITEMTOOLPAINTERBLACK":
                    case "IC2_ITEMTOOLPAINTERRED":
                    case "IC2_ITEMTOOLPAINTERGREEN":
                    case "IC2_ITEMTOOLPAINTERBROWN":
                    case "IC2_ITEMTOOLPAINTERBLUE":
                    case "IC2_ITEMTOOLPAINTERPURPLE":
                    case "IC2_ITEMTOOLPAINTERCYAN":
                    case "IC2_ITEMTOOLPAINTERLIGHTGREY":
                    case "IC2_ITEMTOOLPAINTERDARKGREY":
                    case "IC2_ITEMTOOLPAINTERPINK":
                    case "IC2_ITEMTOOLPAINTERLIME":
                    case "IC2_ITEMTOOLPAINTERYELLOW":
                    case "IC2_ITEMTOOLPAINTERCLOUD":
                    case "IC2_ITEMTOOLPAINTERMAGENTA":
                    case "IC2_ITEMTOOLPAINTERORANGE":
                    case "IC2_ITEMTOOLPAINTERWHITE":
                    case "MINEFACTORYRELOADED_SAFARINETREUSABLE":
                    case "MINEFACTORYRELOADED_SAFARINETSINGLEUSE":
                    case "THERMALEXPANSION_CHILLER":
                    case "THERMALEXPANSION_IGNITER":
                    case "THERMALEXPANSION_WRENCH":
                    case "THERMALEXPANSION_FLORB":
                    case "THAUMCRAFT_WANDCASTING":
                        hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "place", perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true));
                        if (!hasPermission) {
                            event.setCancelled(true);
                            return;
                        }
                    break;
                    // Bucket
                    case "BUCKET":
                    case "BUILDCRAFTENERGY_BUCKETOIL":
                    case "THAUMCRAFT_ITEMBUCKETDEATH":
                    case "THAUMCRAFT_ITEMBUCKETPURE":
                    case "RAILCRAFT_FLUIDCREOSOTEBUCKET":
                    case "MINEFACTORYRELOADED_BUCKETSEWAGE":
                    case "MINEFACTORYRELOADED_BUCKETESSENCE":
                    case "MINEFACTORYRELOADED_BUCKETMEAT":
                    case "MINEFACTORYRELOADED_BUCKETPINKSLIME":
                    case "MINEFACTORYRELOADED_BUCKETCHOCOLATEMILK":
                    case "MINEFACTORYRELOADED_BUCKETMUSHROOMSOUP":
                    case "MINEFACTORYRELOADED_BUCKETBIOFUEL":
                    case "MINEFACTORYRELOADED_BUCKETSLUDGE":
                    case "THERMALFOUNDATION_BUCKET":
                    case "TCONSTRUCT_BUCKETS":
                        hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true);
                        if (!hasPermission) {
                            event.setCancelled(true);
                            return;
                        }
                    break;
                }
            }
        }

        // Travelers Chest block within Othala
        if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType() == Material.getMaterial("THAUMCRAFT_TRUNKSPAWNER")) {
            if (event.getPlayer().getWorld().getName().equalsIgnoreCase("Othala")) {
                System.out.println("[ResProtect] - Blocked Thaumcraft Traveling Chest from: " + event.getPlayer().getName() + " in world: " + event.getPlayer().getWorld().getName());
                event.setCancelled(true);
            }
        }

        if (event.getClickedBlock() == null) return;
        // Tesseract Block within Othala.
        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_TESSERACT") && event.getClickedBlock().getWorld().getName().equalsIgnoreCase("Othala")) {
            event.setCancelled(true);
            System.out.println("[ResProtect] - Blocked Tesseract from: " + event.getPlayer().getName() + " in world: " + event.getPlayer().getWorld().getName());
        }
        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getClickedBlock().getLocation(), event.getPlayer());
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
        boolean hasPermission = false;
        if (res == null || perms == null) return;
        switch (event.getClickedBlock().getType().name().toUpperCase()) {
            // Build
            case "BIBLIOCRAFT_BIBLIOFANCYSIGN":
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "place", perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true));
                if (!hasPermission) {
                    event.setCancelled(true);
                    return;
                }
            break;
            // Container
            case "ALMURA_STORAGEBIRCH_STORAGE_CRATE":
            case "ALMURA_STORAGEDARK_OAK_STORAGE_CRATE":
            case "ALMURA_STORAGEDOCK_CHEST":
            case "ALMURA_STORAGEQUANTUM_CHEST":
            case "ALMURA_STORAGEOAK_STORAGE_CRATE":
            case "ALMURA_STORAGESPRUCE_STORAGE_CRATE":
            case "BIBLIOCRAFT_BIBLIOTABLE":
            case "BIBLIOCRAFT_BIBLIOCASE":
            case "BIBLIOCRAFT_BIBLIORACK":
            case "BIBLIOCRAFT_BIBLIOSHELF":
            case "BIBLIOCRAFT_ARMOR_STAND":
            case "BIBLIOCRAFT_BIBLIOSWORDPEDESTAL":
            case "BIBLIOCRAFT_BIBLIOLABEL":
            case "RAILCRAFT_MACHINEGAMMA": 
            case "RAILCRAFT_MACHINEALPHA":
            case "RAILCRAFT_MACHINEBETA":
            case "THERMALEXPANSION_CELL":
            case "THERMALEXPANSION_MACHINE":
            case "THERMALEXPANSION_TANK":
            case "THERMALEXPANSION_STRONGBOX":
            case "THERMALEXPANSION_CACHE":
            case "THAUMCRAFT_BLOCKTABLE":
            case "THAUMCRAFT_BLOCKSTONEDEVICE":
            case "IC2_BLOCKMACHINE":
            case "IC2_BLOCKMACHINE2":
            case "IC2_BLOCKGENERATOR":
            case "IC2_BLOCKKINETICGENERATOR":
            case "TCONSTRUCT_SMELTERY":
            case "TCONSTRUCT_ARMORDRYINGRACK":
            case "TCONSTRUCT_TOOLSTATIONBLOCK":
            case "TCONSTRUCT_LAVATANK":
            case "TCONSTRUCT_SEAREDBLOCK":
            case "TCONSTRUCT_CRAFTINGSTATION":
            case "TCONSTRUCT_CRAFTINGSLAB":
            case "CARPENTERSBLOCKS_BLOCKCARPENTERSAFE":
            case "THAUMCRAFT_BLOCKJAR":
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true);
                if (!hasPermission) {
                    event.setCancelled(true);
                    return;
                }
                break;
            // Doors
            case "MALISISDOORS_SALOON":
            case "MALISISDOORS_DOOR_ACACIA":
            case "MALISISDOORS_DOOR_JUNGLE":
            case "MALISISDOORS_DOOR_BIRCH":
            case "MALISISDOORS_DOOR_SPRUCE":
            case "MALISISDOORS_DOOR_DARK_OAK":
            case "MALISISDOORS_SHOJI_DOOR":
            case "MALISISDOORS_WOOD_SLIDING_DOOR":
            case "MALISISDOORS_FACTORY_DOOR":
            case "MALISISDOORS_SLIDING_TRAPDOOR":
            case "MALISISDOORS_CURTAIN":
                hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true);
                if (!hasPermission) {
                    event.setCancelled(true);
                    return;
                }
            break;
        }
    }
}