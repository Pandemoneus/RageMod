package net.rageland.ragemod.entity.player;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Location;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.EntityData;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.entity.OwnerData;
import net.rageland.ragemod.entity.npc.race.Race;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.region.Location3D;

public class PlayerData extends OwnerData {
	private final HashMap<Race, Integer> reputation = new HashMap<Race, Integer>();
	
	private boolean isMember = false;
	private Date memberExpiration;
	private float bounty;
	private float extraBounty;
	
	private final HashSet<OwnedPlace> ownedPlaces = new HashSet<OwnedPlace>();

	public Timestamp enterLeaveMessageTime = null; // Prevent message spam by only allowing a message every 10 seconds (while people work on walls, etc)
	
	protected PlayerData(RageMod plugin, int entityId, String name, Race race, Location3D current, Location3D spawn) {
		super(plugin, entityId, name, race, current, spawn);
		// TODO Auto-generated constructor stub
	}

	// Checks whether the current location is inside one of the player's lots
	public boolean isInsideOwnLot(Location location) {
		return false;
	}
}
