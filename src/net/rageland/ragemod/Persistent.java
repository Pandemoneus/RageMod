package net.rageland.ragemod;

import java.util.HashMap;

public interface Persistent {
	public HashMap<Object, Object> save();
	public void load(HashMap<Object, Object> map);
}
