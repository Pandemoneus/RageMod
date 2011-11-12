package net.rageland.ragemod.places;

import net.rageland.ragemod.places.region.Region3D;

public interface Place {
	/**
	 * Returns the type of the place.
	 * @return the type of the place
	 */
	public PlaceType getType();
	
	/**
	 * Sets the boundaries of the place.
	 * Must not be null.
	 * @param boundaries the boundaries
	 */
	public void setBoundaries(final Region3D boundaries);
	
	/**
	 * Returns the boundaries of the place.
	 * @return the boundaries of the place
	 */
	public Region3D getBoundaries();
	
	/**
	 * Sets the name of the place.
	 * Must not be null.
	 * @param name the name
	 */
	public void setName(final String name);
	
	/**
	 * Returns the name of the place.
	 * @return the name of the place
	 */
	public String getName();
	
	/**
	 * Gets the message that is displayed when a player enters the boundaries of the place.
	 * @return the message that is displayed when a player enters the boundaries of the place
	 */
	public String entryMessage();
	
	/**
	 * Gets the message that is displayed when a player exits the boundaries of the place.
	 * @return the message that is displayed when a player exits the boundaries of the place
	 */
	public String exitMessage();
}
