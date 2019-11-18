package sky.mc;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

import sky.mc.util.Messages;

public class Sky extends JavaPlugin{
	private static Sky instance;
	// <Hall_Name,Hall>
	private static HashMap<String,Hall> halls = new HashMap<String,Hall>();
	protected FileConfiguration config = this.getConfig();
	@Override
	public void onEnable()
	{
		instance = this;
		Messages.load();
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args)
	{
		if(label.equalsIgnoreCase("Sky"))
		{
			if(args.length == 0)
			{
				sender.sendMessage("");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("join") && args.length > 1)
			{
				if(halls.get(args[1]) != null)
				{
					
				}
			}
		}
		return false;
	}
	
	public static Sky getInstance()
	{
		return instance;
	}
	
	public static boolean regHall(Hall hall)
	{
		if(hall == null || halls.get(hall.getName()) != null)
		{
			return false;
		}
		halls.put(hall.getName(), hall);
		return true;
	}
}
