package net.rageland.ragemod.places.region;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Pandemoneus - https://github.com/Pandemoneus
 */
public class Region3D {
	protected final World world;
	protected final Location lowPoint;
	protected final Location highPoint;

	/**
	 * Constructs a new cuboid.
	 * 
	 * Note that the world of both locations must be the same.
	 * 
	 * @param startLoc the first point
	 * @param endLoc the second point
	 */
	public Region3D(Location startLoc, Location endLoc) {
		this(startLoc.getWorld(), startLoc.getBlockX(), startLoc.getBlockY(), startLoc.getBlockZ(), endLoc.getBlockX(), endLoc.getBlockY(), endLoc.getBlockZ());

		if (!(startLoc.getWorld().equals(endLoc.getWorld()))) {
			System.out.println("Can't use two different worlds for Region3D!");
			System.out.println("Using the world of the first location instead.");
		}
	}
	
	/**
	 * Constructs a new cuboid with the given coordinates.
	 * 
	 * @param world the world
	 * @param x1 the first x coordinate
	 * @param y1 the first y coordinate
	 * @param z1 the first z coordinate
	 * @param x2 the second x coordinate
	 * @param y2 the second y coordinate
	 * @param z2 the second z coordinate
	 */
	public Region3D(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
		World tmpWorld = world;
		
		if (tmpWorld == null) {
			System.out.println("Passed world is null! Using default world instead.");
			tmpWorld = Bukkit.getWorlds().get(0);
		}
		
		this.world = tmpWorld;
		
		final int lowX = Math.min(x1, x2);
		final int lowY = Math.min(y1, y2);
		final int lowZ = Math.min(z1, z2);
		
		final int highX = Math.max(x1, x2);
		final int highY = Math.max(y1, y2);
		final int highZ = Math.max(z1, z2);

		lowPoint = new Location(this.world, lowX, lowY, lowZ);
		highPoint = new Location(this.world, highX, highY, highZ);
	}

	/**
	 * Determines whether the passed area is within this area.
	 * 
	 * @param area the area to check
	 * @return true if the area is within this area, otherwise false
	 */
	public boolean isAreaWithinArea(Region3D area) {
		return (containsLoc(area.highPoint) && containsLoc(area.lowPoint));
	}

	/**
	 * Determines whether the this cuboid contains the passed location.
	 * 
	 * @param loc the location to check
	 * @return true if the location is within this cuboid, otherwise false
	 */
	public boolean containsLoc(Location loc) {
		if (loc == null || !loc.getWorld().equals(highPoint.getWorld())) {
			return false;
		}

		return lowPoint.getBlockX() <= loc.getBlockX()
				&& highPoint.getBlockX() >= loc.getBlockX()
				&& lowPoint.getBlockZ() <= loc.getBlockZ()
				&& highPoint.getBlockZ() >= loc.getBlockZ()
				&& lowPoint.getBlockY() <= loc.getBlockY()
				&& highPoint.getBlockY() >= loc.getBlockY();
	}
	
	/**
	 * Returns the center of the cuboid.
	 * 
	 * @return the center of the cuboid
	 */
	public Location getCenter() {
		return new Location(world, lowPoint.getBlockX() + getXSize() / 2, lowPoint.getBlockY() + getYSize() / 2, lowPoint.getBlockZ() + getZSize() / 2);
	}

	/**
	 * Returns the volume of this cuboid.
	 * 
	 * @return the volume of this cuboid
	 */
	public long getSize() {
		return Math.abs(getXSize() * getYSize() * getZSize());
	}
	
	/**
	 * Returns whether the cuboid has no volume.
	 * 
	 * @return true if the cuboid has no volume, otherwise false
	 */
	public boolean hasZeroVolume() {
		return getSize() == 0;
	}
	
	/**
	 * Determines a random location inside the cuboid and returns it.
	 * 
	 * @return a random location within the cuboid
	 */
	public Location getRandomLocation() {
		final World world = getWorld();
		final Random randomGenerator = new Random();

		Location result = new Location(world, highPoint.getBlockX(), highPoint.getBlockY(), highPoint.getZ());
		
		if (getSize() > 1) {
			final double randomX = lowPoint.getBlockX() + randomGenerator.nextInt(getXSize());
			final double randomY = lowPoint.getBlockY() + randomGenerator.nextInt(getYSize());
			final double randomZ = lowPoint.getBlockZ() + randomGenerator.nextInt(getZSize());
			
			result = new Location(world, randomX, randomY, randomZ);
		}
		
		return result;
	}
	
	/**
	 * Determines a random location inside the cuboid that is suitable for mob spawning and returns it.
	 * 
	 * @return a random location inside the cuboid that is suitable for mob spawning
	 */
	public Location getRandomLocationForMobs() {
		final Location temp = getRandomLocation();
		
		return new Location(temp.getWorld(), temp.getBlockX() + 0.5, temp.getBlockY() + 0.5, temp.getBlockZ() + 0.5);
	}

	/**
	 * Returns the x span of this cuboid.
	 * 
	 * @return the x span of this cuboid
	 */
	public int getXSize() {
		return (highPoint.getBlockX() - lowPoint.getBlockX()) + 1;
	}

	/**
	 * Returns the y span of this cuboid.
	 * 
	 * @return the y span of this cuboid
	 */
	public int getYSize() {
		return (highPoint.getBlockY() - lowPoint.getBlockY()) + 1;
	}

	/**
	 * Returns the z span of this cuboid.
	 * 
	 * @return the z span of this cuboid
	 */
	public int getZSize() {
		return (highPoint.getBlockZ() - lowPoint.getBlockZ()) + 1;
	}

	/**
	 * Returns the higher location of this cuboid.
	 * 
	 * @return the higher location of this cuboid
	 */
	public Location getHighLoc() {
		return highPoint;
	}

	/**
	 * Returns the lower location of this cuboid.
	 * 
	 * @return the lower location of this cuboid
	 */
	public Location getLowLoc() {
		return lowPoint;
	}

	/**
	 * Returns the world this cuboid is in.
	 * 
	 * @return the world this cuboid is in
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("(").append(lowPoint.getBlockX()).append(", ").append(lowPoint.getBlockY()).append(", ").append(lowPoint.getBlockZ()).append(") to (").append(highPoint.getBlockX()).append(", ").append(highPoint.getBlockY()).append(", ").append(highPoint.getBlockZ()).append(")").toString();
	}
	
	/**
	 * Returns a raw representation that is easy to read for Java.
	 * 
	 * @return a raw representation of this cuboid
	 */
	public String toRaw() {
		return new StringBuilder(getWorld().getName()).append(",").append(lowPoint.getBlockX()).append(",").append(lowPoint.getBlockY()).append(",").append(lowPoint.getBlockZ()).append(",").append(highPoint.getBlockX()).append(",").append(highPoint.getBlockY()).append(",").append(highPoint.getBlockZ()).toString();
	}
}
