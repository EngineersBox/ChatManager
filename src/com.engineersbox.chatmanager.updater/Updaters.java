package com.engineersbox.chatmanager.updater;

import org.bukkit.Bukkit;

import com.engineersbox.chatmanager.updater.SpigotUpdater;

public class Updaters {

	public static void checkVersion(SpigotUpdater updater) {
		
    	Bukkit.getLogger().info("[ChatManager] Checking for updates...");
    	try {
			if (updater.checkForUpdates()) {
				Bukkit.getLogger().info("[ChatManager] An update was found! New version: " + updater.getLatestVersion() + " Download: " + updater.getResourceURL());
			} else {
				Bukkit.getLogger().info("[ChatManager] Plugin is up to date");
			}
		} catch (Exception e) {
			Bukkit.getLogger().warning("[ChatManager] Could not check for updates! Stacktrace:");
			e.printStackTrace();
		}
    	
    }
	
}