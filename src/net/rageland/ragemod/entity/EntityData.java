package net.rageland.ragemod.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.Bukkit;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.npc.race.Race;
import net.rageland.ragemod.entity.npc.race.Unknown;
import net.rageland.ragemod.factions.Faction;
import net.rageland.ragemod.places.region.Location3D;

/**
 * Class containing data that should be persistent between sessions.
 * @author Pandemoneus
 */
public abstract class EntityData {
	// General data
	public final RageMod plugin;
	
	// Persistant
	public final int entityId;
	private String name = "";
	private Race race = new Unknown();
	public Faction faction;
	
	private Location3D currentLocation;
	private Location3D spawnLocation;
	
	public boolean spawnOnLoad;
	
	// Non-persistant
	public Entity associatedEntity;
	
	protected EntityData(final RageMod plugin, final int entityId, final String name, final Race race, final Location3D current, final Location3D spawn, final boolean spawnOnLoad) {
		this.plugin = plugin;
		this.entityId = entityId;
		setName(name);
		setRace(race);
		spawnLocation = spawn == null ? new Location3D(Bukkit.getWorlds().get(0).getSpawnLocation()) : spawn;
		currentLocation = current == null ? spawnLocation : current;
		this.spawnOnLoad = spawnOnLoad;
	}
	
	/**
	 * Sets the race this entity belongs to.
	 * Null is allowed and will set the race to 'Unknown'.
	 * @param race the race
	 */
	public void setRace(final Race race) {		
		this.race = race == null ? new Unknown() : race;
	}
	
	/**
	 * Returns the race of the entity.
	 * @return the race of the entity
	 */
	public Race getRace() {
		return race;
	}
	
	/**
	 * Sets the name of the entity.
	 * Must not be null.
	 * @param name the name
	 */
	public void setName(final String name) {
		if (name == null)
			return;
		
		this.name = name;
	}
	
	/**
	 * Gets the name of the entity.
	 * @return the name of the entity
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the original spawn location of the entity.
	 * @param spawn the spawn location
	 */
	public void setSpawn(final Location3D spawn) {
		if (spawn == null)
			return;
		
		spawnLocation = spawn;
	}
	
	/**
	 * Gets the spawn location of the entity.
	 * @return the spawn location of the entity
	 */
	public Location3D getSpawn() {
		return spawnLocation;
	}
	
	/**
	 * Gets the current, saved location of the entity.
	 * @return the current, saved location of the entity
	 */
	public Location3D getLocation() {
		return currentLocation;
	}
	
	/**
	 * {@inheritDoc}
	 */
    @Override
    public int hashCode() {
    	return new HashCodeBuilder(59, 43).append(entityId).append(name).append(race).append(faction).toHashCode();
    }
    
    public org.bukkit.entity.Entity spawnEntity() {
    	if (associatedEntity == null)
    		return null;
    	
    	return associatedEntity.spawn();
    }
}
