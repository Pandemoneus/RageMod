package net.rageland.ragemod.places.owned;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.NpcData;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.LotType;
import net.rageland.ragemod.places.region.Region3D;

/**
 * This class represents a market.
 * @author Pandemoneus
 */
public class Market extends Lot {	
	/**
	 * Constructs a new market.
	 * @param plugin the plugin
	 * @param boundaries the boundaries
	 * @param name the name
	 * @param owners the onwers
	 */
	public Market(final RageMod plugin, final Region3D boundaries, final String name, final Owner... owners) {
		super(plugin, LotType.MARKET, boundaries, name, owners);
	}
	
	/**
	 * Adds a NPC to the market.
	 * Note that only traders are allowed to be added.
	 * @param npc the trader
	 */
	@Override
	public void addNPC(final NpcData npc) {
		
		super.addNPC(npc);
	}
}
