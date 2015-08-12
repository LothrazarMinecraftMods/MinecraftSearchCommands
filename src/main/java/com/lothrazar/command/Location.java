package com.lothrazar.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
 
public class Location
{
	public double X;
	public double Y;
	public double Z;
	public int index;
	public int dimension = 0;//  : this is unused right now
	public String name;
	
	public Location(int idx,double pX, double pY, double pZ, int d, String pname)
	{
		X = pX;
		Y = pY;
		Z = pZ;
		index = idx;
		dimension = d;
		name = pname;
		if(name == null) name = "";
	}
	public Location(int idx,EntityPlayer p, String pname)
	{
		X = p.posX;
		Y = p.posY;
		Z = p.posZ;
		index = idx;
		dimension = p.dimension;
		name = pname;
		if(name == null) { name = ""; }
	}
	public Location(String csv)
	{
		String[] pts = csv.split(",");
		X = Double.parseDouble(pts[0]);
		Y = Double.parseDouble(pts[1]);
		Z = Double.parseDouble(pts[2]);
		dimension = Integer.parseInt(pts[3]);
		if(pts.length > 4) name = pts[4]; 
		if(name == null) name = "";
	}
	
	public String toCSV()
	{
		if(name == null) name = "";
		return X+","+Y+","+Z + ","+dimension + ","+name;		
	}
	
	public String toDisplay()//different from toCSV, since we round off the numbers and format 
	{
		if(name == null) name = "";
		String showName = " ";
		if(name != null && name.isEmpty() == false) showName = "  :  "+name;
		
		return "["+index + "] "+Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z) + showName;		
	} 
	
	public String toDisplayShort() 
	{
		if(name == null) name = "";
		String showName = " ";
		if(name != null && name.isEmpty() == false) showName = "  :  "+name;
		
		return Math.round(X)+", "+Math.round(Y)+", "+Math.round(Z) ;		
	} 
	
	public String toDisplayNoCoords()
	{
		return name+" (Height "+MathHelper.floor_double(Y)+")";
	}
}
