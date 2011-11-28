package net.rageland.ragemod.entity;

import java.util.HashSet;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.ChatColor;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.factions.Faction;
import net.rageland.ragemod.places.OwnedPlace;

public abstract class SharedData implements Owner {
	public final HashSet<OwnedPlace> ownedPlaces = new HashSet<OwnedPlace>();
	public boolean isMale = true;
	private Race race;
	private String name;
	public Faction faction;
	
	protected SharedData(final String name, final Race race) {
		this.name = name == null ? "" : ChatColor.stripColor(name);
		setRace(race);
	}
	
	/**
	 * Sets the race this entity belongs to.
	 * Null is allowed and will set the race to 'Unknown'.
	 * @param race the race
	 */
	public void setRace(final Race race) {		
		this.race = race == null ? Race.UNKNOWN : race;
	}
	
	/**
	 * Returns the race of the entity.
	 * @return the race of the entity
	 */
	public Race getRace() {
		return race;
	}
	
	public String getName() {
		return name;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public int hashCode() {
    	return new HashCodeBuilder(59, 43).append(isMale).append(race).append(faction).toHashCode();
    }
	
	public HashSet<OwnedPlace> getOwnedPlaces() {
		return ownedPlaces;
	}
	
	public void addPlace(final OwnedPlace place) {
		if (place == null)
			return;
		
		ownedPlaces.add(place);
	}
	
	public void removePlace(final OwnedPlace place) {
		ownedPlaces.remove(place);
	}
}
