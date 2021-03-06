package com.lothrazar.searchcommands.command;

import java.util.ArrayList;
import java.util.List;   

import net.minecraftforge.fml.common.FMLCommonHandler; 
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandSearchItem  implements ICommand
{
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP; 
	public static int RADIUS;
	public static boolean SHOW_COORDS;

	public CommandSearchItem()
	{
		this.aliases = new ArrayList<String>();
		aliases.add("is");
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}
 
	@Override
	public List<String> getCommandAliases() 
	{  
		return aliases;
	}

	@Override
	public String getCommandName() 
	{ 
		return "searchitem";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) 
	{ 
		return "/"+getCommandName()+" <itemname>";
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1) 
	{ 
		return false;
	}
  
	@Override
	public void processCommand(ICommandSender sender, String[] args) 
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {return;}
			
		if (!(sender instanceof EntityPlayerMP)) {return;}
			
		EntityPlayerMP player = (EntityPlayerMP) sender;
		if (args.length < 1)
		{
			player.addChatMessage(new ChatComponentTranslation(getCommandUsage(sender))); 
			return;
		}
		
		String search = args[0].trim().toLowerCase(); // args[0] is the command name or alias used such as "is"
 
		ArrayList<String> foundMessages = new ArrayList<String>();
	  
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;
		
		int xMin = x - RADIUS;
		int xMax = x + RADIUS;

		int yMin = y - RADIUS;
		int yMax = y + RADIUS;

		int zMin = z - RADIUS;
		int zMax = z + RADIUS;
		
		int foundQty;
		int foundStacks;
		 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{
					TileEntity tile = player.worldObj.getTileEntity(new BlockPos(xLoop, yLoop, zLoop));
					
					if(tile == null || !(tile instanceof IInventory) ) {continue;}
					 
					IInventory inventory = (IInventory) tile;
					 
					foundQty = 0;
					foundStacks = 0;
					
					for (int slot = 0; slot < inventory.getSizeInventory(); slot++)//a break; will cancel this loop
					{
						ItemStack invItem = inventory.getStackInSlot(slot);
						if(invItem == null){continue;} //empty slot in chest (or container)
						 
						String invItemName = invItem.getDisplayName().toLowerCase(); 
						
						//find any overlap: so if x ==y , or if x substring of y, or y substring of x 
						if(search.equals(invItemName) 
								|| search.contains(invItemName)
								|| invItemName.contains(search))
						{ 
							System.out.println("SLOT " +slot);//TODO: can we say like.., chest column 4 row 3?
							foundStacks++;
							foundQty += invItem.stackSize; 
						} 
					} //end loop on current tile entity
					
					if(foundQty > 0)
					{
						//something was found in this box?
						foundMessages.add(itemLocDisplay(player,xLoop,yLoop,zLoop,foundQty,foundStacks));
					}
					
				}
			}
		}
 
		//LOOP on foundItems and chat out so each line of chat will be an zyx of a chest that contains
		// ? : we may also want to count number of items and item stacks found?
		
		int found = foundMessages.size();
		
		if(found == 0)
		{ 
			player.addChatMessage(new ChatComponentTranslation("No items found within "+RADIUS+" blocks of you."));
		}
		else
		{
			//Relay.addChatMessage(sender.getEntityWorld(),"Found at the following locations:");
			
			for (int i = 0; i < found; i++) 
			{  
				player.addChatMessage(new ChatComponentTranslation(foundMessages.get(i)));
		    }
		}
	}
	
	private static String itemLocDisplay(EntityPlayerMP player, int x, int y, int z ,int qty, int stacks)
	{  
		String s = (stacks == 1) ? "" : "s";
		String totalsStr = stacks + " stack"+s+"; ("+qty + " total).";
		if(SHOW_COORDS)
		{
			return x+", "+y+", "+z +" : "+ totalsStr;
		}
		int xDist,yDist,zDist;
		
		
		xDist = (int) player.posX - x;
		yDist = (int) player.posY - y;
		zDist = (int) player.posZ - z;
		
		//in terms of directon copmass:
		//North is -z;  south is +z		
		//east is +x, west is -x
		
		//so for Distances: 
		
		boolean isNorth = (zDist > 0);
		boolean isSouth = (zDist < 0);
		
		boolean isWest = (xDist > 0);
		boolean isEast = (xDist < 0);

		boolean isUp   = (yDist < 0);
		boolean isDown = (zDist > 0);
		
		String xStr = "";
		String yStr = "";
		String zStr = "";

		if(isWest) xStr = Math.abs(xDist) + " west ";
		if(isEast) xStr = Math.abs(xDist) + " east ";
		
		if(isNorth) zStr = Math.abs(zDist) + " north ";
		if(isSouth) zStr = Math.abs(zDist) + " south ";

		if(isUp)   yStr = Math.abs(yDist) + " up ";
		if(isDown) yStr = Math.abs(yDist) + " down ";
		
		 
		return xStr +  yStr +  zStr +": "+ totalsStr;
	}
}
