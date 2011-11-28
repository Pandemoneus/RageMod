package net.rageland.ragemod.entity.npc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.SharedData;
import net.rageland.ragemod.utilities.GeneralUtilities;

public class NPCData extends SharedData {
	// Persistant
	public final int uid;
	
	private final HashMap<String, Float> affinities = new HashMap<String, Float>();
	private String speechDataIdentifier;
	
	// Non-persistant
	public static final float MIN_AFFINITY = -10.0F;
	public static final float MAX_AFFINITY = 10.0F;
	
	public NPCData(final String name, final int uid, final Race race, final String speechDataIdentifier) {
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
	
	public NPCSpeechData getSpeech() {
		return NPCSpeechData.fromIdentifier(speechDataIdentifier);
	}
	
    public static NPCData getDataFromConfigurationSection(final ConfigurationSection section) {
    	if (section == null)
    		return null;
    	
    	final String name = section.getString("name");
    	final Race race = Race.fromName(section.getString("race"));
    	final String speechDataIdentifier = section.getString("speech");
    	
    	return new NPCData(name, Integer.parseInt(section.getName()), race, speechDataIdentifier);
    }

	public Map<String, Object> save() {
		final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("name", getName());
		map.put("race", getRace().getName());
		map.put("speech", speechDataIdentifier);
		
		return map;
	}
}
