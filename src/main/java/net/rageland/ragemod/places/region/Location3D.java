package net.rageland.ragemod.places.region;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * Serializable version of Bukkit's Location class
 * 
 * @author Pandemoneus - https://github.com/Pandemoneus
 */
public class Location3D extends Location2D {

	/**
	 * serialVersionUID by Eclipse
	 */
	private static final long serialVersionUID = 7135831682192122814L;
	
	protected final int y;
	protected float yaw;
	protected float pitch;
	
	/**
	 * Constructs a new location with the given world and x, y and z coordinate.
	 * @param world the world
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Location3D(final String world, final int x, final int y, final int z) {
		this(world, x, y, z, 0.0f, 0.0f);
	}
	
	public Location3D(final String world, final int x, final int y, final int z, final float yaw, final float pitch) {
		super(world, x, z);
		this.y = y;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	/**
	 * Constructs a new location from the given Bukkit location.
	 * @param loc the Bukkit location
	 */
	public Location3D(final Location loc) {
		this(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getYaw(), loc.getPitch());
	}
	
	/**
	 * Returns the y coordinate of the location.
	 * @return the y coordinate of the location
	 */
	public int getBlockY() {
		return y;
	}
	
	/**
	 * Returns the yaw of the location.
	 * @return the yaw coordinate of the location
	 */
	public float getYaw() {
		return yaw;
	}
	
	/**
	 * Sets the yaw of the location.
	 * @param yaw the yaw
	 */
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	/**
	 * Returns the pitch coordinate of the location.
	 * @return the pitch coordinate of the location
	 */
	public float getPitch() {
		return pitch;
	}
	
	/**
	 * Sets the pitch of the location.
	 * @param pitch the pitch
	 */
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	/**
	 * Returns the block at this location.
	 * @return the block at this location
	 */
	public Block getBlock() {
		return new Location(Bukkit.getServer().getWorld(world), x, y, z).getBlock();
	}
	
    /**
     * Calculates the euclidian distance between this and the given location.
     * 
     * @param loc the location
     * @return the distance between this and the given location
     */
    public int distance(final Location3D loc) {
    	final int dx = this.x - loc.getBlockX();
    	final int dy = this.y - loc.getBlockY();
    	final int dz = this.z - loc.getBlockZ();
    	
    	return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder(world).append(",").append(x).append(",").append(y).append(",").append(z).toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Location3D)) {
			return false;
		}
		
		Location3D loc = (Location3D) o;
		
		return ((Location2D) this).equals(o) && this.getBlockY() == loc.getBlockY();
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {
    	return new HashCodeBuilder(3, 23).append(world).append(x).append(y).append(z).toHashCode();
    }
}
