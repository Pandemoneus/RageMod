package net.rageland.ragemod.entity.npc;

import net.minecraft.server.ItemInWorldManager;
import net.rageland.ragemod.data.NPCInstance;

import org.martin.bukkit.npclib.NPCEntity;

public class BasicNPC extends NPCEntity implements NPC {
	public BasicNPC(NPCInstance instance) {
		super(instance.server.getMCServer(), instance.world.getWorldServer(), instance.getColorName(), new ItemInWorldManager(instance.world.getWorldServer()));
	}
}
