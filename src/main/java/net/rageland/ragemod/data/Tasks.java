package net.rageland.ragemod.data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.iConomy.iConomy;
import com.iConomy.system.Holdings;

import net.rageland.ragemod.RageConfig;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.RageZones;
import net.rageland.ragemod.Util;

// TODO: Use the bukkit scheduler - the current method will not work with java.Timer

public class Tasks {
		
	private HashMap<String, Timestamp> tasks;
	private RageMod plugin;
	
	public Tasks(RageMod plugin)
	{
		this.plugin = plugin;
		
	}
	
	
	// On startup, pull all records of when tasks ran last
	public void loadTaskTimes()
	{
		tasks = plugin.database.taskQueries.loadTaskTimes();
		if(tasks == null)
			tasks = new HashMap<String, Timestamp>();
	}
	
	// Log task as complete
	public void setComplete(String taskName)
	{
		tasks.put(taskName, Util.now());
		plugin.database.taskQueries.setComplete(taskName);
	}
	
	// Get number of seconds since last task run
	public int getSeconds(String taskName)
	{
		// If we have a null value, the task has never been run, so return a huge value to force it to run
		if( !tasks.containsKey(taskName) || tasks.get(taskName) == null )
			return Integer.MAX_VALUE;
		else
			return (int)((Util.now().getTime() - tasks.get(taskName).getTime()) / 1000);
	}
	
	// *** TASK CODE ***
	
	// Charge taxes for player towns
	public void processTownUpkeeps()
	{
		double remaining;
		double cost = plugin.config.Town_UPKEEP_PER_PLAYER;
		Holdings holdings;
		PlayerData playerData;
		
		if( plugin.config.DISABLE_NON_LOT_CODE )
			return;
		
		System.out.println("Beginning town upkeep processing...");
		
		for( PlayerTown town : plugin.playerTowns.getAll() )
		{
			// Set the total amount needed to be collected
			remaining = town.getLevel().upkeepCost;
			
			// Move through each town resident to collect money
			for( String playerName : plugin.database.townQueries.listTownResidents(town.townName))
			{
				holdings = iConomy.getAccount(playerName).getHoldings();
				playerData = plugin.players.get(playerName);
				
				// Attempt to collect from player's iConomy balance first
				if( holdings.hasEnough(cost) )
				{
					holdings.subtract(cost);
					remaining -= cost;
				}
				// If they don't have enough on hand, see if they have deposits in the treasury
				else if( playerData.treasuryBalance >= cost )
				{
					playerData.treasuryBalance -= cost;
					town.treasuryBalance -= cost;
					remaining -= cost;
					plugin.players.update(playerData);
					plugin.database.playerQueries.updatePlayer(playerData);
				}
				// If the player doesn't have the funds in either area, evict their freeloading ass
				else if( !playerData.isMayor )
				{
					playerData.townName = "";
					playerData.spawn_IsSet = false;
					playerData.treasuryBalance = 0;
					plugin.players.update(playerData);
					
					// TODO: Inform the player of their eviction somehow
				}
			}
			
			// At this point we will have collected an amount of money less, equal to, or more than the town's upkeep
			town.treasuryBalance -= remaining;
			
			// If the treasury balance is in the negative, the town is bankrupt
			if( town.treasuryBalance < 0 )
			{
				if( town.bankruptDate == null )
				{
					System.out.println("The town of " + town.townName + " is now bankrupt.");
					town.bankruptDate = Util.now();
				}
				else
				{
					// At this point the town has been bankrupt for some time - delete after specified time
					if( Util.daysBetween(Util.now(), town.bankruptDate) > plugin.config.Town_MAX_BANKRUPT_DAYS )
					{
						// TODO: Finish writing this
					}
				}
				
			}
			else
			{
				// TODO: Set bankrupt date null
			}
			
			plugin.playerTowns.put(town);	
		}
	}

	// Fill a specified area with sand for a public mine
	public void processFillSandlot(RageMod plugin) 
	{
		World world = plugin.getServer().getWorld("world");
		Random random = new Random();
		
		System.out.println("Refilling sand lot...");
		
		// Look for players who are currently in the area and evacuate them
		for( Player player : plugin.getServer().getOnlinePlayers() )
		{
			if( plugin.zones.isInSandlot(player.getLocation()) )
			{
				Util.message(player, "Automatically refilling sand lot - get out of the way!");
				player.teleport(world.getSpawnLocation());
			}
		}
		
		Block currentBlock;
		
		for( int x = (int)RageZones.Capitol_SandLot.nwCorner.getX(); x <= (int)RageZones.Capitol_SandLot.seCorner.getX(); x++ )
		{
			for( int y = (int)RageZones.Capitol_SandLot.nwCorner.getY(); y <= (int)RageZones.Capitol_SandLot.seCorner.getY(); y++ )
			{
				for( int z = (int)RageZones.Capitol_SandLot.nwCorner.getZ(); z >= (int)RageZones.Capitol_SandLot.seCorner.getZ(); z-- )
				{
					currentBlock = world.getBlockAt(x, y, z);
					if( random.nextInt( plugin.config.Capitol_SANDLOT_GOLD_ODDS ) == 0 )
						currentBlock.setType(Material.GOLD_ORE);
					else if( random.nextInt( plugin.config.Capitol_SANDLOT_DIAMOND_ODDS ) == 0 ) 
						currentBlock.setType(Material.DIAMOND_ORE);
					else
						currentBlock.setType(Material.SAND);
				}
			}
		}
		
	}
	
	
}

