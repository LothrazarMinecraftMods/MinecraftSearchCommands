package com.lothrazar.searchcommands.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandGetHome implements ICommand
{
	public static boolean REQUIRES_OP; 

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic)
	{
		//if we dont require OP, then it always returns true
		return (REQUIRES_OP) ? ic.canCommandSenderUseCommand(2, "") : true; 
	}
	
	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getCommandName()
	{ 
		return "gethome";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "/"+getCommandName();
	}

	@Override
	public List getCommandAliases()
	{ 
		return null;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args)
	{
		EntityPlayer player = ((EntityPlayer)ic); 
		World world = player.worldObj;


		if(player.dimension != 0)
		{
			 player.addChatMessage(new ChatComponentTranslation("No home outside the overworld"));
			 return;
		}
		
		 ChunkCoordinates coords = player.getBedLocation(0);
		 
		 if(coords == null)
		 {
			 //has not been sent in a bed
			 //TODO: get the ID for this chat for translation purposes
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
	 
		 }
		 else
		 {
			 String pos = coords.posX+", "+ coords.posY+", "+ coords.posZ;	 
			 
			 player.addChatMessage(new ChatComponentTranslation("Your home bed is at "+pos));
				
		 }
		
	}
 
	@Override
	public List addTabCompletionOptions(ICommandSender ic, String[] args)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i)
	{
		// TODO Auto-generated method stub
		return false;
	}


}
