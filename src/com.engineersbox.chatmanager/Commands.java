package com.engineersbox.chatmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.engineersbox.chatmanager.Main;

import com.engineersbox.chatmanager.methodlib.HoverText;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("cm")) {
				
				if (args.length == 0) {
					
					String version = Bukkit.getServer().getPluginManager().getPlugin("ChatManager").getDescription().getVersion();
        			
    				p.sendMessage("");
    		    	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Version Info" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
    		    	p.sendMessage("");
    		    	p.sendMessage(ChatColor.BLACK + "> " + ChatColor.DARK_GREEN + "Version Number " + ChatColor.WHITE + ":: " + ChatColor.RED + version);
    		    	p.sendMessage(ChatColor.BLACK + "> " + ChatColor.DARK_GREEN + "Author " + ChatColor.WHITE + ":: " + ChatColor.RED + "EngineersBox");
    		    	p.sendMessage("");
    		    	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Version Info" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
    		    	p.sendMessage("");
					
				} else if (args.length > 0) {
					
					if ((args[0].equalsIgnoreCase("version")) && (p.hasPermission("cm.version"))) {
						
						String version = Bukkit.getServer().getPluginManager().getPlugin("ChatManager").getDescription().getVersion();
	        			
	    				p.sendMessage("");
	    		    	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Version Info" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
	    		    	p.sendMessage("");
	    		    	p.sendMessage(ChatColor.BLACK + "> " + ChatColor.DARK_GREEN + "Version Number " + ChatColor.WHITE + ":: " + ChatColor.RED + version);
	    		    	p.sendMessage(ChatColor.BLACK + "> " + ChatColor.DARK_GREEN + "Author " + ChatColor.WHITE + ":: " + ChatColor.RED + "EngineersBox");
	    		    	p.sendMessage("");
	    		    	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Version Info" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
	    		    	p.sendMessage("");
						
					} else if ((args[0].equalsIgnoreCase("help")) && (p.hasPermission("cm.help"))) {
						
		            	p.sendMessage("");
		            	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Help" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
				    	p.sendMessage("");
		            	HoverText.HoverMessage(p, "&0> &2/cm", Arrays.asList("&6Description:", "&cDisplays the plugin version and author"));
						HoverText.HoverMessage(p, "&0> &2/cm add <keyword> <response> [<response>..<response>]", Arrays.asList("&6Description:", "&cAdd a keyword and response(s) to config"));
						HoverText.HoverMessage(p, "&0> &2/cm remove <keyowrd>", Arrays.asList("&6Description:", "&cRemove a keyword and responses from config"));
						HoverText.HoverMessage(p, "&0> &2/cm messages <enable/disable>", Arrays.asList("&6Description:", "&cConfigure visibility of messages from server"));
						HoverText.HoverMessage(p, "&0> &2/cm reload", Arrays.asList("&6Description:", "&cReloads the plugin"));
						HoverText.HoverMessage(p, "&0> &2/cm version", Arrays.asList("&6Description:", "&cDisplays the plugin version and author"));
						HoverText.HoverMessage(p, "&0> &2/cm help", Arrays.asList("&6Description:", "&cOpens this menu"));
				    	p.sendMessage("");
				    	p.sendMessage(ChatColor.AQUA + "----={<" + ChatColor.GOLD + "  [" + ChatColor.DARK_GRAY + "ChatManager Help" + ChatColor.GOLD + "]  " + ChatColor.AQUA + "}>=----");
				    	p.sendMessage("");
						
					} else if ((args[0].equalsIgnoreCase("reload")) && (p.hasPermission("cm.reload"))) {
					
		    			p.sendMessage(Main.prefix + ChatColor.DARK_GREEN + "Reloading ChatManager...");
		    			Plugin plugin = p.getServer().getPluginManager().getPlugin("ChatManager");
		    			p.getServer().getPluginManager().disablePlugin(plugin);
		    			p.getServer().getPluginManager().enablePlugin(plugin);
		    			p.sendMessage(Main.prefix + ChatColor.DARK_GREEN + "Reload Complete!");
						
					} else if ((args[0].equalsIgnoreCase("add")) && (p.hasPermission("cm.add"))) {
						
						String key = args[1];
						List<String> val = new ArrayList<String>();
						
						for (int i = 2; i < args.length; i++) {
							val.add(args[i]);
						}
						
						if (Config.addEntryList(key, val)) {
							p.sendMessage(Main.prefix + "Keyword already exists, added to stored responses");
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
					} else if (((args[0].equalsIgnoreCase("remove")) || (args[0].equalsIgnoreCase("r"))) && (p.hasPermission("cm.remove"))) {
						
						String key = args[1];
						Boolean removeKey = Config.removeEntry(key);
						if (removeKey) {
							p.sendMessage(Main.prefix + "Removed Entry");
						} else {
							p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Keyword and response does not exist");
						}
						
					} else if ((!p.hasPermission("cm.help")) || (!p.hasPermission("cm.version")) || (!p.hasPermission("cm.reload")) || (!p.hasPermission("cm.add")) || (!p.hasPermission("cm.msg")) || (!p.hasPermission("cm.remove"))) {
						
						p.sendMessage(Main.prefix + ChatColor.RED + "You Do Not Have Permission!");
						
					} else {
						p.sendMessage(Main.prefix + ChatColor.DARK_PURPLE + "Invalid Command!");
					}
				}
			}
			
		}
		return true;
	}

}
