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
		/* TODO: Replace <rID> with resource ID
		SpigotUpdater updater = new SpigotUpdater(this, <rID>);
    	Updaters.checkVersion(updater);
		*/
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		getCommand("cm").setExecutor(new Commands());
		getCommand("cm add").setExecutor(new Commands());
		getCommand("cm remove").setExecutor(new Commands());
		getCommand("cm messages").setExecutor(new Commands());
		getCommand("cm help").setExecutor(new Commands());
		getCommand("cm version").setExecutor(new Commands());
		getCommand("cm reload").setExecutor(new Commands());
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
						response.add(getRandomElement(keyResponses).toString());
					}
				}
			}
			
			for (String resp : response) {
				
				String[] respSplit = resp.split(" ");
				TextComponent msgToPlayer = new TextComponent(new ComponentBuilder(prefix + ChatColor.WHITE).create());
				
				for (String val : respSplit) {
					
					if ((val.contains("https://")) || (val.contains("http://")) || (val.contains("www."))) {
						TextComponent linkClickable = new TextComponent(ComponentSerializer.parse("{text: \"" + format(Config.getLinkColour().toString()) + format(Config.getULine().toString()) + val + "\",clickEvent:{action:open_url,value:\"" + val + "\"}} "));
						msgToPlayer.addExtra(linkClickable);
						msgToPlayer.addExtra(ChatColor.RESET + " " + ChatColor.WHITE);
					} else {
						TextComponent msgContent = new TextComponent(new ComponentBuilder(format(val)).create());
						msgToPlayer.addExtra(msgContent);
						msgToPlayer.addExtra(" ");
					}
					
				}
				
				p.spigot().sendMessage(msgToPlayer);
				
			}
		}
		
	}
	
}
