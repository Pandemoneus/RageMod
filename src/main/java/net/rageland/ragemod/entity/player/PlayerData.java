package net.rageland.ragemod.entity.player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.RageZones.Zone;
import net.rageland.ragemod.data.Lot;
import net.rageland.ragemod.data.NPCInstance;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.Town;
import net.rageland.ragemod.quest.PlayerQuestData;

// TODO: Should I be storing IDs for towns and such for all player data?  Then I would call the PlayerTowns hash
//		 every time I need to retrieve the name.

public class PlayerData implements Owner {
	// ***** DATABASE VALUES *****

	// Basic data
	private String playerName;
	private int playerId;
	private int factionId;
	private boolean isMember = false;
	private Date memberExpiration;
	private float bounty;
	private float extraBounty;
	private boolean persistentInDatabase;

	// Home (used for capitol lots)
	private Location homeLocation;
	public Timestamp homeLastUsed;

	// Spawn (used for player town beds)
	private Location spawnLocation;
	public Timestamp spawnLastUsed;

	// Town info
	private String townName = "";

	// NPCTown info
	private String npcTownName = "";

	// Lot info
	private ArrayList<Lot> ownedLots = new ArrayList<Lot>();

	// Messages
	private String logonMessageQueue = "";

	// NPC-related info
	private HashMap<Integer, Integer> languageSkill; // Skill in each language (id 1-4), up to 100
	private HashSet<Integer> npcInteractions; // List of which NPCInstances the player has interacted with
	private HashSet<Integer> newNPCInteractions; // List of interactions from this session
	private HashMap<Integer, Float> npcAffinities; // NPCs' friendliness towards player

	// ***** STATE (Non-DB) VALUES *****

	// Current location
	public Zone currentZone;
	public Town currentTown;
	public boolean isInCapitol;
	public Timestamp enterLeaveMessageTime = null; // Prevent message spam by only allowing a message every 10 seconds (while people work on walls, etc)

	// Quest data
	public PlayerQuestData activeQuestData = new PlayerQuestData();

	// Misc.
	private RageMod plugin;
	private Player player;

	// Constructor
	public PlayerData(RageMod plugin) {
		this.plugin = plugin;
		this.languageSkill = new HashMap<Integer, Integer>();
		this.npcInteractions = new HashSet<Integer>();
		this.newNPCInteractions = new HashSet<Integer>();
	}

	// Attaches the Player object for sending messages, etc
	public void attachPlayer(Player player) {
		this.player = player;
	}

	// Sets the spawn location when bed clicked
	public void setSpawn(Location location) {
		spawnLocation = location;
	}

	// Returns a Location object of the spawn location
	public Location getSpawn() {
		return spawnLocation;
	}

	// Sets the home location when bed clicked
	public void setHome(Location location) {
		homeLocation = location;
	}

	// Returns a Location object of the home location
	public Location getHome() {
		return homeLocation;
	}

	// Clears the spawn location when bed is broken	
	public void clearSpawn() {
		spawnLocation = null;
	}

	// Clears the home location when bed is broken	
	public void clearHome() {
		homeLocation = null;
	}

	// Checks whether the current location is inside one of the player's lots
	public boolean isInsideOwnLot(Location location) {
		if (!location.getWorld().getName().equalsIgnoreCase("world"))
			return false;

		for (Lot lot : this.ownedLots) {
			if (lot.isInside(location)) {
				return true;
			}
		}

		// The location was not in any of the player's lots
		return false;
	}

	// Returns the player name with special color tags to be interpreted by the messaging methods
	public String getCodedName() {
		char colorCode;

		if (factionId != 0)
			colorCode = plugin.factions.getColorCode(this.factionId);
		else if (RageMod.permission.playerInGroup("world", this.playerName, "Owner"))
			colorCode = 'o';
		else if (RageMod.permission.playerInGroup("world", this.playerName, "Admin"))
			colorCode = 'a';
		else if (RageMod.permission.playerInGroup("world", this.playerName, "Moderator"))
			colorCode = 'm';
		else if (RageMod.permission.playerInGroup("world", this.playerName, "Citizen"))
			colorCode = 'c';
		else
			colorCode = 't'; // tourist

		return "<p" + colorCode + ">" + this.playerName + "</p" + colorCode + ">";
	}

	// Updates the playerData in the database
	public void update() {
		plugin.database.playerQueries.updatePlayer(this);
	}

	// Gets the skill level for the specified language
	public int getLanguageSkill(int id) {
		return this.languageSkill.get(id);
	}

	// Sets the skill level for the specified language
	public void setLanguageSkill(int id, int value) {
		this.languageSkill.put(id, value);
	}

	// Records a player's interaction with an NPC instance
	// Returns true if new encounter with instance
	public void recordNPCInteraction(NPCInstance instance) {
		// Record the interaction
		this.npcInteractions.add(instance.getID());
		this.newNPCInteractions.add(instance.getID());

		// Increase the language skill, if applicable
		if (instance.getRaceID() != plugin.config.NPC_HUMAN_ID && this.languageSkill.get(instance.getRaceID()) < 100) {
			this.languageSkill.put(instance.getRaceID(), this.languageSkill.get(instance.getRaceID()) + 1);

			// Notify the player of increased language skill
			if (player != null)
				plugin.message.languageUp(player, instance.getRaceID(), 1, this.getLanguageSkill(instance.getRaceID()));
		}

		// Increase the affinity
		int npcID = instance.getNPCid();
		if (npcAffinities.containsKey(npcID)) {
			npcAffinities.put(npcID, npcAffinities.get(npcID) + plugin.config.NPC_AFFINITY_GAIN_TALK);
			if (npcAffinities.get(npcID) > plugin.config.NPC_AFFINITY_MAX)
				npcAffinities.put(npcID, plugin.config.NPC_AFFINITY_MAX);
		} else
			npcAffinities.put(npcID, instance.getDefaultAffinity() + plugin.config.NPC_AFFINITY_GAIN_TALK);
	}

	// Gets all NPC interactions
	public HashSet<Integer> getNewInteractions() {
		return this.newNPCInteractions;
	}

	// Sets the instance list
	public void setInteractions(HashSet<Integer> interactions) {
		this.npcInteractions = interactions;
	}

	// Returns whether or not the player has interacted with this instance yet
	public boolean isNewInteraction(int instanceID) {
		return !npcInteractions.contains(instanceID);
	}

	// Sets the instance list
	public void setAffinities(HashMap<Integer, Float> affinities) {
		this.npcAffinities = affinities;
	}

	// Returns all affinity values
	public HashMap<Integer, Float> getAffinities() {
		return this.npcAffinities;
	}

	// Returns whether or not the player has talked to an NPC before
	public boolean isFirstMeeting(int npcID) {
		return !npcAffinities.containsKey(npcID);
	}

	// Returns affinity for a particular NPC
	public float getAffinity(int npcID) {
		return npcAffinities.get(npcID);
	}
	
	public String getName() {
		return playerName;
	}

	@Override
	public HashSet<OwnedPlace> getOwnedPlaces() {
		// TODO Auto-generated method stub
		return null;
	}

}
