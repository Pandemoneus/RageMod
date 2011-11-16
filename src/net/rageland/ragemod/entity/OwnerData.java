package net.rageland.ragemod.entity;

import java.util.HashSet;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.race.Race;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.region.Location3D;

public abstract class OwnerData extends EntityData implements Owner {

	private final HashSet<OwnedPlace> ownedPlaces = new HashSet<OwnedPlace>();
	
	protected OwnerData(final RageMod plugin, final int entityId, final String name, final Race race, final Location3D current, final Location3D spawn, final boolean spawnOnLoad) {
		super(plugin, entityId, name, race, current, spawn, spawnOnLoad);
	}

	@Override
	public HashSet<OwnedPlace> getOwnedPlaces() {
		return ownedPlaces;
	}

	@Override
	public void addPlace(final OwnedPlace place) {
		if (place == null)
			return;
		
		ownedPlaces.add(place);
	}

	@Override
	public void removePlace(final OwnedPlace place) {
		if (place == null)
			return;
		
		ownedPlaces.remove(place);		
	}
}
