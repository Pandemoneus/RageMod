package net.rageland.ragemod.entity;

import java.util.HashSet;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.race.Race;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.region.Location3D;

public abstract class OwnerData extends EntityData implements Owner {

	private final HashSet<OwnedPlace> ownedPlaces = new HashSet<OwnedPlace>();
	
	protected OwnerData(RageMod plugin, int entityId, String name, Race race, Location3D current, Location3D spawn) {
		super(plugin, entityId, name, race, current, spawn);
	}

	@Override
	public HashSet<OwnedPlace> getOwnedPlaces() {
		return ownedPlaces;
	}

	@Override
	public void addPlace(OwnedPlace place) {
		if (place == null)
			return;
		
		ownedPlaces.add(place);
	}

	@Override
	public void removePlace(OwnedPlace place) {
		if (place == null)
			return;
		
		ownedPlaces.remove(place);		
	}
}
