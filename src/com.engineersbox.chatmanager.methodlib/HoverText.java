package com.engineersbox.chatmanager.methodlib;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class HoverText {
	
	static String format(String msg) {
	    	
			String coloredMsg = "";
			if (msg != null) {
				for(int i = 0; i < msg.length(); i++)
				{
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
	
	public static void HoverMessage(Player p, String displayText, List<String> hoverContent) {
		
		ArrayList<Object> components = new ArrayList<>();
		TextComponent comp = new TextComponent(format(displayText));
		TextComponent hoverMessage = new TextComponent(format(hoverContent.get(0)));
		TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
		
		if (hoverContent.size() > 1) {
			for (String content : hoverContent.subList(1, hoverContent.size())) {
				hoverMessage.addExtra(newLine);
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(format(content)).create()));
			}
		}
		
		components.add(hoverMessage);
		BaseComponent[] hoverToSend = (BaseComponent[])components.toArray(new BaseComponent[components.size()]);
		comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverToSend));
		p.spigot().sendMessage(comp);
		
	}
	
	public static void HoverMessageAllPlayers(String displayText, List<String> hoverContent) {
		
		ArrayList<Object> components = new ArrayList<>();
		TextComponent comp = new TextComponent(format(displayText));
		TextComponent hoverMessage = new TextComponent(format(hoverContent.get(0)));
		TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
		
		if (hoverContent.size() > 1) {
			for (String content : hoverContent.subList(1, hoverContent.size())) {
				hoverMessage.addExtra(newLine);
				hoverMessage.addExtra(new TextComponent(new ComponentBuilder(format(content)).create()));
			}
		}
		
		components.add(hoverMessage);
		BaseComponent[] hoverToSend = (BaseComponent[])components.toArray(new BaseComponent[components.size()]);
		comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverToSend));
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(comp);
		}
		
	}
	
}
