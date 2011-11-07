package net.rageland.ragemod.pvp;

public enum PvPStatus {
	PARTY("Party only"),
	FACTION("Faction only"),
	PEACE("None"),
	FREE_FOR_ALL("Free for all"),
	NATION("Nation only");
	
	private final String statusMessage;
	
	private PvPStatus(final String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
}
