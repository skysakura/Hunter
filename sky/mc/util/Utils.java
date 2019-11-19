package sky.mc.util;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	public static int min(int x , int y)
	{
		if(x < y)
		{
			return x;
		}else {
			return y;
		}
	}
	
	public static int max(int x , int y)
	{
		if(x < y)
		{
			return y;
		}else {
			return x;
		}
	}
	
	public static double min(double x , double y)
	{
		if(x < y)
		{
			return x;
		}else {
			return y;
		}
	}
	
	public static double max(double x , double y)
	{
		if(x < y)
		{
			return y;
		}else {
			return x;
		}
	}
	
	public static boolean inArea(Location a,Location b,Location c)
	{
		double x1 = a.getX(),x2 = b.getX(),x3 = c.getX();
		double y1 = a.getY(),y2 = b.getY(),y3 = c.getY();
		double z1 = a.getZ(),z2 = b.getZ(),z3 = c.getZ();
		if(min(x1,x2) <= x3 && x3 <= max(x1,x2))
		{
			if(min(y1,y2) <= y3 && y3 <= max(y1,y2))
			{
				if(min(z1,z2) <= z3 && z3 <= max(z1,z2))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack newItemStack(String name,String[] lore,Material m,int data)
	{
		ItemStack i;
		if(data == 0)
		{
			i = new ItemStack(m);
		}else
		{
			i = new ItemStack(m,1,(short)1,(byte)data);
		}
		
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		ArrayList<String> array = new ArrayList<String>();
		for(String lo : lore)
		{
			array.add(lo);
		}
		im.setLore(array);
		i.setItemMeta(im);
		return i;
	}
	
	public static String getRandom(int min, int max)
	{
		Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return String.valueOf(s);
	}
}
