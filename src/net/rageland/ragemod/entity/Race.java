package net.rageland.ragemod.entity;

import java.util.HashMap;

import net.rageland.ragemod.text.Language;

public enum Race {
	HUMAN("Human", new Language("English", false)),
	CREEPSPAWN("Creepspawn", new Language("Creeptongue", true)),
	PIGMAN("Pigman", new Language("Gronuk", true)),
	BENALI("Benali", new Language("Benali", true)),
	AVARIAN("Avarian", new Language("Avialese", true)),
	UNKNOWN("Unknown", new Language("Unknown", false));
	
	private final String name;
	private final Language lang;
	
	private static HashMap<String, Race> names = new HashMap<String, Race>();
	private static HashMap<Language, Race> languages = new HashMap<Language, Race>();
	
	private Race(final String name, final Language lang) {
		this.name = name;
		this.lang = lang;
	}
	
	public static Race fromName(final String name) {
		return names.get(name);
	}
	
	public static Race fromLanguage(final Language lang) {
		return languages.get(lang);
	}
	
	public String getName() {
		return name;
	}
	
	public Language getLanguage() {
		return lang;
	}
	
	static {
		for (final Race r : Race.values()) {
			names.put(r.getName(), r);
			languages.put(r.getLanguage(), r);
		}
	}
}
