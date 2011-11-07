package net.rageland.ragemod.places;

import java.util.HashMap;
import java.util.HashSet;

import net.milkbowl.vault.economy.Economy;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.entity.player.PlayerData;
import net.rageland.ragemod.places.region.Region3D;
import net.rageland.ragemod.pvp.Contestable;

/**
 * This class provides methods for places that have an owner.
 * @author Pandemoneus
 */
public abstract class OwnedPlace extends BasicPlace implements Rentable, Contestable {
	
	private final HashSet<Owner> owners = new HashSet<Owner>();
	private double rent = 0;

	protected OwnedPlace(final RageMod plugin, final PlaceType type, final Region3D boundaries, final String name, final Owner... owners) {
		this(plugin, type, boundaries, name, 0, owners);
	}
	
	protected OwnedPlace(final RageMod plugin, final PlaceType type, final Region3D boundaries, final String name, final double rent, final Owner... owners) {
		super(plugin, type, boundaries, name);
		addOwners(owners);
		setRent(rent);
	}

	/**
	 * Returns whether the passed owner is an owner of this place.
	 * @param owner the owner to check
	 * @return true if the owner owns this place, otherwise false
	 */
	public boolean isOwner(final Owner owner) {
		return owners.contains(owner);
	}
	
	/**
	 * Returns whether the place has any owners.
	 * @return true if the place has owners, otherwise false
	 */
	public boolean isOwned() {
		return !owners.isEmpty();
	}
	
	/**
	 * Removes an owner from the list of owners of the place.
	 * @param owner the owner
	 */
	public void removeOwner(final Owner owner) {
		owners.remove(owner);
	}
	
	/**
	 * Adds owners to the list of owners of the place.
	 * @param owners the owners to add
	 */
	public void addOwners(final Owner... owners) {
		if (owners == null) {
			return;
		}
		
		for (Owner owner : owners) {
			if (owner == null)
				continue;
				
			this.owners.add(owner);
		}
	}
	
	/**
	 * Removes all current owners from the list of owners of the place.
	 */
	public void setUnowned() {
		owners.clear();
	}
	
	/**
	 * Sets the rent of the place.
	 * @param rent the rent
	 */
	public void setRent(final double rent) {
		this.rent = rent;
	}
	
	/**
	 * Returns the rent of the place.
	 * @return the rent
	 */
	public double getRent() {
		return rent;
	}
	
	private RentResult payRent(final Owner owner) {
		if (rent == 0.0)
			return RentResult.NO_RENT;
		
		if (!(owner instanceof PlayerData))
			return RentResult.WRONG_OWNER_TYPE;
		
		final String playerName = ((PlayerData) owner).getName();
		final Economy economy = RageMod.economy;
		
		if (rent < 0.0) {
			if (economy.has(playerName, rent)) {
				economy.withdrawPlayer(playerName, rent);
			} else {
				return RentResult.NOT_ENOUGH_MONEY;
			}
		} else {
			economy.depositPlayer(playerName, rent);
		}

		return RentResult.PAID;
	}
	
	public HashMap<Owner, RentResult> payRents() {
		final HashMap<Owner, RentResult> map = new HashMap<Owner, RentResult>();
		
		for (Owner o : owners) {
			map.put(o, payRent(o));
		}
		
		return map;
	}
}
