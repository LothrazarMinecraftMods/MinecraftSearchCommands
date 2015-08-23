package com.lothrazar.searchcommands.command;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation; 
import net.minecraft.world.World;

public class CommandHome implements ICommand
{
	public static boolean REQUIRES_OP; 

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
		return "home";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "/"+getName();
	}

	@Override
	public List getAliases()
	{ 
		return null;
	}

	@Override
	public void execute(ICommandSender ic, String[] args)
	{
		EntityPlayer player = ((EntityPlayer)ic); 
		World world = player.worldObj;


		if(player.dimension != 0)
		{
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to your home in the overworld"));
			 return;
		}
		
		 BlockPos coords = player.getBedLocation(0);
		 
		 if(coords == null)
		 {
			 //has not been sent in a bed
			 //TODO: get the ID for this chat for translation purposes
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
			 return;
		 }
		 IBlockState bs  =world.getBlockState(coords);
		 Block block = (bs==null)?null: world.getBlockState(coords).getBlock();
		 
		 if (block != null && (block.equals(Blocks.bed) || block.isBed(world, coords, null)))
		 {
			 //then move over according to how/where the bed wants me to spawn
			 coords = block.getBedSpawnPosition(world, coords, null);
		 }
		 else
		 {
			 //spawn point was set, so the coords were not null, but player broke the bed (probably recently)
			 player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed."));
			 return;
		 }
		 
		 //TODO: make global/shared teleportPlayer class or function
		 //since this is copied from WorldHome
		 
		player.setPositionAndUpdate(coords.getX(),  coords.getY(),  coords.getZ()); 
		while (!world.getCollidingBoundingBoxes(player, player.getBoundingBox()).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		 
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F); 
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
