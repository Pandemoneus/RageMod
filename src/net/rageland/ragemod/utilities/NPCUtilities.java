package net.rageland.ragemod.utilities;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;

public class NpcUtilities {
	public static Location findValidRandomNPCSpawnInLocation(Location min, Location max, int standardHeight) {
		int maxRetries = 20;
		Random rand = new Random();
		if (min.getWorld().equals(max.getWorld())) {
			World world = min.getWorld();
			int randomX = rand.nextInt(max.getBlockX() - min.getBlockX()) + min.getBlockX();
			int randomZ = rand.nextInt(max.getBlockZ() - min.getBlockZ()) + min.getBlockZ();

			Location possibleLocation;

			for (int i = 0; i < maxRetries; i++) {
				possibleLocation = new Location(world, randomX, standardHeight, randomZ);

				for (int x = -1; x < 2; i++) {
					for (int y = -1; y < 2; y++) {
						for (int z = -1; z < 2; z++) {
							// if(possibleLocation.getBlock().getRelative(x, y,
							// z).isEmpty()) // TODO: Commented out by Icarus,
							// preventing compilation
							{

							}
						}
					}
				}

			}

			return new Location(min.getWorld(), 2, 2, 2);
		} else {
			// Cannot find position between different worlds
			return null;
		}
	}
}
