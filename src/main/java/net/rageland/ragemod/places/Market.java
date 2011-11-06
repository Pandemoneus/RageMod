package net.rageland.ragemod.places;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.NPC;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.entity.npc.Trader;
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
	public Market(RageMod plugin, Region3D boundaries, String name, Owner... owners) {
		super(plugin, LotType.MARKET, boundaries, name, owners);
	}
	
	/**
	 * Adds a NPC to the market.
	 * Note that only traders are allowed to be added.
	 * @param npc the trader
	 */
	@Override
	public void addNPC(NPC npc) {
		if (!(npc instanceof Trader))
			return;
		
		super.addNPC(npc);
	}
}
