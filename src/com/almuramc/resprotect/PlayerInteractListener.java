package com.almuramc.resprotect;

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

    @EventHandler
    public void onSignChange(SignChangeEvent event) {        
        if (event.getBlock() == null) return;

        FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getBlock().getLocation(), event.getPlayer());
        ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getBlock().getLocation());

        if (res != null) return;

        if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {    
        // Prevents Tesseract from being placed in Othala.
        if (!event.getBlockPlaced().getWorld().getName().equalsIgnoreCase("Othala")) return;        
        if (event.getBlockPlaced().getType() == Material.getMaterial("THERMALEXPANSION_TESSERACT")) {
            event.setCancelled(true);
            System.out.println("[ResProtect] - Prevented Block Placement: Tesseract by: " + event.getPlayer().getName() + " in world: " + event.getBlockPlaced().getWorld().getName());
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getPlayer().getItemInHand() != null) {

            FlagPermissions perms = Residence.getPermsByLocForPlayer(event.getPlayer().getLocation(), event.getPlayer());
            ClaimedResidence res = Residence.getResidenceManager().getByLoc(event.getPlayer().getLocation());

            if (res != null && perms != null) {
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("THERMALEXPANSION_FLORB")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "destroy", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("BUILDCRAFTENERGY_BUCKETOIL")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("THAUMCRAFT_ITEMBUCKETDEATH")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("THAUMCRAFT_ITEMBUCKETPURE")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("RAILCRAFT_FLUIDCREOSOTEBUCKET")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETSEWAGE")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETESSENCE")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETMEAT")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETSEWAGE")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETPINKSLIME")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETCHOCOLATEMILK")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETMUSHROOMSOUP")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETBIOFUEL")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("MINEFACTORYRELOADED_BUCKETSLUDGE")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("THERMALFOUNDATION_BUCKET")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                
                if (event.getPlayer().getItemInHand().getType() == Material.getMaterial("TCONSTRUCT_BUCKETS")) {
                    if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "bucket", true)) {
                        event.setCancelled(true);
                    }
                    return;
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

        if (res == null || perms == null) return;        

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOFANCYSIGN")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "build", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOTABLE")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOCASE")) {      
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIORACK")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOSHELF")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_ARMOR_STAND")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOSWORDPEDESTAL")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("BIBLIOCRAFT_BIBLIOLABEL")) {            
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("RAILCRAFT_MACHINEGAMMA")) {           
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {             
                event.setCancelled(true);
                return;
            }           
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("RAILCRAFT_MACHINEALPHA")) {           
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {             
                event.setCancelled(true);
                return;
            }           
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("RAILCRAFT_MACHINEBETA")) {           
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {             
                event.setCancelled(true);
                return;
            }           
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_CELL")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_MACHINE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_TANK")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_STRONGBOX")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THERMALEXPANSION_CACHE")) {                        
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("THAUMCRAFT_BLOCKTABLE")) {                        
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKMACHINE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKMACHINE2")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKGENERATOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("IC2_BLOCKKINETICGENERATOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_SMELTERY")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_ARMORDRYINGRACK")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_TOOLSTATIONBLOCK")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_LAVATANK")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_SEAREDBLOCK")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_CRAFTINGSTATION")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("TCONSTRUCT_CRAFTINGSLAB")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("CARPENTERSBLOCKS_BLOCKCARPENTERSAFE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "container", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMSALOON")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMDOOR_ACACIA")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMDOOR_JUNGLE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMDOOR_BIRCH")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMDOOR_SPRUCE")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMSHOJI_DOOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMWOOD_SLIDING_DOOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMFACTORY_DOOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_SLIDING_TRAPDOOR")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
        
        if (event.getClickedBlock().getType() == Material.getMaterial("MALISISDOORS_ITEMCURTAIN")) {
            if (!perms.playerHas(event.getPlayer().getName(), event.getPlayer().getWorld().getName(), "door", true)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}