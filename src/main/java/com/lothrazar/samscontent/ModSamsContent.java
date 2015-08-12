package com.lothrazar.samscontent;

import org.apache.logging.log4j.Logger; 
import com.lothrazar.command.*; 
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
 
/*
 * August 2015 Sam Bassett imported from My old branch
 * https://github.com/PrinceOfAmber/SamsPowerups/commit/00a32f4a16739c307cf3c6149d2417dfff7ea3f3
 * 
 */
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = ModSamsContent.MODID, version = ModSamsContent.VERSION	, canBeDeactivated = false, name = ModSamsContent.NAME, useMetadata = true) 
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
	public static Logger logger;
	public final static String MODID = "samscontent";
	public static final String VERSION = "1";
	public static final String NAME = "Sam's Content";

	public static Configuration config;
	public static ConfigFile settings;
	
	
	//TODO: try asm out http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();
		
		config = new Configuration(event.getSuggestedConfigurationFile());
 
		settings = new ConfigFile();
  
     	 
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register( instance);
     	 
	}

	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		if(ModSamsContent.settings.searchtrade) { event.registerServerCommand(new CommandSearchTrades()); }
		if(ModSamsContent.settings.searchitem) { event.registerServerCommand(new CommandSearchItem()); }
		if(ModSamsContent.settings.enderchest) { event.registerServerCommand(new CommandEnderChest()); }
		 
	
		 
		//todo: config entries for these two
		
		if(ModSamsContent.settings.home) { event.registerServerCommand(new CommandWorldHome()); }
		if(ModSamsContent.settings.worldhome) { event.registerServerCommand(new CommandHome());}
	}
 
	  
}
