package net.rageland.ragemod.places;

import java.util.HashMap;

import net.rageland.ragemod.entity.Owner;

public interface Rentable {
	/**
	 * Gets the rent owners have to pay.
	 * @return the rent owners have to pay
	 */
	public double getRent();
	
	/**
	 * Sets the rent owners have to pay.
	 * @param rent the rent
	 */
	public void setRent(final double rent);
	
	/**
	 * Returns a HashMap containing the results of rent payments.
	 * The map may contain no elements when no applicable owners were found.
	 * @return a HashMap containing the results of rent payments
	 */
	public HashMap<Owner, RentResult> payRents();
}
