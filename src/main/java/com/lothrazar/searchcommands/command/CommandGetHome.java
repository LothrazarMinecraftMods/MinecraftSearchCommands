package com.lothrazar.searchcommands.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;  

public class CommandGetHome implements ICommand
{
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP; 
	public CommandGetHome()
	{
		aliases = new ArrayList<String>();
	}
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canCommandSenderUseCommand(2, this.getCommandName()) : true; 
	}
	
	@Override
	public int compareTo(ICommand arg0)
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
	public ArrayList<String> getCommandAliases()
	{ 
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args)
	{
		EntityPlayer player = ((EntityPlayer)ic); 
		//World world = player.worldObj;


		if(player.dimension != 0)
		{
			 player.addChatMessage(new ChatComponentTranslation("No home outside the overworld"));
			 return;
		}
		
		 BlockPos coords = player.getBedLocation(0);
		 
		 if(coords == null)
		 {
			 //has not been sent in a bed
			 //TODO: get the ID for this chat for translation purposes
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
	 
		 }
		 else
		 {
			 String pos = coords.getX()+", "+ coords.getY()+", "+ coords.getZ();	 
			 
			 player.addChatMessage(new ChatComponentTranslation("Your home bed is at "+pos));
				
		 }
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
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
