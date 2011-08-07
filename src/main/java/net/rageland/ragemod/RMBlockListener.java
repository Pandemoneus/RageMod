package net.rageland.ragemod;

import java.util.HashMap;

import net.rageland.ragemod.data.Lot;
import net.rageland.ragemod.data.Lots;
import net.rageland.ragemod.data.PlayerData;
import net.rageland.ragemod.data.PlayerTown;
import net.rageland.ragemod.data.PlayerTowns;
import net.rageland.ragemod.data.Players;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

/**
 * RageMod block listener
 * @author TheIcarusKid
 */
public class RMBlockListener extends BlockListener 
{
    private final RageMod plugin;

    public RMBlockListener(final RageMod plugin) 
    {
        this.plugin = plugin;
    }

    // Prevent block breaking without permission
    public void onBlockBreak(BlockBreakEvent event) 
    {
    	Player player = event.getPlayer();
    	PlayerData playerData = plugin.players.get(player.getName());
    	Block block = event.getBlock();
    	
    	if (event.isCancelled()) 
        {
            return;
        }

    	// Perform generic edit permission handling
    	if( !canEditBlock(event, player) )
    	{
    		event.setCancelled(true);
    		return;
    	}
    	
    	// *** DISABLE ALL FURTHER CODE FOR LOT RELEASE ***
    	if( plugin.config.DISABLE_NON_LOT_CODE )
    		return;
    	
    	
    	
    	
    	
    	
    	// Bed breaking - clear spawn and home
    	if( block.getType() == Material.BED_BLOCK )
    	{
    		// /home: bed inside capitol lot
			if( plugin.zones.isInZoneA(block.getLocation()) )
			{
				for( Lot lot : playerData.lots )
				{
					if( lot.canSetHome() && lot.isInside(block.getLocation()) )
					{
						playerData.clearHome();
		    			plugin.text.message(player, "You no longer have a home point.");
		    			// Update both memory and database
		    			plugin.database.playerQueries.updatePlayer(playerData);
					}
				}
			}
			// /spawn: for beds in player towns
			else if( plugin.zones.isInZoneB(block.getLocation()) )
			{
				PlayerTown playerTown = plugin.playerTowns.getCurrentTown(block.getLocation());

	    		if( playerTown != null )
	    		{
	    			if( playerData.getSpawn() != null && playerData.getSpawn().distance(block.getLocation()) < plugin.config.Town_DISTANCE_BETWEEN_BEDS )
	    			{
		    			plugin.text.message(player, "You no longer have a spawn point.");
	    				playerData.clearSpawn();
		    			playerData.update();
	    			}
	    			else
	    			{
	    				plugin.text.message(player, "You can only move your own bed.");
	    				event.setCancelled(true);
	    			}
	    		}
			}
    	}
    }
    
    // Prevent block placing without permission
    public void onBlockPlace(BlockPlaceEvent event) 
    {
    	Player player = event.getPlayer();
    	PlayerData playerData = plugin.players.get(player.getName());
    	Block block = event.getBlock();
    	Location location = block.getLocation();
    	
    	if (event.isCancelled()) 
        {
            return;
        }
    	
    	// Perform generic edit permission handling
    	if( !canEditBlock(event, player) )
    	{
    		event.setCancelled(true);
    		return;
    	}
    	
    	// *** DISABLE ALL FURTHER CODE FOR LOT RELEASE ***
    	if( plugin.config.DISABLE_NON_LOT_CODE )
    		return;
    	
    	// *** ZONE A (Neutral Zone) ***
    	if( plugin.zones.isInZoneA(location) )
    	{
    		// Bed placement - set spawn and home
        	if( block.getType() == Material.BED_BLOCK )
        	{
        		// /home: bed inside capitol lot
    			if( playerData.isMember )
    			{
    				for( Lot lot : playerData.lots )
    				{
    					if( lot.canSetHome() && lot.isInside(block.getLocation()) )
    					{
    						if( playerData.getHome() != null )
    						{
    							plugin.text.message(player, "You already have a bed inside your lot (use /home clear to fix errors).");
    							event.setCancelled(true);
    							return;
    						}
    						
    						Location loc = block.getLocation();
    						playerData.setHome(new Location(loc.getWorld(), loc.getX(), loc.getY() + 2, loc.getZ()));
    		    			plugin.text.message(player, "Your home location has now been set.");
    		    			// Update both memory and database
    		    			playerData.update();
    					}
    				}
    			}
        	}	
    	}
    	// *** ZONE B (War Zone) ***
    	else if( plugin.zones.isInZoneB(location) )
    	{
    		// /spawn: for beds in player towns
			if( block.getType() == Material.BED_BLOCK )
			{
				PlayerTown playerTown = plugin.playerTowns.getCurrentTown(block.getLocation());

	    		if( playerTown != null && playerTown.townName.equals(playerData.townName) )
	    		{
	    			if( playerData.getSpawn() != null )
					{
						plugin.text.message(player, "You already have a bed inside your town (use /spawn clear to fix errors).");
						event.setCancelled(true);
						return;
					}
	    			
	    			// Make sure the location is not too close to another player's spawn
	    			HashMap<String, Location> spawns = plugin.database.playerQueries.getSpawnLocations(playerTown.id_PlayerTown);
	    			for( String resident : spawns.keySet() )
	    			{
	    				if( block.getLocation().distance(spawns.get(resident)) < plugin.config.Town_DISTANCE_BETWEEN_BEDS && !resident.equals(playerData.name) )
	    				{
	    					plugin.text.parse(player, "This bed is too close to " + resident + "'s bed - spawn not set.");
	    					event.setCancelled(true);
	    					return;
	    				}
	    			}
	    			
	    			Location loc = block.getLocation();
	    			playerData.setSpawn(new Location(loc.getWorld(), loc.getX(), loc.getY() + 2, loc.getZ()));
	    			plugin.text.parse(player, "Your spawn location has now been set.");
	    			// Update both memory and database
	    			plugin.database.playerQueries.updatePlayer(playerData);
	    		}
			}
    	}
        	
    	
    }
    
