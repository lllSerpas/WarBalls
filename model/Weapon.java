package wb.model;

import wb.controller.GameController;

public abstract class Weapon
{
	GameController controller;
	protected Unit owner;
	protected int damage;
	protected int bleedDamage;
	protected double fireRate =1;//shots per second //default 1
	protected double offSet; // in degrees
	protected int range;
	protected int x;
	protected int y;
	protected int maxClip;
	protected int ammo;
	protected int pid;
	protected int tid;
	protected float deltaTime;
	protected boolean isEmpty;
	protected boolean isReloading;
	protected float reloadTime;
	protected float timeReloading;
	protected int reloadArc;
	
	public abstract void useWeapon(int sx, int sy, int ex, int ey);
	public abstract String info();

	public Unit getOwner()
	{
		return owner;
	}
	
	public void setPid(int pid)
	{
		this.pid = pid;
	}
	
	public void setTid(int tid)
	{
		this.tid = tid;
	}
	
	public int getPid()
	{
		return pid;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setReloadTime(float time)
	{
		reloadTime = time;
	}
	
	public float getReloadTime()
	{
		return reloadTime;
	}
	
	public void setReloading()
	{
		isReloading = true;
	}
	
	public boolean canUse(float time, double fireRate, int ammo)
	{	
		if(deltaTime<fireRate)
		{
			if(ammo<=0)
				setIsEmpty(true);
			return false;
		}
		else if(ammo<=0)
		{
			setIsEmpty(true);
			return false;
		}
		else if(isReloading)
		{
			return false;
		}
		else
			return true;
	}
	
	public void updateTime(float time)
	{
		deltaTime+=time;
		
		if(isReloading)
		{
			timeReloading+= time;
			reloadArc = (int)((360.0/reloadTime)*timeReloading);
			if(timeReloading>=reloadTime)
			{
				ammo = maxClip;
				setIsEmpty(false);
				isReloading = false;
				timeReloading = 0;
				reloadArc = 0;
			}
		}
	}
	
	public int getReloadArc()
	{
		return reloadArc;
	}
	
	public void reload()
	{
		if(isReloading)// || ammo>0)
			return;
		
		ammo = 0;
		isEmpty = true;
		setReloading();
	}
	
	public void setIsEmpty(boolean status)
	{
		isEmpty = status;
	}
	
	public boolean getIsEmpty()
	{
		return isEmpty;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public int getBleedDamage()
	{
		return bleedDamage;
	}
	
	public int getAmmo()
	{
		return ammo;
	}
	
	public double getFireRate()
	{
		return fireRate;
	}
}
