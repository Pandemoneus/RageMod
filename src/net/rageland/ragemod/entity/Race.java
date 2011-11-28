package net.rageland.ragemod.entity;

import java.util.HashMap;

import net.rageland.ragemod.text.Language;

public enum Race {
	HUMAN("Human", new Language()),
	CREEPSPAWN("Creepspawn", new Language()),
	PIGMAN("Pigman", new Language()),
	BENALI("Benali", new Language()),
	AVARIAN("Avarian", new Language()),
	UNKNOWN("Unknown", new Language());
	
	private final String name;
	private final Language lang;
	
	private static HashMap<String, Race> names = new HashMap<String, Race>();
	
	private Race(final String name, final Language lang) {
		this.name = name;
		this.lang = lang;
	}
	
	public static Race fromName(final String name) {
		return names.get(name);
	}
	
	public String getName() {
		return name;
	}
	
	public Language getLanguage() {
		return lang;
	}
	
	static {
		for (Race r : Race.values()) {
			names.put(r.getName(), r);
		}
	}
}
