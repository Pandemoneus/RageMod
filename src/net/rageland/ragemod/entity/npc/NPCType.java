package net.rageland.ragemod.entity.npc;

import java.util.HashMap;

public enum NpcType {
	BASIC("Citizen"),
	TRADER("Trader"),
	SHOPKEEPER("Shopkeeper"),
	BLACKSMITH("Blacksmith"),
	QUESTER("Questgiver"),
	INNKEEPER("Innkeeper"),
	GUARD("Guard");
	
	private final String name;
	private static final HashMap<String, NpcType> names = new HashMap<String, NpcType>();
	
	private NpcType(final String name) {
		this.name = name;
	}
	
	public static NpcType fromName(String name) {
		return names.get(name);
	}
	
	public String getName() {
		return name;
	}
	
	static {
		for (NpcType value : NpcType.values()) {
			names.put(value.getName(), value);
		}
	}
}
