package net.rageland.ragemod.entity.npc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PlayerData;

// Represents a single phrase uttered by an NPC
public class NPCPhrase {
	private final String message;
	private final NPCData npcData;

	public NPCPhrase(final String message, final NPCData npcData) {
		this.message = message;
		this.npcData = npcData;
	}

	public String getMessage(final PlayerData player, final Race race) {
		if (player == null || race == null)
			return null;
		
		if (!race.getLanguage().isForeign || player.getLanguageSkill(race) >= 100)
			return parse(message, player);
		else
			return getTranslation(player, race);
	}

	private String getTranslation(final PlayerData player, final Race race) {		return parse(race.getLanguage().translate(message, player), player);
	}

	private String parse(String toParse, final  PlayerData player) {
		toParse = toParse.replace("$player", player.getName());
		toParse = toParse.replace("$myname", npcData.getName());
		return toParse;
	}

}
