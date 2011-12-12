package net.rageland.ragemod.utilities;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigurationUtilities {
	public static void feedStringMap(final Map<String, Object> map, final ConfigurationSection section) {
		if (map == null || section == null)
			return;
		
		final Set<String> set = section.getKeys(false);
		
		for (final String s : set) {
			if (section.isConfigurationSection(s)) {				
				map.put(s, section.getConfigurationSection(s).get(s));
			}
		}
	}
	
	public static LinkedHashMap<String, Object> feedNewStringMap(final ConfigurationSection section) {
		if (section == null)
			return null;
		
		final LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		feedStringMap(map, section);
		
		return map;
	}
	
	/**
	 * Saves a Map at the specified ConfigurationSection.
	 * @param section the ConfigurationSection
	 * @param map the Map, note that the String parameter in the map refers to the keys the objects are saved in
	 * @throws IllegalArgumentException when either the ConfigurationSection or the Map is null
	 */
	@SuppressWarnings("unchecked")
	public static void saveMap(final ConfigurationSection section, final Map<String, Object> map) throws IllegalArgumentException {
		if (section == null || map == null)
			throw new IllegalArgumentException("Both the configuration section and the map to save must not be null");
		
		final Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
		
		while (iter.hasNext()) {
			final Entry<String, Object> entry = iter.next();
			final Object value = entry.getValue();
			final String key = entry.getKey();
			
			if (value instanceof Map) {
				saveMap(section.createSection(key), (Map<String, Object>) value);
			} else if (value instanceof Collection) {
				saveCollection(section, (Collection<Object>) value);
			} else {
				section.set(key, value);
			}
		}
	}
	
	/**
	 * Saves a Collection at the specified ConfigurationSection.
	 * @param section the ConfigurationSection
	 * @param collection the Collection
	 * @throws IllegalArgumentException when either the ConfigurationSection or the Collection is null
	 */
	public static void saveCollection(final ConfigurationSection section, final Collection<Object> collection) throws IllegalArgumentException {
		if (section == null || collection == null)
			throw new IllegalArgumentException("Both the configuration section and the iterable object to save must not be null");
		
		final Iterator<Object> iter = collection.iterator();
		final String currentSectionPath = section.getCurrentPath();
		
		while (iter.hasNext()) {
			final Object value = iter.next();
			
			section.set(currentSectionPath, value);
		}
	}
}
