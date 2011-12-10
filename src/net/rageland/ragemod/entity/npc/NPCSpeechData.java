package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;

import net.rageland.ragemod.Configuration;
import net.rageland.ragemod.entity.player.PlayerData;
import net.rageland.ragemod.utilities.GeneralUtilities;

public class NPCSpeechData {
	private static HashMap<String, NPCSpeechData> allData = new HashMap<String, NPCSpeechData>();
	public final String identifier;
	public final NPCData npc;
	
	private NPCPhrase initialGreeting;
	private final ArrayList<NPCPhrase> messages = new ArrayList<NPCPhrase>();
	private final ArrayList<NPCPhrase> followups = new ArrayList<NPCPhrase>(5);

	private int messagePointer = 0;

	public NPCSpeechData(final String identifer, final NPCData npc) {
		this.identifier = identifer;
		this.npc = npc;

		allData.put(identifier, this);
	}
	
	public static NPCSpeechData fromIdentifier(final String identifier) {
		return allData.get(identifier);
	}

	public String getNextMessage(final PlayerData playerData) {
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

	public String getInitialGreeting(final PlayerData playerData) {
		if (initialGreeting == null)
			return null;
		
		return initialGreeting.getMessage(npc, playerData);
	}

	// Gets the message for a followup encounter
	public String getFollowupGreeting(PlayerData playerData) {
		// Convert the -10 to 10 affinity float value to the -2 to 2 affinity integer code
		//int affinityCode = GeneralUtilities.getAffinityCode(playerData.getAffinity();

		//return followups.get(affinityCode).getMessage(playerData);
		return "";
	}

	public void addMessage(final NPCPhrase message) {
		messages.add(message);
	}
}
