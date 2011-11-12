package net.rageland.ragemod.entity;

public interface Entity {
	/**
	 * Spawns the entity.
	 * Only spawns when it wasn't spawned yet.
	 * @return the entity that was spawned or null when none was spawned
	 */
	public org.bukkit.entity.Entity spawn();
	
	/**
	 * Despawns the entity.
	 * @return true if it was despawned, otherwise false
	 */
	public boolean despawn();
}
