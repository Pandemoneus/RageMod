package net.rageland.ragemod.npcentities;

import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.World;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.Util;
import net.rageland.ragemod.data.PlayerData;
import net.rageland.ragemod.quest.Quest;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class QuestStartNPCEntity extends NPCEntity {
	private Quest quest;
	
	public QuestStartNPCEntity(
			MinecraftServer minecraftserver, 
			World world, String name,
			ItemInWorldManager iteminworldmanager, 
			RageMod plugin,
			Quest quest) 
	{
		super(minecraftserver, world, name, iteminworldmanager, plugin);
		this.quest = quest;		
	}
	
	/**
	 * Method called when a right click action on the NPC is performed by a player.	 * 
	 * @param player Player that right clicked the entity
	 */
	public void rightClickAction(Player player) 
	{
		plugin.text.message(player, "Quest: " + quest.getQuestName());
		plugin.text.message(player, quest.getQuestText());
		plugin.text.message(player, "[Left click npc to accept]");
	}

	/**
	 * Method called when a left click action on the NPC is performed by a player.	 * 
	 * @param player Player that left clicked the entity
	 */
	public void leftClickAction(Player player) 
	{
		quest.questStart(player, plugin.players.get(player.getName()));
	}

}
