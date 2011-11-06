package net.rageland.ragemod.places.region;


import org.bukkit.Location;
import org.bukkit.World;

public class Region2D extends Region3D {

	public Region2D(Location startLoc, Location endLoc) {
		super(startLoc, endLoc);
	}
	
	public Region2D(World world, int x1, int z1, int x2, int z2) {
		super(world, x1, 0, z1, x2, 0, z2);
	}
	
	/**
	 * Determines whether the passed area is within this area.
	 * 
	 * @param area the area to check
	 * @return true if the area is within this area, otherwise false
	 */
	public boolean isAreaWithinArea(Region2D area) {
		return isAreaWithinArea(new Region3D(lowPoint, highPoint));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new StringBuilder("(").append(lowPoint.getBlockX()).append(", ").append(lowPoint.getBlockZ()).append(") to (").append(highPoint.getBlockX()).append(", ").append(highPoint.getBlockZ()).append(")").toString();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @return a raw representation of this area
	 */
	@Override
	public String toRaw() {
		return new StringBuilder(getWorld().getName()).append(",").append(lowPoint.getBlockX()).append(",").append(lowPoint.getBlockZ()).append(",").append(highPoint.getBlockX()).append(",").append(highPoint.getBlockZ()).toString();
	}
}
