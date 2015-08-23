package com.lothrazar.searchcommands.command;
/*
 * 
 * imported from my 
 * 
 * https://github.com/PrinceOfAmber/BuildersDream_Minecraft/tree/master/mod_enderchestcommand/src/main/java/com/lothrazar/enderchestcommand
 * 
 * 
 * */
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandEnderChest implements ICommand
{
	public static boolean REQUIRES_OP; 

	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		//if we dont require OP, then it always returns true
		return (REQUIRES_OP) ? ic.canUseCommand(2, "") : true; 
	}
	
	private ArrayList<String> aliases;
	public CommandEnderChest()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ec");
		this.aliases.add("enderchest");
		this.aliases.add(getName().toUpperCase());
	}
	
	@Override
	public String getName()
	{
		return "enderchest";
	}
	
	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/"+getName();
	}
	
	@Override
	public ArrayList<String> getAliases()
	{
		return this.aliases;
	}
	
	@Override
	public void execute(ICommandSender icommandsender, String[] astring) throws CommandException
	{
		EntityPlayer p = (EntityPlayer)icommandsender;
		p.displayGUIChest(p.getInventoryEnderChest());
	} 
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
	
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
}
