package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PlayerData;

// Represents a single phrase uttered by an NPC
public class NPCPhrase {
	private String message;
	private ArrayList<String> translations;
	private int id;
	private NPCData npcData;
	private RageMod plugin;
	private boolean isDynamic;

	public NPCPhrase(String message, int id, RageMod plugin, boolean isDynamic, NPCData npcData) {
		this.message = message;
		this.id = id;
		this.plugin = plugin;
		this.isDynamic = isDynamic;
		this.npcData = npcData;
	}

	public String getMessage(PlayerData playerData) {
		if (npcData.getRace() == Race.HUMAN || playerData.getLanguageSkill(npcData.getRace()) == 100)
			return parse(message, playerData);
		else
			return getTranslation(playerData);
	}

	public int getID() {
		return id;
	}

	private String getTranslation(PlayerData playerData) {
		// Check to see if the translations have been set up yet
		if (translations == null)
			translations = plugin.languages.translate(message, npcData.getRace());

		int skill = playerData.getLanguageSkill(npcData.getRace());

		if (skill >= 100)
			return parse(message, playerData);
		else
			return parse(translations.get((int) (((skill / 25) - 3) * -1)), playerData); // This maps 0 to 3, 25 to 2, 50 to 1, and 75 to 0
	}

	private String parse(String toParse, PlayerData playerData) {
		if (!isDynamic)
			return toParse;

		toParse = toParse.replace("$player$", playerData.getName());
		toParse = toParse.replace("$myname$", npcData.getName());
		return toParse;
	}

}
