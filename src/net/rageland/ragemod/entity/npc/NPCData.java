package net.rageland.ragemod.entity.npc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.SharedData;
import net.rageland.ragemod.utilities.ConfigurationUtilities;
import net.rageland.ragemod.utilities.GeneralUtilities;

public class NpcData extends SharedData {
	// Persistant
	public final int uid;
	
	private final HashMap<String, Float> affinities = new HashMap<String, Float>();
	private String speechDataIdentifier;
	
	// Non-persistant
	public static final float MIN_AFFINITY = -10.0F;
	public static final float MAX_AFFINITY = 10.0F;
	
	public NpcData(final String name, final int uid, final Race race, final String speechDataIdentifier) {
		super(name, race);
		this.uid = uid;
		this.speechDataIdentifier = speechDataIdentifier == null ? "" : speechDataIdentifier;
	}
	
	/**
	 * Sets the affinity of a NPC towards a player.
	 * The player does not have to be online for this to work.
	 * @param player the player
	 * @param affinity the affinity, must be between MIN_AFFINITY and MAX_AFFINITY
	 */
	public void setAffinityToPlayer(final OfflinePlayer player, final float affinity) {
		if (player == null || !GeneralUtilities.isBetween(affinity, MIN_AFFINITY, MAX_AFFINITY))
			return;
		
		affinities.put(player.getName(), affinity);
	}
	
	public float getAffinityToPlayer(final OfflinePlayer player) {
		if (player == null)
			return 0;
		
		return affinities.get(player.getName());
	}
	
	public NpcSpeechData getSpeech() {
		return NpcSpeechData.fromIdentifier(speechDataIdentifier);
	}
	
    public static NpcData getDataFromConfigurationSection(final ConfigurationSection section) {
    	if (section == null)
    		return null;
    	
    	final String name = section.getString("name");
    	final Race race = Race.fromName(section.getString("race"));
    	final String speechDataIdentifier = section.getString("speech");
    	
    	final NpcData data = new NpcData(name, Integer.parseInt(section.getName()), race, speechDataIdentifier);
    	
    	if (section.getConfigurationSection("affinity") != null) {
	    	final LinkedHashMap<String, Object> map = ConfigurationUtilities.feedNewStringMap(section.getConfigurationSection("affinity"));
	
			for (final Entry<String, Object> entry : map.entrySet()) {
				data.setAffinityToPlayer(Bukkit.getOfflinePlayer(entry.getKey()), (Float) entry.getValue());
			}
    	}
    	
    	return data;
    }

	public Map<String, Object> save() {
		final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("name", getName());
		map.put("race", getRace().getName());
		map.put("speech", speechDataIdentifier);
		map.put("affinities", affinities);
		
		return map;
	}
}
