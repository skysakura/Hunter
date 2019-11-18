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

import sky.mc.util.Colors;
import sky.mc.util.Messages;
import sky.mc.util.Utils;

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
	private TimerTask tt;
	private int[] machines_p;
	public Hall(String name,Location a,Location b,Location[] c,Location[] d,boolean enable)
	{
		if(a == null || b == null || c == null || name == null)
		{
			new Exception("��Ч����Ϸ����");
		}
		this.name = name;
		this.a = a;
		this.b = b;
		this.machines = c;
		machines_p = new int[c.length];
		this.points = d;
		this.enable = enable;
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
	 * ��ʼ��Ϸ
	 */
	private void startGame()
	{
		start = true;
		Bukkit.getPluginManager().registerEvents(new PlayerEvent(), Sky.getInstance());
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
		
	}
	
	protected boolean onPlayerJoin(Player p)
	{
		if(players.length == amount || inHall(p))
		{
			p.sendMessage(Messages.Prefix + Colors.RED + "����ԭ��: " + Messages.PlayerInHall + Messages.PlayerMax);
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
	 * ������뿪��Ϸʱ
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
		p.sendMessage(Messages.Prefix + Colors.GREEN + "�����뿪��Ϸ.");
	}
	
	/**
	 * �ж�����Ƿ�����Ϸ����
	 * @param p ���
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
	
	public Location getBlockA()
	{
		return a;
	}
	
	public Location getBlockB()
	{
		return b;
	}
	
	public Location[] getMachines()
	{
		return machines;
	}

	public String getName() 
	{
		return name;
	}
	
	/*
	 * ��ֹ����뿪��Ϸ����
	 */
	private class PlayerEvent implements Listener
	{
		@EventHandler
		public void onEvent(PlayerMoveEvent event)
		{
			if(!Utils.inArea(a, b, event.getTo()))
			{
				event.getPlayer().sendMessage(Messages.Prefix + Colors.RED + "�㲻���뿪��Ϸ����.");
				event.setCancelled(true);
			}
		}
		
		
		@EventHandler
		public void onEvent(PlayerInteractEvent event)
		{
			Location p = event.getClickedBlock().getLocation();
			for(Location l : machines)
			{
				if(p.getBlockX() == l.getBlockX() && p.getBlockY() == l.getBlockY() && p.getBlockZ() == l.getBlockZ())
				{
					if(event.getAction().name() == Action.RIGHT_CLICK_BLOCK.name() && event.getMaterial().getId() == Material.STONE_BUTTON.getId())
					{
						
					}
				}
			}
		}
		
	}
	
	public void sendMessage(String msg)
	{
		for(Player pl : players)
		{
			pl.sendMessage(msg);
		}
	}
	
	/*
	 * ��Ϸ��ʼ����ʱ
	 */
	private class Task1 extends TimerTask
	{
		int s = 30;
		@Override
		public void run() 
		{
			sendMessage(Messages.Prefix + Colors.GREEN + "��Ϸ���� " + Colors.YELLOW + s + Colors.GREEN + " ��ʼ��");
			s--;
			if(s == 0)
			{
				startGame();
				tt.cancel();
			}
		}
	}
}
