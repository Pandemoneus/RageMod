package net.rageland.ragemod.entity.npc;

import net.rageland.ragemod.RageMod;

import org.bukkit.ChatColor;

public final class Trader extends BasicNPC {

	private Trader(final RageMod plugin, final NPCData data, final ChatColor nameColor, final NPCType type, final NPCSpeechData speech) {
		super(plugin, data, nameColor, type, speech);
	}
	
	public static BasicNPC fromNPCData(final NPCData data, final ChatColor nameColor) {
		if (data == null)
			return null;
		
		final ChatColor tmp = nameColor == null ? ChatColor.WHITE : nameColor;
		
		return new BasicNPC(data.plugin, data, tmp, NPCType.TRADER, data.getSpeech());
	}
}
