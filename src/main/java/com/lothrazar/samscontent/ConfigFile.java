package com.lothrazar.samscontent;

import net.minecraftforge.common.config.Configuration;

import com.lothrazar.command.CommandHome;
import com.lothrazar.command.CommandWorldHome;


public class ConfigFile
{
	public boolean searchtrade;
	public boolean searchitem;
	public boolean enderchest;
	public boolean home;
	public boolean worldhome;

	public static Configuration config;
	public ConfigFile(Configuration c)
	{
		config=c;
		String category;



		category = "commands";
		

		home = config.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed.");
		

		CommandHome.REQUIRES_OP = config.getBoolean("home_needs_op",category, false,
    			"Using /home is restricted to players with OP (or single player worlds with cheats enabled).");

		worldhome = config.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point."); 
		

		CommandWorldHome.REQUIRES_OP = config.getBoolean("worldhomehome_needs_op",category, false,
    			"Using /worldhome is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = config.getBoolean("searchtrade",category, true,
    			"Command that lets players search the trades of nearby villagers.  Result is only chat output."
    		);
		

		searchitem = config.getBoolean("searchitem",category, true,
    			"Command that lets players search nearby chests for items.   Result is only chat output."
    		);
		
		enderchest = config.getBoolean("enderchest",category, true,
    			"Command that lets players open their enderchest with a command, no item needed."
    		);



		if(config.hasChanged()){ config.save(); }
	}
}
