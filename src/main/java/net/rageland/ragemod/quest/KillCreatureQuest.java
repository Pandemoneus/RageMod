package net.rageland.ragemod.quest;

import net.rageland.ragemod.InventoryUtilities;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.data.PlayerData;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

public class KillCreatureQuest extends Quest
{

	private QuestData questData;
	private RewardData rewardData;
	private Flags flags;
	private CreatureType creatureToBeKilled;
	private RageMod plugin;

	public KillCreatureQuest(
			QuestData questData, 
			RewardData rewardData, 
			Flags flags,
			CreatureType creatureToBeKilled)
	{
		super(questData, rewardData, flags);
		this.questData = questData;
		this.rewardData = rewardData;
		this.flags = flags;
		this.creatureToBeKilled = creatureToBeKilled;
		this.plugin = RageMod.getInstance();
	}
	
	public CreatureType getCreatureToBeKilled()
	{
		return creatureToBeKilled;
	}

	@Override
	/**
	 * Executed when a player is finished with the quest.
	 */
	public void end(Player player, PlayerData playerData)
	{
		if (InventoryUtilities.checkFreeSpace(player.getInventory(), rewardData.getItem(),
				rewardData.getAmountOfItems()))
		{
			plugin.message.parse(player, questData.getEndText());
			plugin.message.parse(player, "Received: ");

			if (rewardData.getAmountOfItems() > 0)
			{
				plugin.message.parse(player,
						Integer.toString(rewardData.getAmountOfItems())
								+ rewardData.getItem().getType().toString());
				InventoryUtilities.addItemToInventory(player.getInventory(),
						rewardData.getItem(), rewardData.getAmountOfItems());
			}

			if (rewardData.getCoins() > 0.0D)
			{
				plugin.message.parse(player,
						Double.toString(rewardData.getCoins()) + " Coins");
				plugin.iConomy.getAccount(player.getName()).getHoldings().add(rewardData.getCoins());
			}

			plugin.message.parse(player, "for finishing " + questData.getName());
		}
		else
		{
			player.sendMessage(InventoryUtilities.notEnoughSpaceMessage);
		}
	}

	@Override
	public void start(Player player, PlayerData playerData)
	{
		if (playerData.activeQuestData.isPlayerOnQuest())
		{
			plugin.message
					.parse(player,
							"You are already on a quest. To abandon the quest write /quest abandon");
		} 
		else if(!questData.isRequirementsMet(playerData)) {
			plugin.message.parse(player, "You don't meet the requirements for this quest.");
		}
		else
		{
			plugin.message.parse(player, "Accepted quest: " + questData.getName());
			plugin.message.parse(player, questData.getStartText());
			playerData.activeQuestData.startNewQuest(this, questData.getObjectiveCounter());
		}
	}

	@Override
	public boolean isFinished(PlayerData playerData)
	{
		if (playerData.activeQuestData.getObjectiveCounter() >= questData.getObjectiveCounter())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void statusUpdate(Player player, PlayerData playerData)
	{
		plugin.message.parse(player, "You have killed "
				+ playerData.activeQuestData.getObjectiveCounter() + " of "
				+ questData.getObjectiveCounter() + " " + this.creatureToBeKilled.getName() + "s.");
	}

}
