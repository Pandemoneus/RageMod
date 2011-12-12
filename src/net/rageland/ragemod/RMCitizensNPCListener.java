package net.rageland.ragemod;

import org.bukkit.entity.Player;

import net.citizensnpcs.api.event.npc.NPCInventoryOpenEvent;
import net.citizensnpcs.api.event.npc.NPCRightClickEvent;
import net.citizensnpcs.api.event.npc.NPCTargetEvent;
import net.citizensnpcs.api.event.npc.NPCToggleTypeEvent;
import net.citizensnpcs.api.event.npc.NPCRemoveEvent;
import net.citizensnpcs.api.event.npc.NPCRemoveEvent.NPCRemoveReason;
import net.citizensnpcs.api.event.npc.NPCTalkEvent;
import net.citizensnpcs.api.event.npc.NPCCreateEvent;
import net.citizensnpcs.api.event.npc.NPCListener;
import net.citizensnpcs.api.event.npc.NPCCreateEvent.NPCCreateReason;
import net.citizensnpcs.resources.npclib.HumanNPC;
import net.rageland.ragemod.entity.npc.NpcData;
import net.rageland.ragemod.entity.npc.NpcHandler;
import net.rageland.ragemod.entity.npc.NpcSpeechData;
import net.rageland.ragemod.entity.player.PcHandler;

public class RmCitizensNpcListener extends NPCListener {
	
	@Override
	public void onNPCCreate(final NPCCreateEvent event) {
		final HumanNPC npc = event.getNPC();
		
		if (event.getReason() == NPCCreateReason.COMMAND) {			
			// we'll assume here that the handler doesn't have the uid for the NPC yet
			final NpcData data = new NpcData(npc.getName(), npc.getUID(), null, null);
			NpcHandler.getInstance().addNPCData(data);
		}
	}
	
	@Override
	public void onNPCRemove(final NPCRemoveEvent event) {		
		if (event.getReason() == NPCRemoveReason.COMMAND) {			
			NpcHandler.getInstance().removeNPCData(event.getNPC().getUID());
		}
	}
	
	@Override
	public void onNPCTalk(final NPCTalkEvent event) {
		final HumanNPC npc = event.getNPC();
		final int uid = npc.getUID();
		final Player player = event.getPlayer();
		final PcHandler pcHandler = PcHandler.getInstance();
		final NpcHandler npcHandler = NpcHandler.getInstance();
		
		// see whether the NPC is one of our custom NPCs
		if (npcHandler.has(uid)) {
			final NpcSpeechData speech = npcHandler.getNPCData(uid).getSpeech();
			
			// see whether our NPC has text, otherwise just cancel the event
			if (speech != null) {
				event.setText(speech.getInitialGreeting(pcHandler.getPlayerData(player.getName())));
			} else {
				event.setCancelled(true);
			}
		}
	}

	@Override
	public void onNPCInventoryOpen(final NPCInventoryOpenEvent event) {
	}

	@Override
	public void onNPCTarget(final NPCTargetEvent event) {
	}

	@Override
	public void onNPCRightClick(final NPCRightClickEvent event) {
	}

	@Override
	public void onNPCToggleType(final NPCToggleTypeEvent event) {
	}
}
