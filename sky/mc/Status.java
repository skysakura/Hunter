package sky.mc;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Status {
	private static File file = new File(Sky.getInstance().getDataFolder(),"datas.yml");
	private static FileConfiguration datas = YamlConfiguration.loadConfiguration(file);
	public Status()
	{
	  if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static FileConfiguration getDatas()
	{
		return datas;
	}
	
	public static File getFile()
	{
		return file;
	}
	
	public static void playerWin(Player p)
	{
		if(hasData(p))
		{
			datas.set(p.getName() + ".wins", datas.getInt(p.getName() + ".wins", 0) + 1);
			datas.set(p.getName() + ".alls", datas.getInt(p.getName() + ".alls", 0) + 1);
		}else {
			createData(p);
			datas.set(p.getName() + ".wins", 1);
			datas.set(p.getName() + ".alls", 1);
		}
	}
	
	public static void createData(Player p) 
	{
		datas.set(p.getName() + ".wins", 0);
		datas.set(p.getName() + ".alls", 0);
		
	}

	public static boolean hasData(Player p)
	{
		return datas.contains(p.getName());
	}
}
