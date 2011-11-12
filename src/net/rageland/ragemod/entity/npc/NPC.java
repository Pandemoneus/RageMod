package net.rageland.ragemod.entity.npc;

import net.rageland.ragemod.entity.Entity;

import org.bukkit.entity.Player;

public interface NPC extends Entity {
	/**
	 * Gets the action that happens when a player left-clicks a NPC.
	 * @param player the player that triggers
	 */
	public void leftClickAction(final Player player);
	
	/**
	 * Gets the action that happens when a player right-clicks a NPC.
	 * @param player the player that triggers
	 */
	public void rightClickAction(final Player player);
}
