package net.rageland.ragemod;

import java.util.HashMap;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.rageland.ragemod.data.Tasks;
import net.rageland.ragemod.data.Towns;
import net.rageland.ragemod.database.DatabaseHandler;
import net.rageland.ragemod.entity.npc.NPCHandler;
import net.rageland.ragemod.entity.player.PlayerHandler;
import net.rageland.ragemod.factions.Faction;
import net.rageland.ragemod.quest.QuestManager;
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
	private RMCitizensListener citizensListener;
	private RMCitizensNPCListener citizensNPCListener;
	private static RageMod instance;
	private ModuleHandler modules;

	private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

	// Plugin info
	public String name;
	public String version;

	// Global data
	public PlayerHandler players;
	public Towns towns;
	public Tasks tasks;
	public Faction factions;

	// Semi-static data and methods
	public RageConfig config;
	public DatabaseHandler database;
	public Message message;
	public QuestManager questManager;

	// Permission and economy support
	public static Permission permission = null;
	public static Economy economy = null;

	public void onEnable() {
		instance = this;
		registerPluginInfo();
		new Log(this);
		
		if (!setupPermission()) {
			Log.getInstance().severe("No valid permission-supporting plugin detected! Disabling plugin...");
			this.setEnabled(false);
			return;
		}

		if (!setupEconomy()) {
			Log.getInstance().severe("No valid economy-supporting plugin detected! Disabling plugin...");
			this.setEnabled(false);
			return;
		}

		initializeVariables();
		registerHandlers();
		registerEvents();
		registerCommands();
		loadDatabaseData();
		startScheduledTasks();

		Log.getInstance().info(new StringBuilder(name).append(" loaded without errors.").toString());
	}

	public void onDisable() {
		modules.saveAll();
		getServer().getScheduler().cancelTasks(this);
		Log.getInstance().info(new StringBuilder(name).append(" disabled.").toString());
	}
	
	public static RageMod getInstance() {
		if (instance == null)
			instance = new RageMod();

		return instance;
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
    
    private void registerCommands() {
        // Page 1
        // commandHandler.addCommand(new PathsCommand(this));
    }

	private void registerPluginInfo() {
		PluginDescriptionFile pdf = getDescription();
		name = pdf.getName();
		version = pdf.getVersion();
	}
	
	private void registerHandlers() {
		modules = ModuleHandler.getInstance();
		modules.addHandler(NPCHandler.getInstance());
		modules.loadAll();
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
		
		pm.registerEvent(Event.Type.CUSTOM_EVENT, citizensListener, Priority.Lowest, this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT, citizensNPCListener, Priority.Lowest, this);
	}

	private void initializeVariables() {
		playerListener = new RMPlayerListener(this);
		blockListener = new RMBlockListener(this);
		entityListener = new RMEntityListener(this);
		citizensListener = new RMCitizensListener();
		citizensNPCListener = new RMCitizensNPCListener();
		config = new RageConfig(this);
		//database = new DatabaseHandler(this, config);

		players = new PlayerHandler(this);
		tasks = new Tasks(this);
		factions = new Faction();

		questManager = new QuestManager();
		message = new Message(this);
	}

	private void loadDatabaseData() {
		//towns.loadTowns();
		//tasks.loadTaskTimes();
	}

	private void startScheduledTasks() {
		//this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new RageTimer(this), 20, 20);
	}

	private boolean setupPermission() {
		final RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}

		return (permission != null);
	}

	private boolean setupEconomy() {
		final RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}
}
