package net.rageland.ragemod.places.region;

public class Region2D extends Region3D {

	public Region2D(final Location2D startLoc, final Location2D endLoc) {
		this(startLoc.getWorldName(), startLoc.getBlockX(), startLoc.getBlockZ(), endLoc.getWorldName(), endLoc.getBlockX(), endLoc.getBlockZ());
	}
	
	public Region2D(final String world1, final int x1, final int z1, final String world2, final int x2, final int z2) {
		super(world1, x1, 0, z1, world2, x2, 0, z2);
	}
	
	/**
	 * Determines whether the passed area is within this area.
	 * @param area the area to check
	 * @return true if the area is within this area, otherwise false
	 */
	public boolean isAreaWithinArea(final Region2D area) {
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
		return new StringBuilder(world).append(",").append(lowPoint.getBlockX()).append(",").append(lowPoint.getBlockZ()).append(",").append(highPoint.getBlockX()).append(",").append(highPoint.getBlockZ()).toString();
	}
}
