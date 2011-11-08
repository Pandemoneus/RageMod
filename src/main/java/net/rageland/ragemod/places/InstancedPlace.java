package net.rageland.ragemod.places;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.places.region.Region3D;

public abstract class InstancedPlace extends BasicPlace {
	
	private static final ChatColor COLOR_ERROR_MESSAGE = ChatColor.RED;
	private static final String NO_SPAWN_MESSAGE = "This instance has no spawns.";
	private static final String INVALID_SPAWN_MESSAGE = "Your admin defined an invalid spawn ID.";
	
	private Region3D entrance;
	private Region3D exit;
	private HashMap<Integer, Region3D> spawns = new HashMap<Integer, Region3D>();
	
	private InstanceStatus status;
	
	private String closedMessage;
	private String inProgressMessage;
	
	/**
	 * Constructs a new instanced place with no entrance, exit or spawn regions.
	 * @param plugin the plugin
	 * @param type the place type
	 * @param boundaries the boundaries
	 * @param name the name
	 */
	protected InstancedPlace(final RageMod plugin, final PlaceType type, final Region3D boundaries, final String name) {
		this(plugin, type, boundaries, name, null, null, null);
	}
	
	protected InstancedPlace(final RageMod plugin, final PlaceType type, final Region3D boundaries, final String name, final Region3D entrance, final Region3D exit, final HashMap<Integer, Region3D> spawns) {
		super(plugin, type, boundaries, name);
		setEntrance(entrance);
		setExit(exit);
		setSpawns(spawns);
	}
	
	/**
	 * Sets the region in which players trigger the teleport method.
	 * @param region the region
	 */
	public void setEntrance(final Region3D region) {
		entrance = region;
	}
	
	/**
	 * Gets the region in which players trigger the teleport method.
	 * @return the region in which players trigger the teleport method
	 */
	public Region3D getEntrance() {
		return entrance;
	}
	
	/**
	 * Sets the region players get teleported to when the instance was completed.
	 * The region must not be null or have no dimensions.
	 * @param region the region
	 */
	public void setExit(final Region3D region) {
		if (region == null || region.hasZeroVolume())
			return;
		
		exit = region;
	}
	
	/**
	 * Gets the region players get teleported to when the instance was completed.
	 * @return the region players get teleported to when the instance was completed
	 */
	public Region3D getExit() {
		return exit;
	}
	
	/**
	 * Sets the message that is displayed when the instance is closed.
	 * @param message the message
	 */
	protected void setClosedMessage(final String message) {
		if (message == null)
			return;
		
		closedMessage = message;
	}
	
	/**
	 * Sets the message that is displayed when the instance is in progress.
	 * @param message the message
	 */
	protected void setInProgressMessage(final String message) {
		if (message == null)
			return;
		
		inProgressMessage = message;
	}
	
	/**
	 * Adds a region that players can spawn in when teleported into the instance.
	 * @param id your id
	 * @param spawn the region
	 */
	public void addSpawn(final int id, final Region3D spawn) {
		if (spawn == null || spawn.hasZeroVolume()) {
			return;
		}
		
		spawns.put(id, spawn);
	}
	
	/**
	 * Removes a region that players could have spawned in when teleported into the instance.
	 * @param id your spawn id
	 */
	public void removeSpawn(final int id) {
		spawns.remove(id);
	}
	
	private void setSpawns(final HashMap<Integer, Region3D> spawns) {
		if (spawns == null)
			return;
		
		this.spawns = spawns;
	}
	
	/**
	 * Teleports a player into the instance to the specified spawn id.
	 * Note that the player will receive an error message when there are no spawns or the id was invalid.
	 * @param player the player to teleport
	 * @param id your spawn id
	 */
	public void teleport(final Player player, final int id) {
		if (player == null)
			return;
		
		if (spawns.isEmpty()) {
			player.sendMessage(COLOR_ERROR_MESSAGE + NO_SPAWN_MESSAGE);
			return;
		}
		
		if (!spawns.containsKey(id)) {
			player.sendMessage(COLOR_ERROR_MESSAGE + INVALID_SPAWN_MESSAGE);
			return;
		}
		
		switch (status) {
		case CLOSED:
			player.sendMessage(COLOR_ERROR_MESSAGE + closedMessage);
			return;
		case IN_PROGRESS:
			player.sendMessage(COLOR_ERROR_MESSAGE + inProgressMessage);
			return;
		default:
			break;
		}
		
		player.teleport(spawns.get(id).getRandomLocationForPlayers());
	}
}
