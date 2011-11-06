package net.rageland.ragemod.places;

import java.util.HashSet;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.region.Region3D;

public class Quarter extends OwnedPlace {
	
	private Town town;
	private final HashSet<Lot> lots = new HashSet<Lot>();

	/**
	 * Constructs a new Quarter.
	 * @param plugin the plugin
	 * @param town the town the quarter should belong to
	 * @param boundaries the boundaries of the quarter
	 * @param name the name of the quarter
	 * @param owners the owners of the quarter
	 */
	public Quarter(RageMod plugin, Town town, Region3D boundaries, String name, Owner... owners) {
		super(plugin, PlaceType.QUARTER, boundaries, name, owners);
		setTown(town);
		// TODO: Config based messages
		setEntryMessage(new StringBuilder("You entered the ").append(getTitleColor()).append(getTitle()).append(" ").append(getType().getName()).toString());
		setExitMessage(new StringBuilder("You left the ").append(getTitleColor()).append(getTitle()).append(" ").append(getType().getName()).toString());
	}
	
	/**
	 * Sets the town this quarter belongs to.
	 * @param town the town
	 */
	public void setTown(Town town) {
		this.town = town;
	}
	
	/**
	 * Gets the town this quarter belongs to.
	 * @return the town this quarter belongs to
	 */
	public Town getTown() {
		return town;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Quarter))
			return false;
		
		Quarter q = (Quarter) o;
		return getName().equals(q.getName()) && town.equals(q.town);
	}
	
	/**
	 * Adds a lot to the quarter.
	 * @param lot the lot
	 */
	public void addLot(Lot lot) {
		if (lot == null)
			return;
		
		lots.add(lot);
	}
	
	/**
	 * Removes a lot from the quarter.
	 * @param lot the lot
	 */
	public void removeLot(Lot lot) {
		lots.remove(lot);
	}
}
