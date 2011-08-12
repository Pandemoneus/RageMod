package net.rageland.ragemod.commands;

import java.util.ArrayList;

import net.rageland.ragemod.Build;
import net.rageland.ragemod.RageConfig;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.Util;
import net.rageland.ragemod.data.Factions;
import net.rageland.ragemod.data.PlayerData;
import net.rageland.ragemod.data.Players;
import net.rageland.ragemod.text.Language;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class DebugCommands 
{
	
	private RageMod plugin;
	
	public DebugCommands(RageMod plugin) 
	{
		this.plugin = plugin;
	}
	
	public void onDebugCommand(Player player, PlayerData playerData, String[] split) 
	{
		if( split.length < 2 )
		{
			plugin.text.parse(player, "Debug commands: <required> [optional]");
			if( true )
				plugin.text.parse(player, "   /debug colors   (displays all chat colors)");
			if( true )
				plugin.text.parse(player, "   /debug donation  (displays amount of donations)");
			if( true )
				plugin.text.parse(player, "   /debug sanctum <level> (attempts to build sanctum floor)");
			if( true )
				plugin.text.parse(player, "   /debug translate [text] (translates the entered text)");
			if( true )
				plugin.text.parse(player, "   /debug transcast <#> <text> (translates/broadcasts the text)");
		}
		else if( split[1].equalsIgnoreCase("colors") )
		{
			this.colors(player);
		}
		else if( split[1].equalsIgnoreCase("donation") )
		{
			this.donation(player);
		}
		else if( split[1].equalsIgnoreCase("sanctum") )
		{
			if( split.length == 3 )
				this.sanctum(player, split[2]); 
			else
    			plugin.text.parse(player, "Usage: /debug sanctum <level>"); 
		}
		else if( split[1].equalsIgnoreCase("translate") )
		{
				this.translate(player, split); 
		}
		else if( split[1].equalsIgnoreCase("transcast") )
		{
			if( split.length > 2 )
				this.transcast(player, split); 
			else
    			plugin.text.parse(player, "Usage: /debug transcast 1-4 <text>"); 
		}
		else
			plugin.text.parse(player, "Type /debug to see a list of available commands.");
	}

	// /debug colors
	private void colors(Player player) 
	{
		player.sendMessage(ChatColor.DARK_GRAY + "Dark Gray: Player (Tourist)");
		player.sendMessage(ChatColor.GRAY + "Gray: Player (Neutral)");
		player.sendMessage(ChatColor.WHITE + "White: Town (Neutral), Player (Merchant)");
		player.sendMessage(ChatColor.YELLOW + "Yellow: Player (Admin)");
		player.sendMessage(ChatColor.GOLD + "Gold: Treasury messages, Player (Owner)");
		player.sendMessage(ChatColor.RED + "Red: Town (Red), Player (Red)");
		player.sendMessage(ChatColor.DARK_RED + "Dark Red: Negative messages");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Light Purple: Battle messages");
		player.sendMessage(ChatColor.DARK_PURPLE + "Dark Purple: Important battle messages");
		player.sendMessage(ChatColor.BLUE + "Blue: Player (Blue)");
		player.sendMessage(ChatColor.DARK_BLUE + "Dark Blue: Unused (illegible)  :(");
		player.sendMessage(ChatColor.AQUA + "Aqua: NPC names, NPC towns");
		player.sendMessage(ChatColor.DARK_AQUA + "Dark Aqua: NPC speech, Quest info");
		player.sendMessage(ChatColor.GREEN + "Green: Ragemod messages");
		player.sendMessage(ChatColor.DARK_GREEN + "Dark Green: Important messages, Broadcasts, Player (Moderator)");
		
	}
	
	// /debug donation
	private void donation(Player player) 
	{
		PlayerData playerData = plugin.players.get(player.getName());
		int donation = plugin.database.playerQueries.getRecentDonations(playerData.id_Player);
		
		plugin.text.send(player, "The database records you with a total donation of $" + donation + " in the last month.");
	}

	// /debug sanctum <level>
	private void sanctum(Player player, String levelString) 
	{
		plugin.text.sendNo(player, "This command has been disabled.");
		return;
		
//		int level;
//		World world = player.getWorld();
//		PlayerData playerData = plugin.players.get(player.getName());
//		
//		try
//		{
//			level = Integer.parseInt(levelString);
//		}
//		catch( Exception ex )
//		{
//			plugin.text.messageNo(player, "Invalid level.");
//			return;
//		}
//		
//		if( level < 1 || level > 5 )
//		{
//			plugin.text.messageNo(player, "Invalid level.");
//			return;
//		}
//		
//		// Pinpoint the top-left corner
//		int cornerX = (int)player.getLocation().getX() - 2; 
//		int cornerY = (int)player.getLocation().getY() - 1;
//		int cornerZ = (int)player.getLocation().getZ() - 10; 
//		
//		Build.sanctumFloor(plugin, world, cornerX, cornerY, cornerZ, level, playerData.id_Faction);
		
	}
	
	// Translates the text typed by the player
	private void translate(Player player, String[] split) 
	{
		String message = new String();
		ArrayList<String> results;
		
		if( split.length == 2 )
		{
			plugin.text.parse(player, "Usage: /debug translate [text]");
			plugin.text.parse(player, "Translating sample message...");
			message = "Greetings, fellow traveler.  Would you like a cup of ale?";
		}
		else
		{
			// Pull the commands out of the string
			for( int i = 2; i < split.length; i++ )
				message += split[i] + " ";
		}
		
		try
		{
			plugin.text.parse(player,  "[En] " + message);
			
			// Translate into all 4 languages
			for( int i = 1; i <= 4; i++ )
			{
				results = plugin.languages.translate(message, i);
				plugin.text.parse(player,  "[" + plugin.languages.getAbbreviation(i) + "] " + results.get(3), ChatColor.DARK_AQUA);
			}
		}
		catch( Exception ex )
		{
			plugin.text.send(player, "Error: " + ex.getMessage());
		}
	}
	
	// Translates and broadcasts the text typed by the player
	private void transcast(Player player, String[] split) 
	{
		PlayerData playerData = plugin.players.get(player.getName());
		String message = new String();
		ArrayList<String> results;
		int id_Language;
		
		try
		{
			id_Language = Integer.parseInt(split[2]);
		}
		catch( Exception ex )
		{
			plugin.text.sendNo(player, "Invalid language (1-4).");
			return;
		}
		if( id_Language < 1 || id_Language > 4 )
		{
			plugin.text.sendNo(player, "Invalid language (1-4).");
			return;
		}
		
		// Pull the commands out of the string
		for( int i = 3; i < split.length; i++ )
			message += split[i] + " ";
		
		results = plugin.languages.translate(message, id_Language);
		plugin.text.broadcast(playerData.getCodedName() + " has initiated a translation test:");
		plugin.text.broadcast("[En] " + message, ChatColor.GREEN);
		
		try
		{
			plugin.text.broadcast("[" + plugin.languages.getAbbreviation(id_Language) + "] " + results.get(3), ChatColor.DARK_AQUA);
		}
		catch( Exception ex )
		{
			plugin.text.send(player, "Error: " + ex.getMessage());
		}
	}

}
