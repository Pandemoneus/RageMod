package net.rageland.ragemod;

// TODO: Bounty

// TODO: Fix onEntityDamageByEntity, I can't get it to fire on mob damage whatsoever

import net.rageland.ragemod.data.Factions;
import net.rageland.ragemod.data.PlayerData;
import net.rageland.ragemod.data.PlayerTown;
import net.rageland.ragemod.data.PlayerTowns;
import net.rageland.ragemod.data.Players;
import net.rageland.ragemod.npcentities.NPCEntity;
import net.rageland.ragemod.npclib.NPCManager;
import net.rageland.ragemod.npclib.NpcEntityTargetEvent;

import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * RageMod entity listener
 * @author TheIcarusKid
 */
public class RMEntityListener extends EntityListener
{
	private final RageMod plugin;

    public RMEntityListener(final RageMod plugin) 
    {
        this.plugin = plugin;
    }
    
    // Called when an entity damages another entity
    @Override
    public void onEntityDamage(EntityDamageEvent rawEvent) 
    {
    	Entity defenderEntity = rawEvent.getEntity();
    	
    	// Makes NPC invulnerable
    	/*
        if (defenderEntity instanceof HumanEntity) 
        {			
			if (NPCManager.isNPC(defenderEntity)) 
			{				
				rawEvent.setCancelled(true);
			}
		}
		*/
    	
    	if(rawEvent instanceof EntityDamageByEntityEvent)
    	{
    		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)rawEvent;
    		System.out.println("DAMAGE!");
        	
        	Entity attackerEntity = event.getDamager();  
        	
        	/*
        	// If defender is NPC, the event is already cancelled, must check if it is a leftClickAction from player.
        	if (NPCManager.isNPC(defenderEntity)) 
			{				
        		if (attackerEntity instanceof Player) 
    			{						
    				NPCEntity npcEntity = NPCManager.getNPCFromEntity(defenderEntity);
    				npcEntity.leftClickAction((Player) attackerEntity);
    			}
				return;
			}
			*/
        	
            
            // Handle PvP
            if( attackerEntity instanceof Player && defenderEntity instanceof Player ) 
            {
            	Player attacker = (Player)attackerEntity;
            	Player defender = (Player)defenderEntity;
            	PlayerData attackerData = plugin.players.get(attacker.getName());
            	PlayerData defenderData = plugin.players.get(defender.getName());
            	
            	// Always prevent allies from harming each other
    			if( attackerData.id_Faction == defenderData.id_Faction )
    			{
    				event.setCancelled(true);
        			Util.message(attacker, defenderData.name + " is your ally!");
        			return;
    			}
            	
            	// Use the defender's position to determine behavior
            	// *** ZONE A (Neutral Zone) ***
            	if( plugin.zones.isInZoneA(defender.getLocation()) )
            	{
            		// No PvP in capitol
            		if( plugin.zones.isInCapitol(defender.getLocation()) )
            		{
            			event.setCancelled(true);
            			Util.message(attacker, "PvP is not allowed inside " + plugin.config.Capitol_Name + ".");
            			return;
            		}
            		else
            		{
            			// Only faction-faction PvP is allowed in neutral zone
            			if( defenderData.id_Faction == 0 )
            			{
            				event.setCancelled(true);
                			Util.message(attacker, "You cannot attack neutral players in " + plugin.config.Zone_NAME_A + ".");
                			return;
            			}
            			else if( attackerData.id_Faction == 0 )
            			{
            				event.setCancelled(true);
                			Util.message(attacker, "Neutral players cannot attack in " + plugin.config.Zone_NAME_A + ".");
                			return;
            			}
            		}
            	}
            	// *** ZONE B (War Zone) ***
            	else if( plugin.zones.isInZoneB(defender.getLocation()) )
            	{
            		PlayerTown playerTown = plugin.playerTowns.getCurrentTown(defender.getLocation());
            		
            		// Keep referees from participating in combat
            		if( RageMod.permissionHandler.has(attacker, "ragemod.referee.blockpvp") )
            		{
            			event.setCancelled(true);
            			Util.message(attacker, "Referees may not participate in combat.");
            			return;
            		}
            		// Protect referees 
            		else if( RageMod.permissionHandler.has(defender, "ragemod.referee.blockpvp") )
            		{
            			event.setCancelled(true);
            			Util.message(attacker, "You cannot harm a referee.");
            			return;
            		}
            		// Handle combat inside of towns
            		else if( playerTown != null )
            		{
    	        		// Protect neutral players inside all towns
    	        		if( defenderData.id_Faction == 0 )
    	        		{
    	        			event.setCancelled(true);
    	        			Util.message(attacker, "You cannot harm neutral players inside of towns.");
    	        			return;
    	        		}
    	        		// Keep neutral players from harming any players
    	        		if( attackerData.id_Faction == 0 )
    	        		{
    	        			event.setCancelled(true);
    	        			Util.message(attacker, "Neutral players cannot attack inside of towns.");
    	        			return;
    	        		}
    	        		// Protect faction players inside of their own and allied towns
    	        		if( defenderData.id_Faction == playerTown.id_Faction )
    	        		{
    	        			event.setCancelled(true);
    	        			Util.message(attacker, "You cannot harm " + plugin.factions.getName(defenderData.id_Faction) + " inside of their own towns.");
    	        			return;
    	        		}
            		}
            	}
            }
            else if( defenderEntity instanceof Player && attackerEntity instanceof Creature)
            {
            	// Automatically kill monsters who attack admins
            	Creature creature = (Creature)attackerEntity;
            	creature.damage(100);
            	System.out.println("Attempted to kill attacking creature");
            } 
            else
            {
            	System.out.println(attackerEntity.getEntityId() + " entity caused damage.");
            }
    	}
    }
    
    // Called when creatures spawn
    public void onCreatureSpawn(CreatureSpawnEvent event) 
    {
        // Don't process code if event was cancelled by another plugin
    	if (event.isCancelled()) 
        {
            return;
        }
    	
    	// Don't let monsters spawn inside player towns or the capitol
    	if( (event.getCreatureType() == CreatureType.CREEPER || event.getCreatureType() == CreatureType.SKELETON ||
    			event.getCreatureType() == CreatureType.ZOMBIE || event.getCreatureType() == CreatureType.SPIDER) && 
    			(plugin.zones.isInCapitol(event.getLocation()) || plugin.playerTowns.getCurrentTown(event.getLocation()) != null) )
    	{
    		event.setCancelled(true);
    	}    	
    }
    
    /**
     *  Called on entityTarget. Used for detectic right clicks on the NPC
     */
    public void onEntityTarget(EntityTargetEvent event) {
		
    	/*
		if ((event instanceof NpcEntityTargetEvent)) {
			
			NpcEntityTargetEvent netEvent = (NpcEntityTargetEvent) event;
			if (((netEvent.getTarget() instanceof Player))
					&& (netEvent.getNpcReason() == NpcEntityTargetEvent.NpcTargetReason.NPC_RIGHTCLICKED)) {
				
				NPCEntity npcEntity = NPCManager.getNPCFromEntity(netEvent
						.getEntity());

				npcEntity.rightClickAction((Player) event.getTarget());
			}
		}
		*/
	}
}

