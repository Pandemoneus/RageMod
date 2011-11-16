package net.rageland.ragemod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration {
	public final RageMod plugin;
	public final File file;
	public YamlConfiguration config;
	
	public Configuration(final RageMod plugin, final String fileName) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), fileName);
		
		reload();
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		 
		final InputStream defaultStream = plugin.getResource(file.getName());
	    if (defaultStream != null) {
	        final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defaultStream);
	 
	        config.setDefaults(defConfig);
	    }
	}
	
	public boolean save() {
		try {
	        config.save(file);
	        return true;
	    } catch (IOException ex) {
	        plugin.logger.severe("Failed saving configuration to" + file);
	        plugin.logger.severe(ex.getMessage());
	        return false;
	    }
	}
}
