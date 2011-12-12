package net.rageland.ragemod.entity.player;

import java.util.Date;
import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.error.YAMLException;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.Handler;
import net.rageland.ragemod.ModuleHandler;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.NpcData;
import net.rageland.ragemod.entity.npc.NpcHandler;
import net.rageland.ragemod.utilities.Log;

public class PcHandler implements Handler {
	private final HashMap<String, PcData> players = new HashMap<String, PcData>();
	private final Configuration playerConfig;
	private static PcHandler instance;
	
	public static final String CONFIG_NAME_NPC = "players.yml";
	
	private PcHandler() {
		final ModuleHandler mh = ModuleHandler.getInstance();
		final RageMod rm = mh.plugin;
		
		mh.addHandler(this);
		
		playerConfig = new Configuration(rm, CONFIG_NAME_NPC);
	}
	
	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static PcHandler getInstance() {
		if (instance == null)
			instance = new PcHandler();

		return instance;
	}
	
	public boolean has(final String playerName) {
		return players.containsKey(playerName);
	}
	
	/**
	 * Adds entity data to the handler.
	 * @param data the entity data
	 * @return true if successful, false if it already contained the data
	 */
	public boolean addPlayerData(final PcData data) {
		if (data == null || players.containsValue(data))
			return false;
		
		players.put(data.getName(), data);
		return true;
	}
	
	public void removePlayerData(final String playerName) {
		players.remove(playerName);
	}
	
	public PcData getPlayerData(final String playerName) {
		return players.get(playerName);
	}

	@Override
	public boolean loadData() {
		final YamlConfiguration c = playerConfig.config;
		int counter = 0;
		
		try {
			for (final String s : c.getKeys(false)) {
				if (!(c.isConfigurationSection(s)))
					continue;
				
				final ConfigurationSection cs = c.getConfigurationSection(s);
				
				npcs.put(Integer.parseInt(s), NpcData.getDataFromConfigurationSection(cs));
				counter++;
			}
			
			Log.getInstance().info(new StringBuilder("Loaded ").append(counter).append(" entities.").toString());
			
			return true;
		} catch (YAMLException yaml) {
			Log.getInstance().severe("Failed loading the configuration for " + CONFIG_NAME_NPC);
			Log.getInstance().severe(yaml.getMessage());
			return false;
		}
	}
	@Override
	public boolean saveData() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
