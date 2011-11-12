package net.rageland.ragemod.places.owned;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.LotType;
import net.rageland.ragemod.places.region.Region3D;

public class Inn extends Lot {

	public Inn(final RageMod plugin, final Region3D boundaries, final String name, final Owner... owners) {
		super(plugin, LotType.INN, boundaries, name, owners);
	}

}
