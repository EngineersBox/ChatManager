package com.engineersbox.chatmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.engineersbox.chatmanager.AbstractFile;
import com.engineersbox.chatmanager.Main;

public class Config extends AbstractFile {

    public Config(Main main) {
       
        super(main, "config.yml");
       
    }
    
    public static boolean addEntry(String key, String response) {
    	String path = "Chat-Responses";
    	Boolean existsFlag = false;
    	if (config.get(path) != null) {
    		
			List<Map<?, ?>> currVal = config.getMapList(path);
			
			for (Map<?, ?> val : currVal) {
				if (val.containsKey(key)) {
					@SuppressWarnings("unchecked")
					List<String> currResponses = (List<String>) val.get(key);
					currResponses.add(response);
					
					Map<String, List<String>> newVal = new HashMap<String, List<String>>();
					newVal.put(key, currResponses);
					
					currVal.remove(val);
					currVal.add(newVal);
					config.set(path, currVal);
					saveConfig();
					existsFlag = true;
					break;
				}
			}
			
			if(existsFlag.equals(false)) {
				Map<String, List<String>> newVal = new HashMap<String, List<String>>();
	    		List<String> newResponse = new ArrayList<String>();
	    		
	    		newResponse.add(response);
	    		newVal.put(key, newResponse);
	    		currVal.add(newVal);
	    		
	    		config.set(path, currVal);
	    		saveConfig();
			}
			
    	}
    	return existsFlag;
    }
    
    public static boolean addEntryList(String key, List<String> response) {
    	String path = "Chat-Responses";
    	Boolean existsFlag = false;
    	if (config.get(path) != null) {
    		
			List<Map<?, ?>> currVal = config.getMapList(path);
			
			for (Map<?, ?> val : currVal) {
				if (val.containsKey(key)) {
					@SuppressWarnings("unchecked")
					List<String> currResponses = (List<String>) val.get(key);
					currResponses.addAll(response);
					
					Map<String, List<String>> newVal = new HashMap<String, List<String>>();
					newVal.put(key, currResponses);
					
					currVal.remove(val);
					currVal.add(newVal);
					config.set(path, currVal);
					saveConfig();
					existsFlag = true;
					break;
				}
			}
			
			if(existsFlag.equals(false)) {
				Map<String, List<String>> newVal = new HashMap<String, List<String>>();
	    		List<String> newResponse = new ArrayList<String>();
	    		
	    		newResponse.addAll(response);
	    		newVal.put(key, newResponse);
	    		currVal.add(newVal);
	    		
	    		config.set(path, currVal);
	    		saveConfig();
			}
			
    	}
		return existsFlag;
    }
    
    public static boolean removeEntry(String key) {
    	String path = "Chat-Responses";
    	Boolean removedFlag = false;
    	if (config.get(path) != null) {
    		
    		List<Map<?, ?>> currVal = config.getMapList(path);
    		
    		for (Map<?, ?> val : currVal) {
    			if (val.containsKey(key)) {
    				currVal.remove(val);
    				config.set(path, currVal);
    				saveConfig();
    				removedFlag = true;
    				break;
    			}
    		}
    	}
    	return removedFlag;
    }
    
    public static List<Map<?, ?>> getResponses() {
		List<Map<?, ?>> list = config.getMapList("Chat-Responses");
    	return list;
    }
    
	public static boolean checkPlayerConfig(Player p) {
    	List<String> players = config.getStringList("Player-Config");
    	return players.contains(p.getUniqueId().toString());
    }
    
    public static void addPlayerConfig(Player p) {
    	List<String> players = config.getStringList("Player-Config");
    	players.add(p.getUniqueId().toString());
    	config.set("Player-Config", players);
    	saveConfig();
    }
    
    public static void removePlayerConfig(Player p) {
    	List<String> players = config.getStringList("Player-Config");
    	players.remove(p.getUniqueId().toString());
    	config.set("Player-Config", players);
    	saveConfig();
    }
    
    public static String getULine() {
    	
    	Boolean booleanData = config.getBoolean("Configuration.Link-Underline");
    	
    	if (booleanData.equals(true)) {
    		return "&N";
    	} else if (booleanData.equals(false)) {
    		return "";
    	} else {
    		return null;
    	}
    	
    }
    
    public static Object getLinkColour() {
    	return config.get("Configuration.Link-Colour");
    }

}
