package net.rageland.ragemod.utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigurationUtilities {
	public static void feedMap(final HashMap<String, Object> map, final ConfigurationSection section) {
		if (map == null || section == null)
			return;
		
		final Set<String> set = section.getKeys(false);
		
		for (final String s : set) {
			if (section.isConfigurationSection(s)) {				
				map.put(s, section.getConfigurationSection(s).get(s));
			}
		}
	}
	
	public static LinkedHashMap<String, Object> feedNewMap(final ConfigurationSection section) {
		if (section == null)
			return null;
		
		final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		feedMap(map, section);
		
		return map;
	}
}
