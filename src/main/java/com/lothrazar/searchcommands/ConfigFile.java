package com.lothrazar.searchcommands;

import net.minecraftforge.common.config.Configuration;
 
import com.lothrazar.searchcommands.command.*; 

public class ConfigFile
{
	public boolean searchtrade;
	public boolean searchitem;
	public boolean enderchest;
	public boolean home;
	public boolean worldhome;
	public boolean gethome;

	public static Configuration config;
	public ConfigFile(Configuration c)
	{
		config = c;
		String category;

		category = Configuration.CATEGORY_GENERAL;
		
		
		home = config.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed.");
		CommandHome.REQUIRES_OP = config.getBoolean("home.needs_op",category, false,
				"Restricted to players with OP (or single player worlds with cheats enabled).");
		gethome = config.getBoolean("gethome",category, true,
    			"Use /gethome to display your spawn point if defined by a bed.");
		CommandHome.REQUIRES_OP = config.getBoolean("gethome.needs_op",category, false,
				"Restricted to players with OP (or single player worlds with cheats enabled).");
		
		worldhome = config.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point."); 
		CommandWorldHome.REQUIRES_OP = config.getBoolean("worldhome._needs_op",category, false,
				"Restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = config.getBoolean("searchtrade",category, true,
    			"Command that lets players search the trades of nearby villagers.  Result is only chat output.");
		CommandSearchTrades.REQUIRES_OP = config.getBoolean("searchtrade.needs_op",category, false,
    			"Restricted to players with OP (or single player worlds with cheats enabled).");
		 
		//CommandSearchTrades.SHOW_COORDS = config.getBoolean("searchitem.show_coords",category, false,
    	//		"Show custom directions, or regular x,y,z coordinates.");
		
		searchitem = config.getBoolean("searchitem",category, true,
    			"Command that lets players search nearby chests for items.   Result is only chat output.");
		CommandSearchItem.REQUIRES_OP = config.getBoolean("searchitem.needs_op",category, false,
    			"Restricted to players with OP (or single player worlds with cheats enabled).");
	
		CommandSearchItem.SHOW_COORDS = config.getBoolean("searchitem.show_coords",category, false,
    			"Show custom directions, or regular x,y,z coordinates.");
		CommandSearchItem.RADIUS = config.getInt("searchitem.radius", category, 32, 2, 256, "Search radius");
		
		//TODO: config to show exact coords
		
		
		//could do ranges here?
		enderchest = config.getBoolean("enderchest",category, true,
    			"Command that lets players open their enderchest with a command, no item needed."    		);
		CommandEnderChest.REQUIRES_OP = config.getBoolean("enderchest.needs_op",category, true,
    			"Restricted to players with OP (or single player worlds with cheats enabled).");
		
		if(config.hasChanged()){ config.save(); }
	}
}
