package net.rageland.ragemod.entity.npc.race;

import net.rageland.ragemod.text.Language;

public abstract class Race {
	private final String name;
	private final Language language;

	public Race(final String name, final Language language) {
		this.name = name;
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public Language getLanguage() {
		return language;
	}
}
