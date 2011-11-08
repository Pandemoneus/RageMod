package net.rageland.ragemod.places.instanced;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.places.InstancedPlace;
import net.rageland.ragemod.places.PlaceType;
import net.rageland.ragemod.places.region.Region3D;

public class Battleground extends InstancedPlace {

	public Battleground(final RageMod plugin, final Region3D boundaries, final String name) {
		super(plugin, PlaceType.BATTLEGROUND, boundaries, name);
	}

}
