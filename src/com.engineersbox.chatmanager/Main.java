package com.engineersbox.chatmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.engineersbox.chatmanager.AbstractFile;
//import com.engineersbox.chatmanager.Commands;

import com.engineersbox.chatmanager.Config;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class Main extends JavaPlugin implements Listener {

	public static FileConfiguration config;
	public static File cfile;
	
	//Globals
	public static String prefix = ChatColor.GOLD + "[" + ChatColor.DARK_GRAY + "Chat Manager" + ChatColor.GOLD + "] " + ChatColor.DARK_AQUA;
	
	public void onEnable() {
		if (!getDataFolder().exists()) {
    		getDataFolder().mkdirs();
    	}
		
		new Config(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getCommand("cm").setExecutor(new Commands());
		getCommand("cm add").setExecutor(new Commands());
	}
	
	@Override
	public void onDisable() {
		AbstractFile.saveConfig();
	}
	
	public static String format(String msg) {
    	
		String coloredMsg = "";
		if (msg != null) {
			for (int i = 0; i < msg.length(); i++) {
			    if(msg.charAt(i) == '&')
			        coloredMsg += '§';
			    else
			        coloredMsg += msg.charAt(i);
			}
		} else {
			coloredMsg = "§f";
		}

		return coloredMsg;
    	
    }
	
	public static String getRandomElement(List<?> list) { 
        Random rand = new Random(); 
        return list.get(rand.nextInt(list.size())).toString(); 
    }
	
	@EventHandler
	public static void onPlayerChat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		if (!Config.checkPlayerConfig(p)) {
			
			List<Map<?, ?>> responses = Config.getResponses();
			String ChatMessage = e.getMessage();
			
			String[] chatSplit = ChatMessage.split(" ");
			List<String> response = new ArrayList<String>();
			
			for (String val : chatSplit) {
				for (Map<?, ?> str : responses) {
					if (str.containsKey(val)) {
						List<?> keyResponses = (List<?>) str.get(val);
						response.add(getRandomElement(keyResponses));
					}
				}
			}
			
			for (String val : response) {
				
				String convURL = "";
				Boolean hasURL = false;
				Boolean canSplit = false;
				
				if (val.contains(" ")) {
					
					chatSplit = val.split(" ");
					
					for (int i = 0; i < chatSplit.length; i++) {
						
						if ((chatSplit[i].contains("https")) || (chatSplit[i].contains("http"))) {
							convURL = chatSplit[i].toString();
							hasURL = true;
							canSplit = true;
							break;
							
						} else {
							hasURL = false;
						}
					}
					
				} else  if ((!val.contains(" ")) && (val.contains("http")) || (val.contains("http"))) {
					convURL = val;
					canSplit = false;
					hasURL = true;
				} else {
					canSplit = false;
					hasURL = false;
				}
				
				TextComponent linkClickable = new TextComponent(ComponentSerializer.parse("{text: \"" + format(Config.getLinkColour().toString()) + format(Config.getULine().toString()) + convURL + "\",clickEvent:{action:open_url,value:\"" + convURL + "\"}}"));
				TextComponent plugin_prefix = new TextComponent(new ComponentBuilder(format(prefix)).create());
				TextComponent pre_split = new TextComponent();
				TextComponent post_split = new TextComponent();
				
				if ((hasURL.equals(true)) && (canSplit.equals(true))) {
					
					String[] messageSplit = val.split(convURL);
					String messageFirst = messageSplit[0].toString();
					
					if (messageSplit.length == 2) {
						String messageSecond = messageSplit[1].toString();
						post_split.addExtra(messageSecond);
					} else {
						
						post_split.addExtra("");
					}
					
					pre_split.addExtra(messageFirst);
					
				} else if ((hasURL.equals(true)) && (canSplit.equals(false))) {
					pre_split.addExtra(linkClickable);
				} else {
					pre_split.addExtra(val);
				}
				
				if ((hasURL.equals(true)) && (canSplit.equals(true))) {
					p.spigot().sendMessage(plugin_prefix, pre_split, linkClickable, post_split);
				} else {
					p.spigot().sendMessage(plugin_prefix, pre_split);
				}
			}
		}
		
	}
	
}
