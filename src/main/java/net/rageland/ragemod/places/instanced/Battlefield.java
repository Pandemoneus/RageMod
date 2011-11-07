package net.rageland.ragemod.places.instanced;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.places.InstancedPlace;
import net.rageland.ragemod.places.PlaceType;
import net.rageland.ragemod.places.region.Region3D;

public class Battlefield extends InstancedPlace {

	public Battlefield(final RageMod plugin, final Region3D boundaries, final String name) {
		super(plugin, PlaceType.BATTLEFIELD, boundaries, name);
	}

}