    // Generic permission edit handler that handles multiple types of block editing
    private boolean canEditBlock(BlockEvent event, Player player)
    {
    	PlayerData playerData = plugin.players.get(player.getName());
    	Block block = event.getBlock();
    	Location location = block.getLocation();
    	
    	// *** ZONE A (Neutral Zone) ***
    	// See if player is in capitol
    	if( plugin.zones.isInZoneA(location) && plugin.zones.isInCapitol(location) )
    	{
    		// If the player is below y=22, make sure they have permission to mine below city (don't check lots)
    		if( location.getY() < 22 && !RageMod.permissionHandler.has(player, "ragemod.mine.capitol"))
    		{
    			plugin.text.parse(player, "You don't have permission to mine under the city.");
    			return false;
    		}
    		// See if the player is inside a lot, and if they own it
    		else if( !playerData.isInsideLot(location) && !RageMod.permissionHandler.has(player, "ragemod.build.anylot") )
    		{
    			Lot lot = plugin.lots.findCurrentLot(location);
    			
    			if( lot == null && !RageMod.permissionHandler.has(player, "ragemod.build.capitol") )
    				plugin.text.message(player, "You don't have permission to edit city infrastructure.");
    			else
    			{
    				if( lot.owner.equals("") )
    					plugin.text.message(player, "You cannot edit unclaimed lots.");
    				else
    				{
    					// Check to see if the player has permission to build in this lot
    					PlayerData ownerData = plugin.players.get(lot.owner);
    					if( !ownerData.lotPermissions.contains(playerData.name) )
    						plugin.text.parse(player, "This lot is owned by " + lot.owner + ".");
    					else
    						return true;
    				}
    			}
    			
    			return false;
    		}
    	}
    	// *** ZONE B (War Zone) ***
    	else if( plugin.zones.isInZoneB(location) )
    	{
    		PlayerTown playerTown = plugin.playerTowns.getCurrentTown(location);
    		
    		if( playerTown != null )
    		{
        		// Players can only build inside their own towns
    			if( !playerTown.townName.equals(playerData.townName) && !RageMod.permissionHandler.has(player, "ragemod.build.anytown") ) 
    			{		
    				plugin.text.message(player, "You can only build inside of your own town.");
    				return false;
    			}
    			else if( playerTown.isInsideSanctumFloor(location) )
    			{
    				plugin.text.message(player, "You cannot modify the inner sanctum floor.");
    				return false;
    			}
    			else if( playerTown.isInsideSanctum(location) )
    			{
    				if( block.getType() != Material.TORCH && block.getType() != Material.GOLD_BLOCK ) 
    				{
    					plugin.text.message(player, "Only torches and gold blocks can be placed inside the inner sanctum.");
        				return false;
    				}
    				else if( block.getType() == Material.GOLD_BLOCK )
    				{
    					return playerTown.processGoldBlock(event);
    				}	
    			}
    		}
    	}
    	// *** TRAVEL ZONE ***
    	else if( plugin.zones.isInTravelZone(location) )
    	{
    		plugin.text.message(player, "You cannot build inside the Travel Zone.");
    		return false;
    	}
    	
    	return true;
    }
    
    

}
