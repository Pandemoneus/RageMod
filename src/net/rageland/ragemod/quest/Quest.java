package net.rageland.ragemod.quest;

import java.util.HashSet;

import net.rageland.ragemod.entity.Race;
import net.rageland.ragemod.entity.player.PlayerData;

public interface Quest {

	public void finish();
	
	public void update();
	
	public String getName();
	
	public String getDescription();
	
	public HashSet<Race> getAllowedRaces();
	
	public boolean isInProgress(PlayerData player);
	
	public boolean isRepeatable();
}
