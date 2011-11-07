package net.rageland.ragemod.entity;

import net.rageland.ragemod.places.region.Location3D;

public interface Spawnable {
	public SpawnResult spawn();
	public Location3D getSpawnLocation();
}
