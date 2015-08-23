package com.lothrazar.searchcommands.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandWorldHome  implements ICommand
{
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP; 
	public CommandWorldHome()
	{
		this.aliases = new ArrayList<String>();
	}
	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}
	
	@Override
	public int compareTo(Object o)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "worldhome";
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
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to worldhome in the overworld"));
			 return;
		}
		
		//this tends to always get something at y=64, regardless if there is AIR or not
		BlockPos coords = world.getSpawnPoint();
		 
		//so we keep moving up until we no longer intersect with the world
		player.setPositionAndUpdate(coords.getX(),  coords.getY(),  coords.getZ()); 
		while (player.getEntityBoundingBox() != null && world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()) != null && 
			  !world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F); 
	}
 
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] ic, int args)
	{ 
		return false;
	} 
}
