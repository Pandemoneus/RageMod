package net.rageland.ragemod.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.rageland.ragemod.RageMod;
import net.rageland.ragemod.command.BasicCommand;

public class ZoneCommand extends BasicCommand {
	
	private final RageMod plugin;

	public ZoneCommand(RageMod plugin) {
        super("Zone");
        this.plugin = plugin;
        setDescription("Displays the zone you are in and the distance from spawn");
        setUsage("/rm zone");
        setArgumentRange(0, 0);
        setIdentifiers("rm zone", "ragemod zone");
	}

	@Override
	public boolean execute(CommandSender executor, String identifier, String[] args) {
		if (executor instanceof Player) {
			Player player = (Player) executor;
			plugin.message.parse(player, new StringBuilder("Your current zone is ").append(plugin.zones.getName(player.getLocation())).append(" and distance from spawn is ").append((int) plugin.zones.getDistanceFromSpawn(player.getLocation())).toString());
		} else {
			executor.sendMessage("This message can only be executed as player");
		}
	
		return true;
	}

}
