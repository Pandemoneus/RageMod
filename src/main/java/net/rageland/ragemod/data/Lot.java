package net.rageland.ragemod.data;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.places.LotType;
import net.rageland.ragemod.places.region.Region3D;

import org.bukkit.Location;

// May want to consider having this extend the Region2D class

// The Lot class is used to store data about player lots in the world's capitol city
public class Lot {
	private RageMod plugin;

	public int lotId;
	public int number;
	public LotType category;
	public Region3D region;
	public String owner; // Only necessary when loaded into the Lots global

	public Lot(RageMod plugin) {
		this.plugin = plugin;
	}

	/**
	 * Returns the prefix and the lot number code.
	 * 
	 * @return the prefix and the lot number code
	 */
	public String getLotCode() {
		return category.getCategoryPrefix() + number;
	}

	// Sets the lot's category based on the category character
	public void setCategory(String prefix) {
		LotType lc = LotType.byPrefix(prefix);
		
		if (lc == null) {
			System.out.println("Invalid prefix passed to Lot.setCategory()");
		} else {
			category = lc;
		}
	}

	// Return whether or not the player can use the /home set command inside this lot
	public boolean canSetHome() {
		return isMemberLot();
	}

	// Returns whether or not the current location is inside the Lot
	public boolean isInside(Location loc) {
		return region.containsLoc(loc);
	}

	// Returns whether or not the lot is one of the categories of member lot
	public boolean isMemberLot() {
		switch (category) {
		case COAL:
		case IRON:
		case GOLD:
		case DIAMOND:
			return true;
		default:
			return false;
		}
	}

	// Returns the price for this lot as defined in the config
	public int getPrice() {
		switch (category) {
		case COAL:
			return plugin.config.Lot_PRICE_COAL;
		case IRON:
			return plugin.config.Lot_PRICE_IRON;
		case GOLD:
			return plugin.config.Lot_PRICE_GOLD;
		case DIAMOND:
			return plugin.config.Lot_PRICE_DIAMOND;
		default:
			return 0;
		}
	}

	/**
	 * Returns a location in the center of the region for compass target.
	 * 
	 * @return a location in the center of the region for compass target
	 */
	public Location getCenter() {
		return region.getCenter();
	}

}
