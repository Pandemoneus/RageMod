package net.rageland.ragemod;

import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.rageland.ragemod.command.CommandHandler;
import net.rageland.ragemod.data.Factions;
import net.rageland.ragemod.data.Lots;
import net.rageland.ragemod.data.Tasks;
import net.rageland.ragemod.data.Towns;
import net.rageland.ragemod.database.RageDB;
import net.rageland.ragemod.entity.npc.NPCHandler;
import net.rageland.ragemod.entity.player.Players;
import net.rageland.ragemod.quest.QuestManager;
import net.rageland.ragemod.text.Languages;
import net.rageland.ragemod.text.Message;
import net.rageland.ragemod.utilities.Log;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * RageMod for Bukkit
 * 
 * @author TheIcarusKid
 * @author Pandemoneus
 */
public class RageMod extends JavaPlugin {

	private RMPlayerListener playerListener;
	private RMBlockListener blockListener;
	private RMEntityListener entityListener;
	private static RageMod plugin;
    private CommandHandler commandHandler = new CommandHandler(this);

	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

	// Plugin info
	public String name;
	public String version;
	public static Log logger;

	// Global data
	public Lots lots;
	public Players players;
	public Towns towns;
	public Tasks tasks;
	public Factions factions;
	public Languages languages;

	// Semi-static data and methods
	public RageConfig config;
	public RageDB database;
	public RageZones zones;
	public Message message;
	public NPCHandler npcManager;
	public QuestManager questManager;

	// Permission and economy support
	public static Permission permission = null;
	public static Economy economy = null;

	public void onEnable() {
		plugin = this;
		if (!setupPermission()) {
			logger.severe("No valid permission-supporting plugin detected! Disabling plugin...");
			this.setEnabled(false);
			return;
		}

		if (!setupEconomy()) {
			logger.severe("No valid economy-supporting plugin detected! Disabling plugin...");
			this.setEnabled(false);
			return;
		}

		registerPluginInfo();
		initializeVariables();
		registerEvents();
		registerCommands();
		loadDatabaseData();
		startScheduledTasks();
		runDebugTests();

		logger.info(new StringBuilder(name).append(" loaded without errors.").toString());
	}

	public void onDisable() {
		// this.npcManager.despawnAll();
		logger.info(new StringBuilder(name).append(" disabled.").toString());
	}
	
	public static RageMod getInstance() {
		if (plugin == null)
			plugin = new RageMod();

		return plugin;
	}

	public boolean isDebugging(final Player player) {
		if (debugees.containsKey(player)) {
			return debugees.get(player);
		}
		
		return false;
	}

	public void setDebugging(final Player player, final boolean value) {
		debugees.put(player, value);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
    
    private void registerCommands() {
        // Page 1
        // commandHandler.addCommand(new PathsCommand(this));
    }

	private void runDebugTests() {
		logger.info("Number of lots:" + lots.getAll().size());
	}

	private void registerPluginInfo() {
		PluginDescriptionFile pdf = getDescription();
		name = pdf.getName();
		version = pdf.getVersion();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_PORTAL, playerListener, Priority.Normal, this);

		pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Normal, this);

		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.ENTITY_INTERACT, entityListener, Priority.High, this);
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
	}

	private void initializeVariables() {
		playerListener = new RMPlayerListener(this);
		blockListener = new RMBlockListener(this);
		entityListener = new RMEntityListener(this);
		config = new RageConfig(this);
		database = new RageDB(this, config);
		logger = new Log(this);

		lots = new Lots(this);
		players = new Players(this);
		towns = new Towns(this);
		tasks = new Tasks(this);
		factions = new Factions();
		languages = new Languages(this);

		zones = new RageZones(this, config);
		npcManager = new NPCHandler(this);
		questManager = new QuestManager();
		message = new Message(this);
	}

	private void loadDatabaseData() {
		towns.loadTowns();
		lots.loadLots();
		tasks.loadTaskTimes();
		languages.loadDictionaries();
		npcManager.associateLocations();
		npcManager.spawnAllInstances();
	}

	private void startScheduledTasks() {
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new RageTimer(this), 20, 20);
	}

	private Boolean setupPermission() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}

		return (permission != null);
	}

	private Boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}
