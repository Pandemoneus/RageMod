package net.rageland.ragemod.command;

import org.bukkit.command.CommandSender;

public interface InteractiveCommand extends Command {

    public String getCancelIdentifier();

    public void onCommandCancelled(CommandSender executor);

}
