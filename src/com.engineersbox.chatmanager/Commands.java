package com.engineersbox.chatmanager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("cm")) {
				
				if (args.length == 0) {
					
					p.sendMessage("error");
					
				} else if (args.length > 0) {
					
					if (args[0].equalsIgnoreCase("add")) {
						
						String key = args[1];
						List<String> val = new ArrayList<String>();
						
						for (int i = 2; i < args.length; i++) {
							val.add(args[i]);
						}
						
						if (Config.addEntryList(key, val)) {
							p.sendMessage(Main.prefix + "Keyword already exists, added to stored responses.");
						} else {
							p.sendMessage(Main.prefix + "Keyword does not exist, added new response");
						}
						return true;
					}
				}
			}
			
		}
		return true;
	}

}
