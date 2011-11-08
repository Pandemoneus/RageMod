package net.rageland.ragemod.places;

import java.util.HashSet;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.ChatColor;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.NPC;
import net.rageland.ragemod.places.region.Region3D;
import net.rageland.ragemod.pvp.PvPStatus;
import net.rageland.ragemod.text.Title;

/**
 * This class provides basic methods for all places.
 * @author Pandemoneus
 */
public abstract class BasicPlace implements Place, Title {
	
	@SuppressWarnings("unused")
	// Place info
	private RageMod plugin;
	private PlaceType type;
	private Region3D boundaries;
	private String name;
	// TODO: Config based standard title color
	private ChatColor nameColor = ChatColor.YELLOW;
	private PvPStatus pvp = PvPStatus.PEACE;
	
	// NPCs
	private final HashSet<NPC> npcs = new HashSet<NPC>();
	
	// Messages
	private String entryMessage = "";
	private String exitMessage = "";
	
	/**
	 * Constructs a basic place.
	 * @param plugin the plugin
	 * @param type the place type
	 * @param boundaries the boundaries
	 * @param name the name
	 */
	protected BasicPlace(final RageMod plugin, final PlaceType type, final Region3D boundaries, final String name) {
		this.plugin = plugin;
		this.type = type;
		this.boundaries = boundaries;
		this.name = name;
	}

	public PlaceType getType() {
		return type;
	}

	public Region3D getBoundaries() {
		return boundaries;
	}
	
	public void setBoundaries(final Region3D boundaries) {
		if (boundaries == null || boundaries.hasZeroVolume()) {
			return;
		}
		
		this.boundaries = boundaries;
	}

	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		if (name == null || name.isEmpty()) {
			return;
		}
		
		this.name = name;
	}
	
	public String entryMessage() {
		return entryMessage;
	}
	
	/**
	 * Sets the entry message that is displayed when a player enters the place.
	 * 
	 * The message must not be null. An empty string is allowed though.
	 * @param message the entry message
	 */
	protected void setEntryMessage(final String message) {
		if (message == null) {
			return;
		}
		
		entryMessage = message;
	}
	
	public String exitMessage() {
		return exitMessage;
	}
	
	/**
	 * Sets the entry message that is displayed when a player enters the place.
	 * 
	 * The message must not be null. An empty string is allowed though.
	 * @param message the entry message
	 */
	protected void setExitMessage(final String message) {
		if (message == null) {
			return;
		}
		
		exitMessage = message;
	}
	
	/**
	 * Gets the color in which the title is colored.
	 * @return the color in which the title is colored
	 */
	public ChatColor getTitleColor() {
		return nameColor;
	}
	
	/**
	 * Defines in which color the title should be colored.
	 * @param color the color
	 */
	public void setTitleColor(final ChatColor color) {
		nameColor = color;
	}
	
	/**
	 * @see BasicPlace#getName()
	 */
	public String getTitle() {
		return getName();
	}
	
	
	/**
	 * Adds a NPC to the place.
	 * @param npc the NPC
	 */
	public void addNPC(final NPC npc) {
		if (npc == null)
			return;
		
		npcs.add(npc);
	}
	
	/**
	 * Removes a NPC from the place.
	 * @param npc the NPC
	 */
	public void removeNPC(final NPC npc) {
		npcs.remove(npc);
	}
	
	/**
	 * Returns the pvp status of the place.
	 * @return the pvp status of the place
	 */
	public PvPStatus getPvpStatus() {
		return pvp;
	}
	
	/**
	 * Sets the pvp status of the place.
	 * @param status the status
	 */
	public void setPvpStatus(final PvPStatus status) {
		pvp = status;
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {
    	return new HashCodeBuilder(13, 23).append(name).append(boundaries).append(type).toHashCode();
    }
}
