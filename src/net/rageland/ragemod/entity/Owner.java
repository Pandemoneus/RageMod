package net.rageland.ragemod.entity;

import java.util.HashSet;

import net.rageland.ragemod.places.OwnedPlace;

public interface Owner {
	/**
	 * Returns a HashSet containing a list of all places the owner owns.
	 * @return a HashSet containing a list of all places the owner owns
	 */
	public HashSet<OwnedPlace> getOwnedPlaces();
	
	/**
	 * Adds a place to the list of owned places of the owner.
	 * @param place the place
	 */
	public void addPlace(OwnedPlace place);
	
	/**
	 * Removes a place of the list of owned place of the owner.
	 * @param place the place
	 */
	public void removePlace(OwnedPlace place);
}
