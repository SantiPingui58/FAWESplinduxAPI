package me.santipingui58.fawe;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	public static Plugin pl;

	
	public static Plugin get() {
	    return pl;
	  }	
	

	@Override
	public void onEnable() {
		pl = this;

	}


	
}
