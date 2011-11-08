package net.rageland.ragemod.entity.npc;

import net.minecraft.server.ItemInWorldManager;
import net.minecraft.server.NetHandler;
import net.minecraft.server.NetworkManager;
import net.rageland.ragemod.RageMod;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.martin.bukkit.npclib.NPCEntity;
import org.martin.bukkit.npclib.NPCNetHandler;
import org.martin.bukkit.npclib.NPCNetworkManager;
import org.martin.bukkit.npclib.NullSocket;

public class BasicNPC extends NPCEntity implements NPC {
	private int lastTargetId;
	private long lastBounceTick;
	private int lastBounceId;
	protected RageMod plugin;
	protected SpeechData speechData;
	protected NPCInstance instance;
	protected Location location;
	
	public BasicNPC(NPCInstance instance) {
		super(instance.server.getMCServer(), instance.world.getWorldServer(), instance.getColorName(), new ItemInWorldManager(instance.world.getWorldServer()));
	
		NetworkManager netMgr = new NPCNetworkManager(new NullSocket(), "NPC Manager", new NetHandler() {
			public boolean c() {
				return true;
			}
		});
		
		this.netServerHandler = new NPCNetHandler(instance.server.getMCServer(), netMgr, this);
		this.lastTargetId = -1;
		this.lastBounceId = -1;
		this.lastBounceTick = 0L;
		this.plugin = instance.plugin;
		this.instance = instance;

		int radius = 20;
		int interval = 30;

		this.speechData = plugin.database.npcQueries.getPhrases(instance.getNPCData(), instance.getTownID());
	}

	@Override
	public void rightClickAction(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftClickAction(Player player) {
		// TODO Auto-generated method stub
		
	}
}
