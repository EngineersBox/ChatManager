package com.engineersbox.chatmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.engineersbox.chatmanager.Main;

public class AbstractFile {
   
    protected static Main main;
    static File file;
    protected static FileConfiguration config;
   
    public AbstractFile(Main main, String filename) {
        AbstractFile.main = main;
        AbstractFile.file = new File(main.getDataFolder(), filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AbstractFile.config = YamlConfiguration.loadConfiguration(file);
        config.addDefault("Configuration.Link-Colour", "&4");
        config.addDefault("Configuration.Link-Underline", true);
        config.addDefault("Chat-Responses", null);
        config.options().copyDefaults(true);
        saveConfig();
    }
    
    public static void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void addEntry(String key, String response) {
    	String path = "Chat-Responses";
    	if (config.get(path) != null) {
    		
			List<Map<?, ?>> currVal = config.getMapList(path);
			Boolean existsFlag = false;
			
			for (Map<?, ?> val : currVal) {
				if (val.containsKey(key)) {
					@SuppressWarnings("unchecked")
					List<String> currResponses = (List<String>) val.get(key);
					currResponses.add(response);
					
					Map<String, List<String>> newVal = new HashMap<String, List<String>>();
					newVal.put(key, currResponses);
					
					currVal.remove(currVal.indexOf(val));
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
    }
    
    public static void addEntryList(String key, List<String> response) {
    	String path = "Chat-Responses";
    	if (config.get(path) != null) {
    		
			List<Map<?, ?>> currVal = config.getMapList(path);
			Boolean existsFlag = false;
			
			for (Map<?, ?> val : currVal) {
				if (val.containsKey(key)) {
					@SuppressWarnings("unchecked")
					List<String> currResponses = (List<String>) val.get(key);
					currResponses.addAll(response);
					
					Map<String, List<String>> newVal = new HashMap<String, List<String>>();
					newVal.put(key, currResponses);
					
					currVal.remove(currVal.indexOf(val));
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
    }
    
    public static List<List<String>> getResponses() {
    	@SuppressWarnings("unchecked")
		List<List<String>> list = (List<List<String>>) config.get("Chat-Responses");
    	return list;
    }
   
}