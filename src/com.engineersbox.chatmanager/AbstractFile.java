package com.engineersbox.chatmanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        AbstractFile.config = YamlConfiguration.loadConfiguration(file);config.addDefault("User-Details.UseSQL", false);
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
    
    public static void addEntry(String path, String entry) {
    	if (config.contains(path)) {
    		if (config.get(path) instanceof List) {
    			List<String> currVal = config.getStringList(path);
    			currVal.add(entry);
    			config.set(path, currVal);
    		} else {
    			List<String> newVal = new ArrayList<String>();
    			newVal.add(config.getString(path));
    			newVal.add(entry);
    			config.set(path, newVal);
    		}
    	} else {
    		config.set(path, entry);
    	}
    }
   
}