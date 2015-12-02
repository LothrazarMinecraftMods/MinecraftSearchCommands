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
	private ArrayList<String> aliases;
	public static boolean REQUIRES_OP; 

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender ic)
	{ 
		//if we dont require OP, then it always returns true
		return (REQUIRES_OP) ? ic.canCommandSenderUseCommand(2, "") : true; 
	}
	public CommandEnderChest()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ec");
		this.aliases.add("enderchest");
		this.aliases.add(getCommandName().toUpperCase());
	}
	
	@Override
	public String getCommandName()
	{
		return "enderchest";
	}
	
	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/"+getCommandName();
	}
	
	@Override
	public ArrayList<String> getCommandAliases()
	{
		return this.aliases;
	}
	
	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) throws CommandException
	{
		EntityPlayer p = (EntityPlayer)icommandsender;
		p.displayGUIChest(p.getInventoryEnderChest());
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	public int compareTo(ICommand o)
	{
		return 0;
	}
}
