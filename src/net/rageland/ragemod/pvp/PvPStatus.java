package net.rageland.ragemod.pvp;

public enum PvpStatus {
	PARTY("Party only"),
	FACTION("Faction only"),
	PEACE("None"),
	FREE_FOR_ALL("Free for all"),
	NATION("Nation only");
	
	private final String status;
	
	private PvpStatus(final String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
