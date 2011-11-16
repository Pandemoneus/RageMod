package net.rageland.ragemod.entity.npc;

import java.util.HashMap;

public enum NPCType {
	BASIC("Citizen"),
	TRADER("Trader"),
	SHOPKEEPER("Shopkeeper"),
	BLACKSMITH("Blacksmith"),
	QUESTER("Questgiver"),
	INNKEEPER("Innkeeper"),
	GUARD("Guard");
	
	private final String name;
	private static final HashMap<String, NPCType> names = new HashMap<String, NPCType>();
	
	private NPCType(final String name) {
		this.name = name;
	}
	
	public static NPCType fromName(String name) {
		return names.get(name);
	}
	
	public String getName() {
		return name;
	}
	
	static {
		for (NPCType value : NPCType.values()) {
			names.put(value.getName(), value);
		}
	}
}
