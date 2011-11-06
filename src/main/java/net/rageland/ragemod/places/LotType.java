package net.rageland.ragemod.places;

import java.util.HashMap;
import java.util.Map;

public enum LotType {
	GENERIC("Generic"),
	WARRENS("Warrens"),
	MARKET("Market"),
	COAL("Coal"),
	IRON("Iron"),
	GOLD("Gold"),
	DIAMOND("Diamond");
	
	private final String name;
	private final static Map<String, LotType> names = new HashMap<String, LotType>();
	
	private LotType(String name) {
		this.name = name;
	}
	
	public String getCategoryName() {
		return name;
	}
	
	
	public static LotType byCategoryName(String categoryName) {
		return names.get(categoryName);
	}
	
    static {
        for (LotType cat : LotType.values()) {
            names.put(cat.getCategoryName(), cat);
        }
    }
}
