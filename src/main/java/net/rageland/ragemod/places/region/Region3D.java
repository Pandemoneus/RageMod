package net.rageland.ragemod.places.region;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * @author Pandemoneus - https://github.com/Pandemoneus
 */
public class Region3D {
	protected final String world;
	protected final Location3D lowPoint;
	protected final Location3D highPoint;

	/**
	 * Constructs a new cuboid.
	 * 
	 * Note that the world of both locations must be the same.
	 * 
	 * @param startLoc the first point
	 * @param endLoc the second point
	 */
	public Region3D(final Location3D startLoc, final Location3D endLoc) {
		this(startLoc.getWorld(), startLoc.getBlockX(), startLoc.getBlockY(), startLoc.getBlockZ(), endLoc.getWorld(), endLoc.getBlockX(), endLoc.getBlockY(), endLoc.getBlockZ());
	}
	
	/**
	 * Constructs a new cuboid with the given coordinates.
	 * 
	 * @param world1 the first world
	 * @param x1 the first x coordinate
	 * @param y1 the first y coordinate
	 * @param z1 the first z coordinate
	 * @param world2 the second world
	 * @param x2 the second x coordinate
	 * @param y2 the second y coordinate
	 * @param z2 the second z coordinate
	 */
	public Region3D(final String world1, final int x1, final int y1, final int z1, final String world2, final int x2, final int y2, final int z2) {
		if (!(world1.equals(world2))) {
			System.out.println("Can't use two different worlds for Region3D!");
			System.out.println("Using the world of the first location instead.");
		}
		
		this.world = world1;
		
		final int lowX = Math.min(x1, x2);
		final int lowY = Math.min(y1, y2);
		final int lowZ = Math.min(z1, z2);
		
		final int highX = Math.max(x1, x2);
		final int highY = Math.max(y1, y2);
		final int highZ = Math.max(z1, z2);

		lowPoint = new Location3D(this.world, lowX, lowY, lowZ);
		highPoint = new Location3D(this.world, highX, highY, highZ);
	}

	/**
	 * Determines whether the passed area is within this area.
	 * 
	 * @param area the area to check
	 * @return true if the area is within this area, otherwise false
	 */
	public boolean isAreaWithinArea(final Region3D area) {
		return (containsLoc(area.highPoint) && containsLoc(area.lowPoint));
	}

	/**
	 * Determines whether the this cuboid contains the passed location.
	 * 
	 * @param loc the location to check
	 * @return true if the location is within this cuboid, otherwise false
	 */
	public boolean containsLoc(final Location3D loc) {
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
	public Location3D getCenter() {
		return new Location3D(world, lowPoint.getBlockX() + getXSize() / 2, lowPoint.getBlockY() + getYSize() / 2, lowPoint.getBlockZ() + getZSize() / 2);
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
		final String world = getWorld();
		final Random randomGenerator = new Random();

		Location result = new Location(Bukkit.getWorld(world), highPoint.getBlockX(), highPoint.getBlockY(), highPoint.getBlockZ());
		
		if (getSize() > 1) {
			final double randomX = lowPoint.getBlockX() + randomGenerator.nextInt(getXSize());
			final double randomY = lowPoint.getBlockY() + randomGenerator.nextInt(getYSize());
			final double randomZ = lowPoint.getBlockZ() + randomGenerator.nextInt(getZSize());
			
			result = new Location(Bukkit.getWorld(world), randomX, randomY, randomZ);
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
	 * Determines a random location inside the cuboid that is suitable for player spawning and returns it.
	 * 
	 * @return a random location inside the cuboid that is suitable for player spawning
	 */
	public Location getRandomLocationForPlayers() {
		final Location temp = getRandomLocationForMobs();
		
		return new Location(temp.getWorld(), temp.getBlockX(), temp.getBlockY() + 1, temp.getBlockZ());
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
	public Location3D getHighLoc() {
		return highPoint;
	}

	/**
	 * Returns the lower location of this cuboid.
	 * 
	 * @return the lower location of this cuboid
	 */
	public Location3D getLowLoc() {
		return lowPoint;
	}

	/**
	 * Returns the world this cuboid is in.
	 * 
	 * @return the world this cuboid is in
	 */
	public String getWorld() {
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
		return new StringBuilder(getWorld()).append(",").append(lowPoint.getBlockX()).append(",").append(lowPoint.getBlockY()).append(",").append(lowPoint.getBlockZ()).append(",").append(highPoint.getBlockX()).append(",").append(highPoint.getBlockY()).append(",").append(highPoint.getBlockZ()).toString();
	}
}
