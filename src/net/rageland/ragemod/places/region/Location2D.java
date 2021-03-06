package net.rageland.ragemod.places.region;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Serializable version of Bukkit's Location class in 2D
 * 
 * @author Pandemoneus - https://github.com/Pandemoneus
 */
public class Location2D implements Serializable {

	/**
	 * serialVersionUID by Eclipse
	 */
	private static final long serialVersionUID = -1837835802368871228L;

	protected final String world;
	protected final int x;
	protected final int z;
	
	/**
	 * Constructs a new location with the given world and x and z coordinate.
	 * @param world the world
	 * @param x the x coordinate
	 * @param z the z coordinate
	 */
	public Location2D(final String world, final int x, final int z) {
		this.world = world;
		this.x = x;
		this.z = z;
	}

	/**
	 * Constructs a new location from the given Bukkit location.
	 * @param loc the Bukkit location
	 */
	public Location2D(final Location loc) {
		this(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockZ());
	}
	
	/**
	 * Returns the x coordinate of the location.
	 * @return the x coordinate of the location
	 */
	public int getBlockX() {
		return x;
	}
	
	/**
	 * Returns the z coordinate of the location.
	 * @return the z coordinate of the location
	 */
	public int getBlockZ() {
		return z;
	}
	
	/**
	 * Returns the world's name of the location.
	 * @return the world's name of the location
	 */
	public String getWorldName() {
		return world;
	}
	
	/**
	 * Returns the world of the location.
	 * Might return null when the world was deleted after the creation of the location.
	 * @return the world of the location
	 */
	public World getWorld() {
		return Bukkit.getWorld(world);
	}
	
    /**
     * Calculates the euclidean distance between this and the given location.
     * 
     * @param loc the location
     * @return the distance between this and the given location
     */
    public int distance(final Location2D loc) {
    	final int dx = this.x - loc.getBlockX();
    	final int dz = this.z - loc.getBlockZ();
    	
    	return (int) Math.sqrt(dx * dx + dz * dz);
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder(world).append(",").append(x).append(",").append(z).toString();
	}
	
	/**
	 * Returns a Bukkit location.
	 * @return a bukkit location
	 */
	public Location toLocation() {
		return new Location(getWorld(), x, 0, z);
	}
	
	public LinkedHashMap<String, Object> toMap() {
		final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(3);
		
		map.put("world", world);
		map.put("x", x);
		map.put("z", z);
		
		return map;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof Location2D)) {
			return false;
		}
		
		final Location2D loc = (Location2D) o;
		
		return this.world.equals(loc.world) && this.x == loc.x && this.z == loc.z;
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {
    	return new HashCodeBuilder(7, 23).append(world).append(x).append(z).toHashCode();
    }
}
