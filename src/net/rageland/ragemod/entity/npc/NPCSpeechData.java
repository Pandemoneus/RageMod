package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;
import net.rageland.ragemod.entity.player.PcData;
import net.rageland.ragemod.utilities.GeneralUtilities;

/**
 * This class represents all phrases one NPC can utter.
 * NpcSpeechData consists of an initial greeting, follow-up greetings and miscellaneous messages.
 * Miscellaneous messages are uttered in the order they are passed.
 * @author Pandemoneus
 *
 */
public class NpcSpeechData {
	private NpcData npc;
	private boolean isAssociatedWithNpc;

	private int messagePointer = 0;
	
	public static final int MESSAGE_AFFINITY_MIN = -2;
	public static final int MESSAGE_AFFINITY_MAX = 2;
	public static final int MESSAGE_AFFINITY_SCOPE = 5;
	
	private NpcPhrase initialGreeting;
	private final ArrayList<NpcPhrase> messages = new ArrayList<NpcPhrase>();
	private final NpcPhrase[] followups = new NpcPhrase[MESSAGE_AFFINITY_SCOPE];

	/**
	 * Constructs new {@link NpcSpeechData}.
	 * @param initialGreeting the initial greeting
	 * @param messages the miscellaneous messages
	 * @param followups the follow-up greetings, the array must be of size {@link #MESSAGE_AFFINITY_SCOPE}
	 */
	public NpcSpeechData(final String initialGreeting, final ArrayList<String> messages, final String[] followups) {
		//initial greeting
		if (initialGreeting != null)
			this.initialGreeting = new NpcPhrase(initialGreeting);
		
		//messages
		if (messages != null) {
			for (final String s : messages) {
				if (s != null)
					this.messages.add(new NpcPhrase(s));
			}
		}
		
		//follow ups
		if (followups != null && followups.length == MESSAGE_AFFINITY_SCOPE) {
			for (int i = MESSAGE_AFFINITY_MIN; i <= MESSAGE_AFFINITY_MAX; i++) {
				setFollowUpMessage(i, followups[i - MESSAGE_AFFINITY_MIN]);
			}
		}
	}
	
	/**
	 * Sets a follow-up message for a specific affinity level.
	 * The affinity level must be between {@link #MESSAGE_AFFINITY_MIN} and {@link #MESSAGE_AFFINITY_MAX}.
	 * @param affinityLevel the affinity level
	 * @param message the message to set
	 */
	public void setFollowUpMessage(final int affinityLevel, final String message) {
		if (!GeneralUtilities.isBetween(affinityLevel, MESSAGE_AFFINITY_MIN, MESSAGE_AFFINITY_MAX))
			return;
		
		followups[affinityLevel] = new NpcPhrase(message == null ? "" : message);
	}
	
	/**
	 * Adds more messages.
	 * @param message the message
	 */
	public void addMessage(final String message) {
		if (message == null)
			return;
		
		messages.add(new NpcPhrase(message));
	}

	/**
	 * Returns the next miscellaneous message.
	 * If there are no next messages, then the very first message will be returned.
	 * If there is no message stored at all, then null will be returned.
	 * @param playerData the player
	 * @return the next miscellaneous message
	 */
	public String getNextMessage(final PcData playerData) {
		if (messages.size() > 0) {
			final String message = messages.get(messagePointer).getMessage(npc, playerData);

			messagePointer++;
			if (messagePointer == messages.size())
				messagePointer = 0;

			return message;
		} else {
			return null;
		}
	}

	/**
	 * Returns the initial greeting.
	 * @param playerData the player
	 * @return the initial greeting
	 */
	public String getInitialGreeting(final PcData playerData) {
		if (initialGreeting == null)
			return null;
		
		return initialGreeting.getMessage(npc, playerData);
	}
	
	/**
	 * Returns whether the {@link NpcSpeechData} is associated with a {@link NpcData}.
	 * @return true when the {@link NpcSpeechData} is associated with a {@link NpcData}
	 */
	public boolean isAssociatedWithNpc() {
		return isAssociatedWithNpc;
	}
	
	/**
	 * Associates this {@link NpcSpeechData} with a {@link NpcData}.
	 * @param npcData the {@link NpcData}
	 */
	public void associateWith(final NpcData npcData) {
		npc = npcData;
		isAssociatedWithNpc = npc != null;
	}

	public String getFollowupGreeting(final PcData playerData) {
		// Convert the -10 to 10 affinity float value to the -2 to 2 affinity integer code
		//int affinityCode = GeneralUtilities.getAffinityCode(playerData.getAffinity();

		//return followups.get(affinityCode).getMessage(playerData);
		return "";
	}
}
