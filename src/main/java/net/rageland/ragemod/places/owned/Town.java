package net.rageland.ragemod.places.owned;

import java.util.HashSet;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.PlaceType;
import net.rageland.ragemod.places.region.Region3D;

/**
 * This class represents a town.
 * @author Pandemoneus
 */
public class Town extends OwnedPlace {
	private final HashSet<Quarter> quarters = new HashSet<Quarter>();

	public Town(final RageMod plugin, final Region3D boundaries, final String name, final Owner... owners) {
		super(plugin, PlaceType.TOWN, boundaries, name, owners);
		// TODO: set entry and exit message
	}

	/**
	 * Adds a quarter to this town.
	 * If the quarter belonged to another town before, it will now belong to this one.
	 * @param quarter the quarter
	 */
	public void addQuarter(final Quarter quarter) {
		if (quarter == null)
			return;
		
		quarters.add(quarter);
		quarter.setTown(this);
	}
	
	/**
	 * Removes a quarter from this town.
	 * @param quarter the quarter
	 */
	public void removeQuarter(final Quarter quarter) {
		quarters.remove(quarter);
		quarter.setTown(null);
	}
}
