package com.almuramc.resprotect;

import org.bukkit.ChatColor;

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

    @EventHandler(priority = EventPriority.HIGH) // Do not use ignoreCancelled = true because Forge cancels click air events by default, how stupid.
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand() != null) {

            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());
            boolean hasPermission = false;

            if (res != null && perms != null) {
                switch (event.getPlayer().getItemInHand().getType().name().toUpperCase()) {
                    // Build / Place
                    case "TCONSTRUCT_THROWINGKNIFE":
                    case "TCONSTRUCT_SHURIKEN":
                    case "TCONSTRUCT_JAVELIN":
                    case "TCONSTRUCT_SHORTBOW":
                    case "TCONSTRUCT_CROSSBOW":
                    case "TCONSTRUCT_LONGBOW":
                    case "THERMALFOUNDATION_TOOLBOWELECTRUM":
                    case "THERMALFOUNDATION_TOOLBOWINVAR":
                    case "THERMALFOUNDATION_TOOLBOWSILVER":
                    case "THERMALFOUNDATION_TOOLBOWTIN":
                    case "THERMALFOUNDATION_TOOLBOWLEAD":
                    case "THERMALFOUNDATION_TOOLBOWBRONZE":
                    case "THERMALFOUNDATION_TOOLBOWPLATINUM":
                    case "THERMALFOUNDATION_TOOLBOWCOPPER":
                        hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "pvp", true);
                        if (!hasPermission) {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [PVP] residence flag permission required.");
                            return;
                        }
                        break;
                    case "BUILDCRAFTTRANSPORT_PIPEPLUG":
                    case "BUILDCRAFTTRANSPORT_PIPEGATE":
                    case "BUILDCRAFTTRANSPORT_PIPEFACADE":
                    case "BUILDCRAFTCORE_PAINTBRUSH":
                    case "BUILDCRAFT_WRENCHITEM":
                    case "CARPENTERSBLOCKS_ITEMCARPENTERSHAMMER":
                    case "CARPENTERSBLOCKS_ITEMCARPENTERSCHISEL":
                    case "IC2_ITEMTOOLMININGLASER":
                    case "IC2_ITEMDYNAMITE":
                    case "IC2_ITEMREMOTE":
                    case "IC2_ITEMTOOLWRENCH":
                    case "IC2_PLASMALAUNCHER":
                    case "IC2_ITEMWEEDINGTROWEL":
                    case "IC2_ITEMFOAMSPRAYER":
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
                    case "MINEFACTORYRELOADED_SAFARINETJAILERFANCY":
                    case "MINEFACTORYRELOADED_SAFARINETJAILER":
                    case "MINEFACTORYRELOADED_ROCKETLAUNCHER":
                    case "MINEFACTORYRELOADED_SPYGLASS":
                    case "MINEFACTORYRELOADED_NEEDLEGUN":
                    case "MINEFACTORYRELOADED_POTATOLAUNCHER":
                    case "MINEFACTORYRELOADED_SYRINGEHEALTH":
                    case "MINEFACTORYRELOADED_SYRINGEEMPTY":
                    case "MINEFACTORYRELOADED_SYRINGESLIME":
                    case "MINEFACTORYRELOADED_SYRINGEGROWTH":
                    case "MINEFACTORYRELOADED_XPEXTRACTOR":
                    case "RAILCRAFT_TOOLCROWBAR":
                    case "RAILCRAFT_TOOLCROWBARMAGIC":
                    case "RAILCRAFT_TOOLCROWBARREINFORCED":
                    case "RAILCRAFT_TOOLCROWBARVOID":
                    case "THERMALEXPANSION_CHILLER":
                    case "THERMALEXPANSION_IGNITER":
                    case "THERMALEXPANSION_WRENCH":
                    case "THERMALEXPANSION_FLORB":
                    case "THERMALEXPANSION_PUMP":
                    case "THERMALEXPANSION_TOOLBATTLEWRENCHINVAR":
                    case "THERMALDYNAMICS_RETRIEVER":
                    case "THERMALDYNAMICS_RELAY":
                    case "THERMALDYNAMICS_FILTER":
                    case "THERMALDYNAMICS_SERVO":
                    case "THAUMCRAFT_WANDCASTING":
                    case "THAUMCRAFT_FOCUSPRIMAL":
                    case "MOCREATURES_STAFFTELEPORT":
                    case "MOCREATURES_STAFFPORTAL":
                        hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "place", perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true));
                        if (!hasPermission) {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Place] or [Build] residence flag permissions required.");
                            return;
                        }
                        break;
                        // Bucket
                    case "BUCKET":
                    case "BUILDCRAFTENERGY_BUCKETOIL":
                    case "BUILDCRAFTENERGY_BUCKETFUEL":
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
                            event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Bucket] residence flag permission required.");
                            return;
                        }
                        break;
                }
            }
        }

        if (event.getClickedBlock() != null) {
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
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Place] or [Build] residence flag permissions required.");
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
                case "RAILCRAFT_ANVIL":
                case "THERMALEXPANSION_CELL":
                case "THERMALEXPANSION_MACHINE":
                case "THERMALEXPANSION_TANK":
                case "THERMALEXPANSION_STRONGBOX":
                case "THERMALEXPANSION_DEVICE":
                case "THERMALEXPANSION_CACHE":
                case "THAUMCRAFT_BLOCKTABLE":
                case "THAUMCRAFT_BLOCKSTONEDEVICE":
                case "IC2_BLOCKMACHINE":
                case "IC2_BLOCKMACHINE2":
                case "IC2_BLOCKMACHINE3":
                case "IC2_BLOCKGENERATOR":
                case "IC2_BLOCKPERSONAL":
                case "IC2_BLOCKKINETICGENERATOR":
                case "MINEFACTORYRELOADED_MACHINE":
                case "MINEFACTORYRELOADED_MACHINEBLOCK":
                case "MINEFACTORYRELOADED_MACHINE0":
                case "MINEFACTORYRELOADED_MACHINE1":
                case "MINEFACTORYRELOADED_MACHINE2":
                case "BUILDCRAFTROBOTICS_ZONEPLAN":
                case "BUILDCRAFTROBOTICS_REQUESTER":
                case "BUILDCRAFTSILICON_LASERTABLEBLOCK":
                case "BUILDCRAFTBUILDERS_FILLERBLOCK":
                case "BUILDCRAFTBUILDERS_BUILDERBLOCK":
                case "BUILDCRAFTBUILDERS_ARCHITECTBLOCK":
                case "BUILDCRAFTBUILDERS_LIBRARYBLOCK":
                case "BUILDCRAFTFACTORY_AUTOWORKBENCHBLOCK":
                case "BUILDCRAFTFACTORY_REFINERBLOCK":
                case "BUILDCRAFTSILICON_PACKAGERBLOCK":
                case "BUILDCRAFTFACTORY_BLOCKHOPPER":
                case "TCONSTRUCT_SMELTERY":
                case "TCONSTRUCT_ARMORDRYINGRACK":
                case "TCONSTRUCT_TOOLSTATIONBLOCK":
                case "TCONSTRUCT_LAVATANK":
                case "TCONSTRUCT_SEAREDBLOCK":
                case "TCONSTRUCT_CRAFTINGSTATION":
                case "TCONSTRUCT_CRAFTINGSLAB":
                case "TCONSTRUCT_TOOLFORGEBLOCK":
                case "CARPENTERSBLOCKS_BLOCKCARPENTERSAFE":
                case "THAUMCRAFT_BLOCKJAR":
                case "IRONCHEST_BLOCKIRONCHEST":
                case "THAUMCRAFT_BLOCKCHESTHUNGRY":
                    hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true);
                    if (!hasPermission) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Container] residence flag permission required.");
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
                case "THAUMCRAFT_BLOCKARCANEDOOR":
                    hasPermission = perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true);
                    if (!hasPermission) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("[" + ChatColor.DARK_AQUA + "ResProtect" + ChatColor.WHITE + " - Your action(s) have been blocked.  [Door] residence flag permission required.");
                        return;
                    }
                    break;
            }
        }
    }
}