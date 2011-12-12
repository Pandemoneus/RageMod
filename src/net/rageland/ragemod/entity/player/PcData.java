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
import net.rageland.ragemod.text.Language;

public class PcData extends SharedData {
	
	private final HashMap<Race, Integer> reputation = new HashMap<Race, Integer>();
	private final HashMap<Language, Integer> languageSkillLevel = new HashMap<Language, Integer>();

	public PcData(String name, Race race) {
		super(name, race);
	}

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

	public int getLanguageSkill(final Race race) {
		if (race == null)
			return 0;
		
		final Language lang = race.getLanguage();
		
		if (!languageSkillLevel.containsKey(lang)) {
			languageSkillLevel.put(lang, 0);
		}
		
		return languageSkillLevel.get(lang);
	}
}
