package net.rageland.ragemod.places.region;

public class WayPoint {
	private Location3D location;
	private long waitTime = 0;
	
	/**
	 * Constructs a new WayPoint at the given location with no waiting time.
	 * @param location the location of the WayPoint
	 */
	public WayPoint(final Location3D location) {
		this(location, 0);
	}
	
	/**
	 * Constructs a new WayPoint at the given location with the given waiting time.
	 * @param location the location of the point
	 * @param waitingTime the time in milliseconds
	 */
	public WayPoint(final Location3D location, final long waitingTime) {
		setLocation(location);
		setWaitingTime(waitingTime);
	}
	
	/**
	 * Sets the location of the WayPoint.
	 * @param location the new location
	 */
	public void setLocation(final Location3D location) {
		if (location == null)
			return;
		
		this.location = location;
	}
	
	/**
	 * Returns the location of the WayPoint.
	 * @return the location of the WayPoint
	 */
	public Location3D getLocation() {
		return location;
	}
	
	/**
	 * Sets the waiting time that an entity has to wait at the WayPoint.
	 * @param time the time in milliseconds
	 */
	public void setWaitingTime(final long time) {
		if (time < 0)
			return;
		
		waitTime = time;
	}
	
	/**
	 * Returns the waiting time.
	 * @return the waiting time
	 */
	public long getWaitingTime() {
		return waitTime;
	}
}
