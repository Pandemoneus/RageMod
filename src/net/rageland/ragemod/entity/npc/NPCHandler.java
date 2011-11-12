package net.rageland.ragemod.entity.npc;

import org.bukkit.entity.Entity;
import org.martin.bukkit.npclib.NPCManager;

import net.rageland.ragemod.entity.EntityHandler;

public class NPCHandler extends EntityHandler {
	public final NPCManager manager;
	private static NPCHandler instance;
	
	private NPCHandler() {
		manager = new NPCManager(plugin);
	}

	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static NPCHandler getInstance() {
		if (instance == null)
			instance = new NPCHandler();

		return instance;
	}
	
	/**
	 * Spawns a NPC.
	 * NEVER CALL THIS METHOD DIRECTLY.
	 * Instead, use the NPCs themselves to call this.
	 * @param data the npc data
	 * @return the entity spawned
	 */
	public Entity spawn(final NPCData data) {
		if (data == null)
			return null;
		
		final Entity ent = (Entity) manager.spawnNPC(data.getName(), data.getLocation().toLocation(), Integer.toString(data.entityId));
		
		return ent;
	}
	
	/**
	 * Despawns a NPC.
	 * NEVER CALL THIS METHOD DIRECTLY.
	 * Instead, use the NPCs themselves to call this.
	 * @param data the npc data
	 */
	public void despawn(final NPCData data) {
		if (data == null)
			return;
		
		manager.despawnById(Integer.toString(data.entityId));
	}
}