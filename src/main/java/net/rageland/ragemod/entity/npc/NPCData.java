package net.rageland.ragemod.entity.npc;

import java.util.HashSet;

import net.rageland.ragemod.entity.EntityData;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.OwnedPlace;

public class NPCData extends EntityData implements Owner {
	public int id_NPC;
	public int id_NPCRace;
	public String name;
	public boolean isBilingual;
	public int id_NPCTown;
	public boolean isMale = true;
	public String skinPath;
	public int defaultAffinityCode;

	private boolean inUse = false;

	// Sets the current NPC to "in use"
	public void activate() {
		this.inUse = true;
	}

	// Sets the current NPC to "in reserve"
	public void deactivate() {
		this.inUse = false;
	}

	// Returns the NPC's name with the appropriate code
	public String getCodedName() {
		return "<pn>" + name + "</pn>";
	}

	@Override
	public HashSet<OwnedPlace> getOwnedPlaces() {
		return null;
	}

}
