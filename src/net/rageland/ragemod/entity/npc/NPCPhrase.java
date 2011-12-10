package net.rageland.ragemod.entity.npc;

import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PlayerData;

public class NPCPhrase {
	private final String message;

	public NPCPhrase(final String message, final NPCData npcData) {
		this.message = message;
	}

	public String getMessage(final NPCData npc, final PlayerData player) {
		if (player == null || npc == null)
			return null;
		
		final Race race = npc.getRace();
		
		if (!race.getLanguage().isForeign || player.getLanguageSkill(race) >= 100)
			return parse(npc, player, message);
		else
			return getTranslation(npc, player);
	}

	private String getTranslation(final NPCData npc, final PlayerData player) {		return parse(npc, player, npc.getRace().getLanguage().translate(message, player));
	}

	private String parse(final NPCData npc, final PlayerData player, String toParse) {
		toParse = toParse.replace("$player", player.getName());
		toParse = toParse.replace("$myname", npc.getName());
		return toParse;
	}

}
