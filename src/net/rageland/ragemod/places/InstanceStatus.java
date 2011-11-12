package net.rageland.ragemod.places;

public enum InstanceStatus {
	IN_PROGRESS("in progress"),
	CLOSED("closed"),
	OPEN("open");
	
	private final String status;
	
	private InstanceStatus(final String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
