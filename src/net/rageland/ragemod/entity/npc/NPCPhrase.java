package net.rageland.ragemod.entity.npc;

import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PcData;

public class NpcPhrase {
	private final String message;

	public NpcPhrase(final String message) {
		this.message = message;
	}

	public String getMessage(final NpcData npc, final PcData player) {
		if (player == null || npc == null)
			return null;
		
		final Race race = npc.getRace();
		
		if (!race.getLanguage().isForeign || player.getLanguageSkill(race) >= 100)
			return parse(npc, player, message);
		else
			return getTranslation(npc, player);
	}

	private String getTranslation(final NpcData npc, final PcData player) {		return parse(npc, player, npc.getRace().getLanguage().translate(message, player));
	}

	private String parse(final NpcData npc, final PcData player, String toParse) {
		toParse = toParse.replace("$player", player.getName());
		toParse = toParse.replace("$myname", npc.getName());
		return toParse;
	}

}
