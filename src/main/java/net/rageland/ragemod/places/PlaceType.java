package net.rageland.ragemod.places;

import java.util.HashMap;

public enum PlaceType {
	CAPITOL("Capitol"),
	GENERIC("Generic"),
	DUNGEON("Dungeon"),
	BATTLEGROUND("Battleground"),
	LOT("Lot"),
	QUARTER("Quarter"),
	SHRINE("Shrine"),
	TOWN("Town");

	private final String name;
	private static HashMap<String, PlaceType> names = new HashMap<String, PlaceType>();

	private PlaceType(final String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the type.
	 * @return the name of the type
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a type by the given name.
	 * @param name the name
	 * @return the type specified by the name or null if the name does not specify any type
	 */
	public PlaceType byName(final String name) {
		return names.get(name);
	}

	static {
		for (PlaceType type : PlaceType.values()) {
			names.put(type.getName(), type);
		}
	}
}
