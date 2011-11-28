package net.rageland.ragemod.quest;

import net.rageland.ragemod.entity.player.PlayerData;

import org.bukkit.entity.Player;

public class TravelQuest extends QuestOld
{
	
	public TravelQuest(QuestData questData, RewardData rewardData, Flags flags)
	{
		super(questData, rewardData, flags);
	}

	/*
	@Override
	public boolean isFinished(PlayerData playerData)
	{
		return true;
	}

	@Override
	public void statusUpdate(Player player, PlayerData playerData)
	{
		// No status update for a travel quest.
	}*/
}
