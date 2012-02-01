package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.error.YAMLException;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.Handler;
import net.rageland.ragemod.ModuleHandler;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.utilities.ConfigurationUtilities;
import net.rageland.ragemod.utilities.PluginLogger;

public final class NpcHandler implements Handler {
	private final HashMap<Integer, NpcData> npcs = new HashMap<Integer, NpcData>();
	private final HashMap<String, NpcSpeechData> speech = new HashMap<String, NpcSpeechData>();
	private final Configuration npcConfig;
	private final Configuration speechConfig;
	private static NpcHandler instance;
	
	public static final String CONFIG_NAME_NPC = "npcs.yml";
	public static final String CONFIG_NAME_SPEECH = "speech.yml";
	
	private NpcHandler() {
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
	public boolean addNPCData(final NpcData data) {
		if (data == null || npcs.containsValue(data))
			return false;
		
		npcs.put(data.uid, data);
		return true;
	}
	
	public void removeNpcData(final int uid) {
		npcs.remove(uid);
	}
	
	public NpcData getNpcData(final int uid) {
		return npcs.get(uid);
	}
	
	public NpcSpeechData getNpcSpeechData(final String identifier) {
		return speech.get(identifier);
	}
	
	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static NpcHandler getInstance() {
		if (instance == null)
			instance = new NpcHandler();

		return instance;
	}

	@Override
	public boolean loadData() {
		boolean success = true;
		
		//NPCData
		final YamlConfiguration npcConf = npcConfig.getYamlConfig();
		int counter = 0;
		
		try {
			for (final String s : npcConf.getKeys(false)) {
				if (!(npcConf.isConfigurationSection(s)))
					continue;
				
				final ConfigurationSection cs = npcConf.getConfigurationSection(s);
				
				npcs.put(Integer.parseInt(s), NpcData.getDataFromConfigurationSection(cs));
				counter++;
			}
			
			PluginLogger.getInstance().info(new StringBuilder("Loaded ").append(counter).append(" NPCs.").toString());
		} catch (YAMLException yaml) {
			PluginLogger.getInstance().severe("Failed loading the configuration for " + CONFIG_NAME_NPC);
			PluginLogger.getInstance().severe(yaml.getMessage());
			success = false;
		}
		
		//SpeechData
		final YamlConfiguration speechConf = speechConfig.getYamlConfig();
		
		try {
			for (final String identifier : speechConf.getKeys(false)) {
				if (!(speechConf.isConfigurationSection(identifier)))
					continue;
				
				final ConfigurationSection cs = speechConf.getConfigurationSection(identifier);
				
				final String initialGreeting = cs.getString("Greeting");
				final List<String> messages = cs.getStringList("Messages");
				final List<String> followUps = cs.getStringList("FollowUps");
				
				final NpcSpeechData speechData = new NpcSpeechData(initialGreeting, new ArrayList<String>(messages), followUps.toArray(new String[5]));
				
				speech.put(identifier, speechData);
			}
		} catch (YAMLException yaml) {
			PluginLogger.getInstance().severe("Failed loading the configuration for " + CONFIG_NAME_SPEECH);
			PluginLogger.getInstance().severe(yaml.getMessage());
			success = false;
		}
		
		return success;
	}
	
	@Override
	public boolean saveData() {
		final YamlConfiguration c = npcConfig.getYamlConfig();
		int counter = 0;
		
		try {
			for (final int id : npcs.keySet()) {
				final ConfigurationSection cs = c.createSection(Integer.toString(id));
				ConfigurationUtilities.saveMap(cs, npcs.get(id).save());
				counter++;
			}
			
			npcConfig.save();
			PluginLogger.getInstance().info(new StringBuilder("Saved ").append(counter).append(" NPCs.").toString());
			
			return true;
		} catch (YAMLException yaml) {
			PluginLogger.getInstance().severe("Failed saving the configuration for " + CONFIG_NAME_NPC);
			PluginLogger.getInstance().severe(yaml.getMessage());
			return false;
		}
	}
}
