package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.GameController;

public abstract class Item
{
	protected GameController controller;
	protected Body body;
	protected Unit owner;
	protected Color mainColor;
	protected int pid;
	protected int tid;
	protected int health;
	protected double maxHealth;
	protected int armor;
	protected int deathRate;
	protected int x;
	protected int y;
	protected int sizeX;
	protected int sizeY;
	protected int size;
	protected boolean isAlive;
	protected float deltaTime;
	protected int maxCount;
	protected int count;
	protected boolean isEmpty;
	protected double placeRate = 1;
	protected boolean stopPenetration;
	protected int tick;
	protected float tickTime;
	protected double hs = (health/maxHealth);
	protected int hx =  (int) (sizeX*hs);
	protected int hy =  (int) (sizeY*hs);
	protected int damageSizeX = sizeX - hx;
	protected int damageSizeY = sizeY - hy;
	protected boolean canPickupItem;
	protected boolean isWeapon;
	
	public abstract void drawBody(Graphics g);
	public abstract String info();
	
	public void useItem(int sx, int sy)
	{
		
	}
	
	public void updateStatus()
	{
		
	}
	
	public boolean isWeapon()
	{
		return isWeapon;
	}
	
	public void setCanPickup(boolean canPickUp)
	{
		canPickupItem = canPickUp;
	}
	
	public boolean getCanPickup()
	{
		return canPickupItem;
	}
	
	public Unit getOwner()
	{
		return owner;
	}
	
	public void setMainColor(Color color)
	{
		mainColor = color;
	}
	
	public Color getMainColor()
	{
		return mainColor;
	}
	
	public int getDamageSizeX()
	{
		return damageSizeX;
	}
	
	public int getDamageSizeY()
	{
		return damageSizeY;
	}

	public void updateTime(float time)
	{
		deltaTime += time;
		applyTickDamage(time);
		updateStatus();
	}
	
	public void updateIsAlive()
	{
		if(health<=0)
		{
			setAlive(false);
		}
	}
	
	public boolean canUse(float time, double placeRate, int ammo)
	{	
		if(deltaTime<placeRate)
		{
			if(count<=0)
				setIsEmpty(true);
			return false;
		}
		else if(count<=0)
		{
			setIsEmpty(true);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setIsEmpty(boolean status)
	{
		isEmpty = status;
	}
	
	public int getArmor()
	{
		return armor;
	}
	
	public int getPid()
	{
		return pid;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setX(int x)
	{
		this.x = x;
		body.setX(x);
	}
	
	public void setY(int y)
	{
		this.y = y;
		body.setY(y);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	

	protected void applyTickDamage(float time)
	{
		hs = (health/maxHealth);
		hx =  (int) (sizeX*hs);
		hy =  (int) (sizeY*hs);
		damageSizeX = sizeX - hx;
		damageSizeY = sizeY - hx;
		
		if(damageSizeX>sizeX)
			damageSizeX = sizeX;
		if(damageSizeY>sizeY)
			damageSizeY = sizeY;

		updateIsAlive();
		
		if(tick==0)
			return;
		
		if(deathRate>0)
			tickTime += time;
		if(tickTime>=tick)
		{
			health-=deathRate;
			tickTime=0;
		}
	}
	
	public void setHealth(int health)
	{
		this.health = health;
		maxHealth = health;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void setDeathRate(int rate)
	{
		deathRate = rate;
	}
	
	public int getDeathRate()
	{
		return deathRate;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void setAlive(boolean alive)
	{
		this.isAlive = alive;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void hit(int damage, int tickDamage)
	{
//		if(damage>0)
//			System.out.println(health+" -"+damage+" ="+(health-damage));
		
		health-=damage;
		deathRate = tickDamage;
	}
	
	public void setSizeX(int size)
	{
		sizeX = size;
	}
	
	public int getSizeX()
	{
		return sizeX;
	}

	public void setSizeY(int size)
	{
		sizeY = size;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return size;
	}

	public void setTick(int tick)
	{
		this.tick = tick;
	}
	
	public int getTickTime()
	{
		return tick;
	}
}
