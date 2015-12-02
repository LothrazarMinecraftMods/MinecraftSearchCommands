package com.lothrazar.searchcommands;

import org.apache.logging.log4j.Logger; 
 
import com.lothrazar.searchcommands.command.*; 

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
 
/*
 * August 2015 Sam Bassett imported from My old branch
 * https://github.com/PrinceOfAmber/SamsPowerups/commit/00a32f4a16739c307cf3c6149d2417dfff7ea3f3
 * 
 */
//TODO: fix // ,guiFactory = "com.lothrazar.samspowerups.gui.ConfigGuiFactory"
@Mod(modid = ModSamsContent.MODID,  useMetadata = true, updateJSON="https://raw.githubusercontent.com/LothrazarMinecraftMods/MinecraftSearchCommands/master/update.json") 
public class ModSamsContent
{
	@Instance(value = ModSamsContent.MODID)
	public static ModSamsContent instance;
	public static Logger logger;
	public final static String MODID = "searchcommands";

	public static ConfigFile settings;
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();
		 
		settings = new ConfigFile(new Configuration(event.getSuggestedConfigurationFile()));
  
		MinecraftForge.EVENT_BUS.register(instance); 
	}

	@EventHandler
	public void onServerLoad(FMLServerStartingEvent event)
	{
		if(ModSamsContent.settings.searchtrade) { event.registerServerCommand(new CommandSearchTrades()); }
		if(ModSamsContent.settings.searchitem) { event.registerServerCommand(new CommandSearchItem()); }
		if(ModSamsContent.settings.enderchest) { event.registerServerCommand(new CommandEnderChest()); }
		if(ModSamsContent.settings.gethome) { event.registerServerCommand(new CommandGetHome()); }
		if(ModSamsContent.settings.home) { event.registerServerCommand(new CommandWorldHome()); }
		if(ModSamsContent.settings.worldhome) { event.registerServerCommand(new CommandHome());}
	} 
}
