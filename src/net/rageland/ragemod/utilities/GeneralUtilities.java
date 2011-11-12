package net.rageland.ragemod.utilities;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

// Misc. methods
public class GeneralUtilities {
	/**
	 * Formats the cooldown time in a readable string.
	 * 
	 * @param totalSeconds the cooldown time in seconds
	 * @return a readable string of the cooldown time
	 */
	public static String formatCooldown(int totalSeconds) {
		int minutes, seconds;

		minutes = totalSeconds / 60;
		seconds = totalSeconds % 60;

		DecimalFormat nft = new DecimalFormat("#00.##");
		nft.setDecimalSeparatorAlwaysShown(false);

		return minutes + ":" + nft.format(seconds);
	}

	/**
	 * Returns the current time.
	 * 
	 * @return the current time
	 */
	public static Timestamp now() {
		Date today = new java.util.Date();
		Timestamp now = new java.sql.Timestamp(today.getTime());
		return now;
	}

	/**
	 * Returns the number of days between the two given time stamps.
	 * 
	 * @param time1 the first time stamp
	 * @param time2 the second time stamp
	 * @return the number of days between the two given time stamps
	 */
	public static int daysBetween(Timestamp time1, Timestamp time2) {
		return (int) ((time1.getTime() - time2.getTime()) / 86400000);
	}

	/**
	 * Returns the time passed in seconds since the time stamp.
	 * 
	 * @param timestamp the time stamp
	 * @return the time passed in seconds since the time stamp
	 */
	public static int secondsSince(Timestamp timestamp) {
		return (int) ((now().getTime() - timestamp.getTime()) / 1000);
	}

	/**
	 * Returns a time stamp X minutes in the future from now.
	 * 
	 * @param minutes the amount of minutes
	 * @return a time stamp X minutes in the future from now
	 */
	public static Timestamp minutesFromNow(int minutes) {
		return new Timestamp(now().getTime() + (minutes * 60000));
	}

	/**
	 * Returns the CreatureType represented by the Creature.
	 * @param creature the creature
	 * @return the CreatureType represented by the Creature
	 */
	public static CreatureType getCreatureTypeFromEntity(Creature creature) {
		final String tmp = getCreatureName(creature);
		
		if (tmp == null) {
			return null;
		}
		
        return CreatureType.fromName(tmp.replaceAll("//s+", ""));
	}
	
    public static String getCreatureName(Creature creature) {
        if (creature instanceof CaveSpider)
            return "Cave Spider";
        else if (creature instanceof Cow)
            return "Cow";
        else if (creature instanceof Chicken)
            return "Chicken";
        else if (creature instanceof Creeper)
            return "Creeper";
        else if (creature instanceof Enderman)
            return "Enderman";
        else if (creature instanceof Ghast)
            return "Ghast";
        else if (creature instanceof Giant)
            return "Giant";
        else if (creature instanceof Pig)
            return "Pig";
        else if (creature instanceof PigZombie)
            return "Pig Zombie";
        else if (creature instanceof Sheep)
            return "Sheep";
        else if (creature instanceof Skeleton)
            return "Skeleton";
        else if (creature instanceof Silverfish)
            return "Silverfish";
        else if (creature instanceof Slime)
            return "Slime";
        else if (creature instanceof Spider)
            return "Spider";
        else if (creature instanceof Squid)
            return "Squid";
        else if (creature instanceof Wolf)
            return "Wolf";
        else if (creature instanceof Zombie)
            return "Zombie";
        else
            return null;
    }

	// Parses a set of coordinates from the config file
	public static Location getLocationFromCoords(World world, String coords) {
		String[] split = coords.split(",");
		try {
			return new Location(world, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
		} catch (Exception ex) {
			System.out.println("ERROR: Invalid coordinate string passed to Util.getLocationFromCoords(): " + coords);
			return null;
		}
	}

	// Returns the point between the two points
	public static double between(double x1, double x2) {
		return x1 - ((x1 - x2) / 2);
	}

	/**
	 * Checks whether a value toTest is between the specified min and max.
	 * @param toTest the value to test
	 * @param min the minimum
	 * @param max the maximum
	 * @return true if the value is between, otherwise false
	 */
	public static boolean isBetween(final float toTest, final float min, final float max) {
		return toTest >= min && toTest <= max;
	}
	
	/**
	 * Checks whether a value toTest is between the specified min and max.
	 * @param toTest the value to test
	 * @param min the minimum
	 * @param max the maximum
	 * @return true if the value is between, otherwise false
	 */
	public static boolean isBetween(final double toTest, final double min, final double max) {
		return toTest >= min && toTest <= max;
	}

	// Finds the nearest spot to teleport to by looking upwards
	public static Location findTeleportLocation(Location location) {
		if (location == null)
			return null;
		
		final World world = location.getWorld();
		final Location loc = location.clone();
		final int locX = location.getBlockX();
		final int locZ = location.getBlockZ();
		Block block;
		
		do {
			final int highestY = world.getHighestBlockYAt(loc);
			
			if (highestY + 1 > 128) {
				block = null;
				break;
			}
			
			block = world.getBlockAt(locX, highestY + 1, locZ);
		} while (block.getType() != Material.AIR);
		
		return block == null ? null : block.getLocation();
	}

	// Returns the appropriate affinity code (-2 -> 2) for the affinity value (-10 -> 10)
	public static int getAffinityCode(float affinity) {
		int affinityCode = Math.round(affinity / 4);
		if (affinityCode > 2)
			affinityCode = 2;
		else if (affinityCode < -2)
			affinityCode = -2;

		return affinityCode;
	}

}
