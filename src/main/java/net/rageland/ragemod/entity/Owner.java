package net.rageland.ragemod.entity;

import java.util.HashSet;

import net.rageland.ragemod.places.OwnedPlace;

public interface Owner {
	public HashSet<OwnedPlace> getOwnedPlaces();
}
