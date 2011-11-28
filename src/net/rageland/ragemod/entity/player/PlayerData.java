package net.rageland.ragemod.entity.player;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Location;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.Owner;
import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.SharedData;
import net.rageland.ragemod.places.OwnedPlace;
import net.rageland.ragemod.places.region.Location3D;

public class PlayerData extends SharedData {

	protected PlayerData(String name, Race race) {
		super(name, race);
		// TODO Auto-generated constructor stub
	}


	private final HashMap<Race, Integer> reputation = new HashMap<Race, Integer>();
	
	private boolean isMember = false;
	private Date memberExpiration;
	private float bounty;
	private float extraBounty;
	
	private final HashSet<OwnedPlace> ownedPlaces = new HashSet<OwnedPlace>();

	public Timestamp enterLeaveMessageTime = null; // Prevent message spam by only allowing a message every 10 seconds (while people work on walls, etc)


	// Checks whether the current location is inside one of the player's lots
	public boolean isInsideOwnLot(Location location) {
		return false;
	}


	public Map<String, Object> save() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getLanguageSkill(Race race) {
		// TODO Auto-generated method stub
		return 0;
	}
}
