package net.rageland.ragemod.places;

import java.util.HashMap;

import net.rageland.ragemod.entity.Owner;

public interface Rentable {
	/**
	 * Returns a HashMap containing the results of rent payments.
	 * The map may contain no elements when no applicable owners were found.
	 * @return a HashMap containing the results of rent payments
	 */
	public HashMap<Owner, RentResult> payRents();
}
