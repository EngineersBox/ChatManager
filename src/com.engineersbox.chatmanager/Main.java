package com.engineersbox.chatmanager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.engineersbox.chatmanager.AbstractFile;
//import com.engineersbox.chatmanager.Commands;

import com.engineersbox.chatmanager.Config;

public class Main extends JavaPlugin implements Listener {

	public static FileConfiguration config;
	public static File cfile;
	
	public void onEnable() {
		if (!getDataFolder().exists()) {
    		getDataFolder().mkdirs();
    	}
		
		new Config(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		//getCommand("cm").setExecutor(new Commands());
	}
	
	@Override
	public void onDisable() {
		AbstractFile.saveConfig();
	}
	
}
