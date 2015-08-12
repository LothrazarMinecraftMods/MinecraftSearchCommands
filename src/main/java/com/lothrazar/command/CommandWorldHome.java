package com.lothrazar.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandWorldHome  implements ICommand
{
	public static boolean REQUIRES_OP; 

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic)
	{
		//if we dont require OP, then it always returns true
		return (REQUIRES_OP) ? ic.canCommandSenderUseCommand(2, "") : true; 
	}
	
	@Override
	public int compareTo(Object o)
	{ 
		return 0;
	}

	@Override
	public String getCommandName()
	{ 
		return "worldhome";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "worldhome";
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
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to worldhome in the overworld"));
			 return;
		}
		
		//this tends to always get something at y=64, regardless if there is AIR or not
		ChunkCoordinates coords = world.getSpawnPoint();
		 
		//so we keep moving up until we no longer intersect with the world
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
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] ic, int args)
	{ 
		return false;
	} 
}
