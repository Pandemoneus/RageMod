package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PcData;
import net.rageland.ragemod.utilities.ConfigurationUtilities;
import net.rageland.ragemod.utilities.GeneralUtilities;

public class NpcSpeechData {
	public final NpcData npc;
	
	private NpcPhrase initialGreeting;
	private final ArrayList<NpcPhrase> messages = new ArrayList<NpcPhrase>();
	private final ArrayList<NpcPhrase> followups = new ArrayList<NpcPhrase>(5);

	private int messagePointer = 0;

	public NpcSpeechData(final NpcData npc) {
		this.npc = npc;
	}
	
	public static NpcSpeechData fromIdentifier(final String identifier) {
		return allData.get(identifier);
	}

	public String getNextMessage(final PcData playerData) {
		if (messages.size() > 0) {
			String message = messages.get(messagePointer).getMessage(npc, playerData);

			messagePointer++;
			if (messagePointer == messages.size())
				messagePointer = 0;

			return message;
		} else {
			return "I am supposed to have text, but somehow failed loading it.";
		}
	}

	public String getInitialGreeting(final PcData playerData) {
		if (initialGreeting == null)
			return null;
		
		return initialGreeting.getMessage(npc, playerData);
	}

	// Gets the message for a followup encounter
	public String getFollowupGreeting(PcData playerData) {
		// Convert the -10 to 10 affinity float value to the -2 to 2 affinity integer code
		//int affinityCode = GeneralUtilities.getAffinityCode(playerData.getAffinity();

		//return followups.get(affinityCode).getMessage(playerData);
		return "";
	}

	public void addMessage(final NpcPhrase message) {
		messages.add(message);
	}

    public static NpcSpeechData getDataFromConfigurationSection(final ConfigurationSection section) {
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
}
