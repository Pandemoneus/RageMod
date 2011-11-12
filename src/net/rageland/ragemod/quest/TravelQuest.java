package net.rageland.ragemod.quest;

import net.rageland.ragemod.entity.player.PlayerData;

import org.bukkit.entity.Player;

public class TravelQuest extends QuestOld
{
	
	public TravelQuest(QuestData questData, RewardData rewardData, Flags flags)
	{
		super(questData, rewardData, flags);
	}

	/**
	 * A travel quest does not have any requirements to be fulfilled.
	 * This quest type should not be attached to a startendquest.
	 */
	@Override
	public boolean isFinished(PlayerData playerData)
	{
		return true;
	}

	@Override
	public void statusUpdate(Player player, PlayerData playerData)
	{
		// No status update for a travel quest.
	}
}
