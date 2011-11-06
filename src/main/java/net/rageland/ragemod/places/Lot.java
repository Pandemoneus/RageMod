package net.rageland.ragemod.places;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
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
	public Lot(RageMod plugin, LotType type, Region3D boundaries, String name, Owner... owners) {
		super(plugin, PlaceType.LOT, boundaries, name, owners);
		setLotType(type);
		// TODO: set entry and exit message
	}
	
	/**
	 * Sets the type of the lot.
	 * @param type the type
	 */
	public void setLotType(LotType type) {
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
	public void setQuarter(Quarter quarter) {
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
