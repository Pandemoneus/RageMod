package net.rageland.ragemod;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

// Misc. methods
public class Util 
{	
	// Formats the cooldown time into a lovely string
	public static String formatCooldown(int totalSeconds)
	{
		int minutes, seconds;
		
		minutes = totalSeconds / 60;
		seconds = totalSeconds % 60;
		
		java.text.DecimalFormat nft = new java.text.DecimalFormat("#00.###");
		nft.setDecimalSeparatorAlwaysShown(false);
		
		return minutes + ":" + nft.format(seconds);
	}
	
	// Returns the current time
	public static Timestamp now()
	{
		Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		return now;
	}
	
	// Returns the number of days between the two Timestamps
	public static int daysBetween(Timestamp time1, Timestamp time2)
	{
		return (int)((time1.getTime() - time2.getTime()) / 86400000);
	}
	
	// Returns the number of seconds since the Timestamp occurred
	public static int secondsSince(Timestamp timestamp)
	{
		return (int)((now().getTime() - timestamp.getTime()) / 1000);
	}
	
	
}
