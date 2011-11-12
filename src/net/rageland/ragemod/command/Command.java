package net.rageland.ragemod.command;

import org.bukkit.command.CommandSender;

public interface Command {

	public void cancelInteraction(CommandSender executor);

	public boolean execute(CommandSender executor, String identifier, String[] args);

	public String getDescription();

	public String[] getIdentifiers();

	public int getMaxArguments();

	public int getMinArguments();

	public String getName();

	public String[] getNotes();

	public String getPermission();

	public String getUsage();

	public boolean isIdentifier(CommandSender executor, String input);

	public boolean isInProgress(CommandSender executor);

	public boolean isInteractive();

	public boolean isShownOnHelpMenu();

}
