package net.rageland.ragemod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import net.rageland.ragemod.utilities.Log;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {
	private final JavaPlugin plugin;
	private final File file;
	private YamlConfiguration config;
	
	/**
	 * Creates a new configuration together with its own file.
	 * @param plugin the plugin the configuration belongs to
	 * @param fileName the name of the file, should end with .yml
	 */
	public Configuration(final JavaPlugin plugin, final String fileName) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), fileName);
		
		reload();
	}
	
	/**
	 * Reloads the configuration from disk.
	 */
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		 
		final InputStream defaultStream = plugin.getResource(file.getName());
	    if (defaultStream != null) {
	        final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defaultStream);
	 
	        config.setDefaults(defConfig);
	    }
	}
	
	/**
	 * Saves the configuration to the disk.
	 * @return true if successful
	 */
	public boolean save() {
		try {
	        config.save(file);
	        return true;
	    } catch (IOException ex) {
	        Log.getInstance().severe("Failed saving configuration to" + file);
	        Log.getInstance().severe(ex.getMessage());
	        return false;
	    }
	}
	
	/**
	 * Returns the YamlConfiguration.
	 * @return the YamlConfiguration
	 */
	public YamlConfiguration getYamlConfig() {
		return config;
	}
}
