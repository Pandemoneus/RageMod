package net.rageland.ragemod.quest;

import net.rageland.ragemod.NPCUtilities;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.Util;
import net.rageland.ragemod.data.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.CreatureType;

public class KillCreatureQuest implements Quest
{

	private QuestData questData;
	private RewardData rewardData;
	private Flags flags;
	private CreatureType creatureToBeKilled;
	private int killNeededCounter;
	private RageMod plugin;

	public KillCreatureQuest(
			QuestData questData, 
			RewardData rewardData, 
			Flags flags,
			CreatureType creatureToBeKilled,
			int killNeededCounter, 
			RageMod plugin)
	{
		this.questData = questData;
		this.rewardData = rewardData;
		this.flags = flags;
		this.creatureToBeKilled = creatureToBeKilled;
		this.killNeededCounter = killNeededCounter;
		this.plugin = plugin;
	}
	
	public CreatureType getCreatureToBeKilled()
	{
		return creatureToBeKilled;
	}

	@Override
	/**
	 * Executed when a player is finished with the quest.
	 */
	public void questEnd(Player player, PlayerData playerData)
	{
		if (NPCUtilities.checkFreeSpace(player.getInventory(), rewardData.getItem(),
				rewardData.getAmountOfItems()))
		{
			plugin.text.parse(player, questData.getEndText());
			plugin.text.parse(player, "Received: ");

			if (rewardData.getAmountOfItems() > 0)
			{
				plugin.text.parse(player,
						Integer.toString(rewardData.getAmountOfItems())
								+ rewardData.getItem().getType().toString());
				NPCUtilities.addItemToInventory(player.getInventory(),
						rewardData.getItem(), rewardData.getAmountOfItems());
			}

			if (rewardData.getCoins() > 0.0D)
			{
				plugin.text.parse(player,
						Double.toString(rewardData.getCoins()) + " Coins");
			}

			plugin.text.parse(player, "for finishing " + questData.getStartText());

			if (flags.isRandom())
			{
				flags.setActive(false);
			}
		}
		else
		{
			player.sendMessage(NPCUtilities.notEnoughSpaceMessage);
		}
	}

	@Override
	public void questStart(Player player, PlayerData playerData)
	{
		if (playerData.activeQuestData.isPlayerOnQuest())
		{
			plugin.text
					.parse(player,
							"You are already on a quest. To abandon the quest write /quest abandon");
		}
		else
		{
			plugin.text.parse(player, "Accepted quest: " + questData.getName());
			plugin.text.parse(player, questData.getStartText());
			playerData.activeQuestData.startNewQuest(this, killNeededCounter);
		}
	}

	@Override
	public boolean isQuestFinished(PlayerData playerData)
	{
		if (playerData.activeQuestData.getObjectiveCounter() >= killNeededCounter)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void presentQuest(Player player, PlayerData playerData)
	{

	}
	
	@Override
	public QuestData getQuestData()
	{
		return questData;
	}

	@Override
	public void questUpdate(Player player, PlayerData playerData)
	{
		plugin.text.parse(player, "You have killed "
				+ playerData.activeQuestData.getObjectiveCounter() + " of "
				+ killNeededCounter + " " + this.creatureToBeKilled.getName() + "s.");
	}

}
