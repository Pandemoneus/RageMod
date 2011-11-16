package net.rageland.ragemod.entity;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.martin.bukkit.npclib.NPCManager;
import org.yaml.snakeyaml.error.YAMLException;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.Handler;
import net.rageland.ragemod.ModuleHandler;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.NPCData;

public final class EntityHandler implements Handler {
	private final HashMap<Integer, EntityData> entities = new HashMap<Integer, EntityData>();
	private final ArrayList<Integer> spawnedEntities = new ArrayList<Integer>();
	private final Configuration config;
	private static EntityHandler instance;
	
	private static final String CONFIG_NAME = "Entities.yml";
	
	public final NPCManager manager;
	
	private EntityHandler() {
		final ModuleHandler mh = ModuleHandler.getInstance();
		final RageMod rm = mh.plugin;
		
		mh.addHandler(this);
		manager = new NPCManager(rm);
		
		config = new Configuration(rm, CONFIG_NAME);
		addDefaults();
	}
	
	private void addDefaults() {
				
	}

	/**
	 * Gets an id that can be used for new entities.
	 * @return a free id
	 */
	public int getNextFreeId() {
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
			ent = (Entity) manager.spawnNPC(data.getName(), data.getLocation().toLocation(), Integer.toString(data.entityId));
		}
		
		if (ent != null)
			spawnedEntities.add(id);
		
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
			manager.despawnById(Integer.toString(data.entityId));
			spawnedEntities.remove(id);
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean loadData() {
		final YamlConfiguration c = config.config;
		
		try {
			for (final Object o : c.getList(c.getName())) {
				if (o instanceof String) {
					final String s = (String) o;
					if (c.isConfigurationSection(s))
						;
				}
			}
		} catch (YAMLException yaml) {
			final RageMod plugin = config.plugin;
			plugin.logger.severe("Failed loading the configuration for " + config.file);
			plugin.logger.severe(yaml.getMessage());
			return false;
		}
	}
}
