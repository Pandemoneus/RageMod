package net.rageland.ragemod.entity.npc;

import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.getspout.spoutapi.SpoutManager;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.OwnerData;
import net.rageland.ragemod.entity.npc.race.Race;
import net.rageland.ragemod.places.region.Location3D;
import net.rageland.ragemod.utilities.GeneralUtilities;

public class NPCData extends OwnerData {
	// Persistant
	public boolean isMale = true;
	private String skinPath = "";
	private final HashMap<String, Float> affinities = new HashMap<String, Float>();
	private String speechDataIdentifier;
	
	// Non-persistant
	public static final float MIN_AFFINITY = -10.0F;
	public static final float MAX_AFFINITY = 10.0F;
	
	/**
	 * Constructs new NPCData.
	 * This should be done from a configuration file or a database.
	 * @param plugin the plugin
	 * @param entityId the entity id
	 * @param name the name of the NPC
	 * @param race the race of the NPC
	 * @param skinPath the path to the skin of the NPC
	 * @param current the current location of the NPC
	 * @param spawn the spawn location of the NPC
	 * @param speechDataIdentifier the identifier used to get SpeechData for the NPC
	 */
	public NPCData(final RageMod plugin, final int entityId, final String name, final Race race, final String skinPath, final Location3D current, final Location3D spawn, final String speechDataIdentifier) {
		super(plugin, entityId, name, race, current, spawn);
		setSkin(skinPath);
		this.speechDataIdentifier = speechDataIdentifier;
	}
	
	/**
	 * Sets the skin of a NPC.
	 * @param path the path to the skin
	 */
	public void setSkin(final String path) {
		if (path == null)
			return;
		
		skinPath = path;
		//TODO: Check whether Spout is really activated to prevent NPE
		SpoutManager.getAppearanceManager().setGlobalSkin((HumanEntity) associatedEntity, skinPath);
	}
	
	/**
	 * Sets the affinity of a NPC towards a player.
	 * The player does not have to be online for this to work.
	 * @param player the player
	 * @param affinity the affinity, must be between MIN_AFFINITY and MAX_AFFINITY
	 */
	public void setAffinityToPlayer(final OfflinePlayer player, final float affinity) {
		if (player == null || !GeneralUtilities.isBetween(affinity, MIN_AFFINITY, MAX_AFFINITY))
			return;
		
		affinities.put(player.getName(), affinity);
	}
	
	public NPCSpeechData getSpeech() {
		return NPCSpeechData.fromIdentifier(speechDataIdentifier);
	}
}
