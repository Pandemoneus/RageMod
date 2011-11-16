package net.rageland.ragemod;

import java.util.HashSet;

public class ModuleHandler {
	public final RageMod plugin;
	private final HashSet<Handler> handlers = new HashSet<Handler>();
	private static ModuleHandler instance;
	
	public ModuleHandler(final RageMod plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Returns the instance of this class.
	 * @return the instance of this class
	 */
	public static ModuleHandler getInstance() {
		if (instance == null)
			instance = new ModuleHandler(RageMod.getInstance());

		return instance;
	}
	
	public void addHandler(final Handler handler) {
		if (handler == null)
			return;
		
		handlers.add(handler);
	}
	
	public boolean loadAll() {
		if (handlers.isEmpty())
			return true;
		
		for (Handler h : handlers)
			h.loadData();
		
		return true;
	}
}
