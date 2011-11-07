package net.rageland.ragemod.places.owned;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.LotType;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.PlaceType;
import net.rageland.ragemod.places.region.Region3D;

/**
 * This class represents a lot that is part of quarter in a town.
 * @author Pandemoneus
 */
public class Lot extends OwnedPlace {
	
	private LotType type = LotType.GENERIC;
	private Quarter quarter;

	/**
	 * Constructs a lot with the given owners and the given type.
	 * @param plugin the plugin
	 * @param type the LotType
	 * @param boundaries the boundaries
	 * @param name the name
	 * @param owners the owners
	 */
	public Lot(final RageMod plugin, final LotType type, final Region3D boundaries, final String name, final Owner... owners) {
		super(plugin, PlaceType.LOT, boundaries, name, owners);
		setLotType(type);
		// TODO: set entry and exit message
	}
	
	/**
	 * Sets the type of the lot.
	 * @param type the type
	 */
	public void setLotType(final LotType type) {
		this.type = type;
	}
	
	/**
	 * Returns the type of the lot.
	 * @return the type of the lot
	 */
	public LotType getLotType() {
		return type;
	}
	
	/**
	 * Sets the quarter the lot belongs to.
	 * @param quarter the quarter
	 */
	public void setQuarter(final Quarter quarter) {
		this.quarter = quarter;
	}
	
	/**
	 * Returns the quarter the lot belongs to.
	 * @return the quarter the lot belongs to
	 */
	public Quarter getQuarter() {
		return quarter;
	}
}
