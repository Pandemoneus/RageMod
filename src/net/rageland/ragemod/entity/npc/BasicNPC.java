package net.rageland.ragemod.entity.npc;

import net.minecraft.server.ItemInWorldManager;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.EntityHandler;
import net.rageland.ragemod.text.Title;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.martin.bukkit.npclib.BServer;
import org.martin.bukkit.npclib.BWorld;
import org.martin.bukkit.npclib.NPCEntity;

public class BasicNPC extends NPCEntity implements NPC, Title {
	public final RageMod plugin;
	public final NPCData data;
	public final NPCType type;
	public final NPCSpeechData speech;
	
	private ChatColor nameColor;
	
	private boolean spawned = false;
	
	protected BasicNPC(final RageMod plugin, final NPCData data, final ChatColor nameColor, final NPCType type, final NPCSpeechData speech) {
		super(BServer.getInstance(plugin).getMCServer(), new BWorld(data.getLocation().getWorld()).getMCWorld(), nameColor + data.getName(), new ItemInWorldManager(new BWorld(data.getLocation().getWorld()).getWorldServer()));
		
		this.plugin = plugin;
		this.data = data;
		this.data.associatedEntity = this;
		this.nameColor = nameColor;
		this.type = type;
		this.speech = speech;
	}
	
	/**
	 * Constructs a BasicNPC from NPCData.
	 * Note that there is no other way to construct a BasicNPC.
	 * @param data the data
	 * @param nameColor the color in which the npc's name should appear
	 * @return a new BasicNPC
	 */
	public static BasicNPC fromNPCData(final NPCData data, final ChatColor nameColor) {
		if (data == null)
			return null;
		
		final ChatColor tmp = nameColor == null ? ChatColor.WHITE : nameColor;
		
		return new BasicNPC(data.plugin, data, tmp, NPCType.BASIC, data.getSpeech());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rightClickAction(final Player player) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void leftClickAction(final Player player) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isSpawned() {
		return spawned;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.bukkit.entity.Entity spawn() {
		if (spawned)
			return null;
		
		spawned = true;
		return EntityHandler.getInstance().spawn(data.entityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean despawn() {
		if (!spawned)
			return false;
		
		spawned = false;
		EntityHandler.getInstance().despawn(data.entityId);
		return true;
	}

	/**
	 * Sets the color of the name of the NPC.
	 * @param color the color
	 */
	public void setNameColor(ChatColor color) {
		if (color == null)
			return;
		
		nameColor = color;
		
		if (spawned)
			NPCHandler.getInstance().manager.rename(Integer.toString(data.entityId), nameColor + data.getName());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChatColor getTitleColor() {
		return nameColor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return data.getName();
	}
}
