package sky.mc;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

import sky.mc.util.Colors;
import sky.mc.util.Messages;
import sky.mc.util.Utils;

@SuppressWarnings("unused")
public class Hall {
	//Hunter Hider Machine Status
	private String name;
	private Player[] players = new Player[5];
	private int amount = 0;
	private Location a,b;
	private Location[] machines;
	private Location[] points;
	private boolean enable = false,start = false;
	private Timer timer = new Timer();
	private TimerTask tt,tg;
	private Listener lis;
	private int[] machines_p;
	private Inventory[] ins;
	private int winner = 0;
	private int failed = 0;
	private Player hunter = null;
	private Player[] hider = new Player[4];
	public Hall(String name,Location a,Location b,Location[] c,Location[] d,boolean enable)
	{
		if(a == null || b == null || c == null || name == null)
		{
			new Exception("无效的游戏参数");
		}
		this.name = name;
		this.a = a;
		this.b = b;
		this.machines = c;
		machines_p = new int[c.length];
		this.points = d;
		this.enable = enable;
		for(int i = 0;i <= machines_p.length;i++)
		{
			ins[i] = Bukkit.createInventory(null, 54);
		}
		reloadInventory();
		savePlayerData();
	}
	
	/**
	 * 临时保存玩家原本的背包，坐标数据
	 */
	private void savePlayerData() 
	{
		
	}

	/**
	 * 恢复玩家原本的数据
	 */
	private void reloadPlayerData()
	{
		
	}
	
	/**
	 * 重启游戏的界面
	 */
	private void reloadInventory() 
	{
		
	}

	public void shutDown()
	{
		reloadPlayerData();
	}
	
	public boolean isEnable()
	{
		return enable;
	}
	
	public boolean wasStarted()
	{
		return start;
	}
	
	/*
	 * 开始游戏
	 */
	private void startGame()
	{
		start = true;
		lis = new PlayerEvent();
		Bukkit.getPluginManager().registerEvents(lis, Sky.getInstance());
		tg = new Task2();
		timer.schedule(tg, 1000,1000);
	}
	
	public Location[] getPoints()
	{
		return points;
	}
	
	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}
	
	private void endGame()
	{
		tg.cancel();
		//Status.playerWin();
	}
	
	protected boolean onPlayerJoin(Player p)
	{
		if(players.length == amount || inHall(p))
		{
			p.sendMessage(Messages.Prefix + Colors.RED + "加入失败，可能原因: " + Messages.PlayerInHall + Messages.PlayerMax);
			return false;
		}
		if(start)
		{
			p.sendMessage(Messages.Prefix + Colors.RED + "该游戏已经开始了!");
			return false;
		}
		players[amount] = p;
		amount++;
		if(amount == 5)
		{
			tt = new Task1();
			timer.schedule(tt, 1000, 1000);
		}
		return true;
	}
	
	/*
	 * 当玩家离开游戏时
	 */
	protected void onPlayerLeave(Player p)
	{
		tt.cancel();
		for(int i = 0;i <= 5;i++)
		{
			Player[] temp = new Player[5];
			int j = 0;
			if(players[i].getName().equals(p.getName()))
			{
				continue;
			}else {
				temp[j] = players[i];
				j++;
			}
		}
		amount--;
		p.sendMessage(Messages.Prefix + Colors.GREEN + "你已离开游戏.");
	}
	
	/**
	 * 判断玩家是否在游戏场内
	 * @param p 玩家
	 * @return boolean
	 */
	public boolean inHall(Player p) 
	{
		for(Player pl : players)
		{
			if(pl.getName().equals(p.getName()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the location of this game's first point.
	 * @return
	 */
	public Location getBlockA()
	{
		return a;
	}
	
	/**
	 * Get the location of this game's second point.
	 * @return
	 */
	public Location getBlockB()
	{
		return b;
	}
	
	/**
	 * Get all location of machines that in this game.
	 * @return Location[]
	 */
	public Location[] getMachines()
	{
		return machines;
	}

	/**
	 * Get this name of this game.
	 * @return String name
	 */
	public String getName() 
	{
		return name;
	}
	
	/*
	 * 阻止玩家离开游戏区域
	 */
	private class PlayerEvent implements Listener
	{
		@EventHandler
		public void onEvent(PlayerMoveEvent event)
		{
			if(start)
			{
				if(!Utils.inArea(a, b, event.getTo()))
				{
					event.getPlayer().sendMessage(Messages.Prefix + Colors.RED + "你不能离开游戏区域.");
					event.setCancelled(true);
				}
			}
		}
		
		
		@EventHandler
		public void onEvent(PlayerInteractEvent event)
		{
			if(start)
			{
				Location p = event.getClickedBlock().getLocation();
				for(int i = 0;i <= machines.length;i++)
				{
					Location l = machines[i];
					if(p.getBlockX() == l.getBlockX() && p.getBlockY() == l.getBlockY() && p.getBlockZ() == l.getBlockZ())
					{
						event.getPlayer().openInventory(ins[i]);
						//Open the machines.
					}
				}
			}
		}
		
	}
	
	/*
	 * Tell something to players who is in this game.
	 */
	public void sendMessage(String msg)
	{
		for(Player pl : players)
		{
			pl.sendMessage(msg);
		}
	}
	
	/*
	 * 游戏开始倒计时
	 */
	private class Task1 extends TimerTask
	{
		int s = 30;
		@Override
		public void run() 
		{
			sendMessage(Messages.Prefix + Colors.GREEN + "游戏将在 " + Colors.YELLOW + s + Colors.GREEN + " 后开始。");
			s--;
			if(s == 0)
			{
				startGame();
				tt.cancel();
			}
		}
	}
	
	/*
	 * 检测是否满足游戏结束的条件
	 */
	private class Task2 extends TimerTask
	{
		@Override
		public void run() 
		{
			//监管者胜利
			if(failed == amount - 1)
			{
				endGame();
				Status.playerWin(hunter);
			}
			
			//幸存者胜利
			if(winner == amount - 1)
			{
				endGame();
				for(Player p : hider)
				{
					Status.playerWin(p);
				}
			}
			
			//监管者掉线
			if(!inHall(hunter) || amount - 1 == 0)
			{
				endGame();
			}
		}
	}
}
