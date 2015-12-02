package com.lothrazar.searchcommands.command;

import java.util.ArrayList;
import java.util.List; 

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class CommandSearchTrades  implements ICommand
{
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP;
	public static boolean SHOW_COORDS; 
	public CommandSearchTrades()
	{
		this.aliases = new ArrayList<String>();
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
		return "searchtrade";
	}

	@Override
	public String getCommandUsage(ICommandSender ic) 
	{ 
		return  "/"+getCommandName()+" <itemname> <qty>";
	}

	@Override
	public List<String> getCommandAliases() 
	{ 
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender ic, String[] args) 
	{
		EntityPlayer p = (EntityPlayer)ic;
		if(args.length == 0)
		{  
			p.addChatMessage(new ChatComponentTranslation(getCommandUsage(ic))); 
			return;
		}
		
		String searching = args[0].toLowerCase();
		int searchingQty = -1;
		if(args.length > 1)
		{
			searchingQty = Integer.parseInt(args[1]);
			
			if(searchingQty < 0) {searchingQty  = 0;}
		}
		//step 1: get list of nearby villagers, seearch entities nearby in world
		double X = ic.getPosition().getX();
		double Z = ic.getPosition().getZ();
		double range = 64;
		//AxisAlignedBB.fromBounds(x1, y1, z1, x2, y2, z2)
		AxisAlignedBB searchRange = AxisAlignedBB.fromBounds(
				X + 0.5D - range, 0.0D, 
				Z + 0.5D - range, 
				X + 0.5D + range, 255.0D, 
				Z + 0.5D + range);
		
		//ic.getEntityWorld().getEntitiesWithinAABB(classEntity, bb)
		
		 List<EntityLiving> merchants = ic.getEntityWorld().getEntitiesWithinAABB(EntityLiving.class, searchRange);
		 List<IMerchant> villagers = new ArrayList<IMerchant>();
		 
		 //double check that it should be an adult villager
		 for (Object m : merchants) 
		 {
		     if(m instanceof IMerchant && ((EntityLiving)m).isChild() == false && (IMerchant)m != null)
		     {
		    	 villagers.add((IMerchant)m);
		     }
		 }
		 
		 MerchantRecipeList list;
		 MerchantRecipe rec;
		 ItemStack buy;
		 ItemStack buySecond;
		 ItemStack sell;
		 String disabled, m;
		 
		 ArrayList<String> messages = new ArrayList<String>();
		 boolean match = false;
		 
		 for(int i = 0; i < villagers.size(); i++)
		 { 
			 list = villagers.get(i).getRecipes(p); 
			 
			 for(int r = 0; r < list.size(); r++)
			 {
				 rec = (MerchantRecipe)list.get(r); 
				 disabled = (rec.isRecipeDisabled()) ? "[x]" : "";
				 
				 buy = rec.getItemToBuy();
				 buySecond = rec.getSecondItemToBuy();
				 
				 sell = rec.getItemToSell();

				 //match to any of the three possible items
				 //only match quantity if they enter one
				 
				 if(buy.getDisplayName().toLowerCase().contains(searching))
				 {
					 if(searchingQty < 0 || searchingQty == buy.stackSize)
						 match = true;
				 }
				 
				 if(buySecond != null && buySecond.getDisplayName().contains(searching))
				 {
					 if(searchingQty < 0 || searchingQty == buySecond.stackSize)
						 match = true;
				 }
				 
				 if(sell.getDisplayName().contains(searching))
				 {
					 if(searchingQty < 0 || searchingQty == sell.stackSize)
						 match = true;
				 }
				 
				 if(match)
				 {
					 m =  disabled  +
							 sell.stackSize +" "+sell.getDisplayName()+
							 " :: "+
							 buy.stackSize+" "+buy.getDisplayName();
					 messages.add(m); 
				 }
			 } 
		 }
 
		 for(int j = 0; j < messages.size();j++)
		 {
			p.addChatMessage(new ChatComponentTranslation(messages.get(j))); 
		 }
		 
		 if(messages.size() == 0)
		 {
			p.addChatMessage(new ChatComponentTranslation("No matching trades found in nearby villagers ("+range+"m).")); 
		 } 
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int i) 
	{ 
		return false;
	} 
}
