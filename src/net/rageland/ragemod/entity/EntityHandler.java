package net.rageland.ragemod.entity;

import java.util.ArrayList;
import java.util.HashMap;
import net.rageland.ragemod.Handler;
import net.rageland.ragemod.entity.npc.NPCData;
import net.rageland.ragemod.entity.npc.NPCHandler;

public class EntityHandler extends Handler {
	private static final HashMap<Integer, EntityData> entities = new HashMap<Integer, EntityData>();
	private static final ArrayList<Integer> spawnedEntities = new ArrayList<Integer>();
	private static EntityHandler instance;
	
	/**
	 * Gets an id that can be used for new entities.
	 * @return a free id
	 */
	public static int getNextFreeId() {
		int i = 0;
		
		while(entities.containsKey(i))
			i++;
		
		return i;
	}
	
	/**
	 * Adds entity data to the handler.
	 * @param data the entity data
	 * @return true if successful, false if it already contained the data
	 */
	public boolean addEntityData(final EntityData data) {
		if (data == null || entities.containsValue(data))
			return false;
		
		entities.put(data.entityId, data);
		return true;
	}
	
	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static EntityHandler getInstance() {
		if (instance == null)
			instance = new EntityHandler();

		return instance;
	}
	
	/**
	 * Spawns an entity using the given id.
	 * The entity must be added before by using addEntityData().
	 * @param id the id
	 * @return the entity that was spawned or null when it failed to
	 */
	public org.bukkit.entity.Entity spawn(final int id) {
		final EntityData data = entities.get(id);
		
		if (data == null)
			return null;
		
		org.bukkit.entity.Entity ent = null;
		
		if (data instanceof NPCData) {
			ent = NPCHandler.getInstance().spawn((NPCData) data);
			
			if (ent != null)
				spawnedEntities.add(id);
		}
		
		return ent;
	}
	
	/**
	 * Despawns an entity using the given id.
	 * @param id the id
	 * @return true when it was despawned, false when there was no entity with that id
	 */
	public boolean despawn(final int id) {
		boolean result = false;
		final EntityData data = entities.get(id);

		if (data instanceof NPCData) {
			NPCHandler.getInstance().despawn((NPCData) data);
			spawnedEntities.remove(id);
			result = true;
		}
		
		return result;
	}
}
