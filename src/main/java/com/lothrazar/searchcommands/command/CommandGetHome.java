package com.lothrazar.searchcommands.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation; 
import net.minecraft.world.World;

public class CommandGetHome implements ICommand
{
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP; 
	public CommandGetHome()
	{
		aliases = new ArrayList<String>();
	}
	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}
	
	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "gethome";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "/"+getName();
	}

	@Override
	public List getAliases()
	{ 
		return aliases;
	}

	@Override
	public void execute(ICommandSender ic, String[] args)
	{
		EntityPlayer player = ((EntityPlayer)ic); 
		World world = player.worldObj;


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
