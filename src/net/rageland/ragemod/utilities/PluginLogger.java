package net.rageland.ragemod.utilities;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Server logger class
 * 
 * @author Pandemoneus - https://github.com/Pandemoneus
 */
public final class PluginLogger {
	private String pre;
	private boolean debugMode;
	private static PluginLogger instance;
	
	private static final HashSet<Logger> activeLoggers = new HashSet<Logger>();
	
	private static final Logger MINECRAFT = Logger.getLogger("Minecraft");
	private static final Logger PLUGIN = Logger.getAnonymousLogger();
	
	public PluginLogger() {
		pre = "";
	}

	public PluginLogger(JavaPlugin plugin, PluginLoggerMode mode) {
		instance = this;
		setLoggersActive(mode);
		
		if (plugin != null) {
			pre = "[" + plugin.toString() + "] ";
			
			try {
				final Handler handler = new FileHandler(plugin.getDataFolder().getAbsolutePath() + File.pathSeparator + "Log.txt", 10000, 10);
				handler.setLevel(Level.ALL);
				PLUGIN.addHandler(handler);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void setLoggersActive(PluginLoggerMode mode) {
		activeLoggers.clear();
		
		switch (mode) {
		case ALL:
			activeLoggers.add(MINECRAFT);
			activeLoggers.add(PLUGIN);
			break;
		case MINECRAFT:
			activeLoggers.add(MINECRAFT);
			break;
		case PLUGIN:
			activeLoggers.add(PLUGIN);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Returns an instance of this class.
	 * Note that the class needs at least one instance for this to work.
	 * @return an instance of this class, otherwise null
	 */
	public static PluginLogger getInstance() {
		return instance;
	}

	/**
	 * Displays a message with info level.
	 * @param message the message to display
	 */
	public void info(String message) {
		for (Logger logger : activeLoggers)
			logger.log(Level.INFO, new StringBuilder(pre).append(message).toString());
	}

	/**
	 * Displays a message with warning level.
	 * @param message the message to display
	 */
	public void warning(String message) {
		for (Logger logger : activeLoggers)
			logger.log(Level.WARNING, new StringBuilder(pre).append(message).toString());
	}

	/**
	 * Displays a message with severe level.
	 * 
	 * @param message the message to display
	 */
	public void severe(String message) {
		for (Logger logger : activeLoggers)
			logger.log(Level.SEVERE, new StringBuilder(pre).append(message).toString());
	}
	
	/**
	 * Displays a debug message with info level if the debug mode is enabled.
	 * @param message the message to display
	 */
	public void debug(String message) {
		if (debugMode)
			for (Logger logger : activeLoggers)
				logger.log(Level.INFO, new StringBuilder(pre).append(message).toString());
	}
	
	/**
	 * Enables/disables debug message logging.
	 * @param flag the flag
	 */
	public void setDebugging(boolean flag) {
		debugMode = flag;
	}
	
	public enum PluginLoggerMode {
		/**
		 * Log to all loggers.
		 */
		ALL,
		
		/**
		 * Log to the plugin's logger.
		 */
		PLUGIN,
		
		/**
		 * Log to Minecraft console.
		 */
		MINECRAFT,
		
		/**
		 * Don't log.
		 */
		NONE;
	}
}
