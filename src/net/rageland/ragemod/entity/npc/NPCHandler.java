package net.rageland.ragemod.entity.npc;

import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.error.YAMLException;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.Handler;
import net.rageland.ragemod.ModuleHandler;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.utilities.Log;

public final class NPCHandler implements Handler {
	private final HashMap<Integer, NPCData> npcs = new HashMap<Integer, NPCData>();
	private final Configuration npcConfig;
	public final Configuration speechConfig;
	private static NPCHandler instance;
	
	public static final String CONFIG_NAME_NPC = "npcs.yml";
	public static final String CONFIG_NAME_SPEECH = "speech.yml";
	
	private NPCHandler() {
		final ModuleHandler mh = ModuleHandler.getInstance();
		final RageMod rm = mh.plugin;
		
		mh.addHandler(this);
		
		npcConfig = new Configuration(rm, CONFIG_NAME_NPC);
		speechConfig = new Configuration(rm, CONFIG_NAME_SPEECH);
	}
	
	public boolean has(final int uid) {
		return npcs.containsKey(uid);
	}
	
	/**
	 * Adds entity data to the handler.
	 * @param data the entity data
	 * @return true if successful, false if it already contained the data
	 */
	public boolean addNPCData(final NPCData data) {
		if (data == null || npcs.containsValue(data))
			return false;
		
		npcs.put(data.uid, data);
		return true;
	}
	
	public void removeNPCData(final int uid) {
		npcs.remove(uid);
	}
	
	public NPCData getNPCData(final int uid) {
		return npcs.get(uid);
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

	public boolean loadData() {
		final YamlConfiguration c = npcConfig.config;
		int counter = 0;
		
		try {
			for (final String s : c.getKeys(false)) {
				if (!(c.isConfigurationSection(s)))
					continue;
				
				final ConfigurationSection cs = c.getConfigurationSection(s);
				
				npcs.put(Integer.parseInt(s), NPCData.getDataFromConfigurationSection(cs));
				counter++;
			}
			
			Log.getInstance().info(new StringBuilder("Loaded ").append(counter).append(" entities.").toString());
			
			return true;
		} catch (YAMLException yaml) {
			Log.getInstance().severe("Failed loading the configuration for " + npcConfig.file);
			Log.getInstance().severe(yaml.getMessage());
			return false;
		}
	}
	
	public boolean saveData() {
		final YamlConfiguration c = npcConfig.config;
		int counter = 0;
		
		try {
			for (final int id : npcs.keySet()) {
				final ConfigurationSection cs = c.createSection(Integer.toString(id));
				Configuration.saveMap(cs, npcs.get(id).save());
				counter++;
			}
			
			npcConfig.save();
			Log.getInstance().info(new StringBuilder("Saved ").append(counter).append(" entities.").toString());
			
			return true;
		} catch (YAMLException yaml) {
			Log.getInstance().severe("Failed saving the configuration for " + npcConfig.file);
			Log.getInstance().severe(yaml.getMessage());
			return false;
		}
	}
}
