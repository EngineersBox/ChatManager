package com.engineersbox.chatmanager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.engineersbox.chatmanager.Main;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("cm")) {
				
				if (args.length == 0) {
					
					p.sendMessage("Help Section Here");
					
				} else if (args.length > 0) {
					
					if ((args[0].equalsIgnoreCase("add")) && (p.hasPermission("cm.add"))) {
						
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
					} else if (((args[0].equalsIgnoreCase("messages")) || (args[0].equalsIgnoreCase("msg"))) && (p.hasPermission("cm.msg"))) {
						Boolean configState = Config.checkPlayerConfig(p);
						if (args[1].equalsIgnoreCase("enable")) {
							if (configState.equals(false)) {
								p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Already Enabled!");
							} else {
								Config.removePlayerConfig(p);
								p.sendMessage(Main.prefix + "Enabled Messages");
							}
						} else if (args[1].equalsIgnoreCase("disable")) {
							if (configState.equals(true)) {
								p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Already Disabled!");
							} else {
								Config.addPlayerConfig(p);
								p.sendMessage(Main.prefix + "Disabling Messages");
							}
						} else {
							p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Invalid Syntax!");
							p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Usage: " + ChatColor.ITALIC + "/cm messages <enbale/disable>");
						}
					}
				}
			}
			
		}
		return true;
	}

}
