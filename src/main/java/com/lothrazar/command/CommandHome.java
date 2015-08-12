package com.lothrazar.command;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandHome implements ICommand
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
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "home";
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
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to your home in the overworld"));
			 return;
		}
		
		 ChunkCoordinates coords = player.getBedLocation(0);
		 
		 if(coords == null)
		 {
			 //has not been sent in a bed
			 //TODO: get the ID for this chat for translation purposes
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
			 return;
		 }
		
		 Block block = world.getBlock(coords.posX, coords.posY, coords.posZ);
		 if (block.equals(Blocks.bed) || block.isBed(world, coords.posX, coords.posY, coords.posZ, null))
		 {
			 //then move over according to how/where the bed wants me to spawn
			 coords = block.getBedSpawnPosition(world, coords.posX, coords.posY, coords.posZ, null);
		 }
		 else
		 {
			 //spawn point was set, so the coords were not null, but player broke the bed (probably recently)
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
			 return;
		 }
		 
		 //TODO: make global/shared teleportPlayer class or function
		 //since this is copied from WorldHome
		 
		player.setPositionAndUpdate(coords.posX, coords.posY, coords.posZ); 
		while (!world.getCollidingBoundingBoxes(player, player.boundingBox).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		 
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F); 
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
