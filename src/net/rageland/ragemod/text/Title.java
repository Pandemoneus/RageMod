package net.rageland.ragemod.text;

import org.bukkit.ChatColor;

public interface Title {
	/**
	 * Returns the color the title is colored in.
	 * @return the color the title is colored in
	 */
	public ChatColor getTitleColor();
	
	/**
	 * Returns the title.
	 * @return the title
	 */
	public String getTitle();
}
