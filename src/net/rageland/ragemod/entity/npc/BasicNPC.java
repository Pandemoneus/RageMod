package net.rageland.ragemod.entity.npc;

import net.minecraft.server.ItemInWorldManager;
import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.entity.EntityHandler;
import net.rageland.ragemod.text.Title;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
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
	
	public BasicNPC(final RageMod plugin, final NPCData data, final ChatColor nameColor, final NPCType type, final NPCSpeechData speech) {
		super(BServer.getInstance(plugin).getMCServer(), new BWorld(data.getLocation().getWorld()).getMCWorld(), nameColor + data.getName(), new ItemInWorldManager(new BWorld(data.getLocation().getWorld()).getWorldServer()));
		
		this.plugin = plugin;
		this.data = data;
		this.data.associatedEntity = this;
		this.nameColor = nameColor == null ? ChatColor.WHITE : nameColor;
		this.type = type;
		this.speech = speech;
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
	
	/**
	 * Sets the skin of a NPC.
	 * @param path the path to the skin
	 */
	public void setSkin(final String path) {
		if (path == null)
			return;
		
		data.setSkinPath(path);
		//TODO: Check whether Spout is really activated to prevent NPE
		SpoutManager.getAppearanceManager().setGlobalSkin((HumanEntity) this, data.getSkinPath());
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
			EntityHandler.getInstance().manager.rename(Integer.toString(data.entityId), nameColor + data.getName());
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
