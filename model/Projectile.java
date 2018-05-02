package wb.model;

import java.awt.Graphics;

import wb.controller.Collider;
import wb.controller.GameController;

public abstract class Projectile
{
	GameController controller;
	protected Unit owner;
	protected int sx, sy;
	protected int mx, my;
	protected int fmx, fmy;
	protected int ex, ey;
	protected int fex, fey;
	protected int damage;
	protected int bleedDamage;
	protected int pid;
	protected int tid;
	protected boolean isAlive;
	protected boolean hasCollided;
	protected float deltaTime;
	protected float timeFuse;
	protected int size;
	protected boolean canPenetrate;
	protected int maxPenetrationDistance;
	protected int maxRange;
	protected int distanceTraveled;
	protected int tracerProbability;
	protected boolean canDraw = false;
	
	public abstract void drawBody(Graphics g);
	public abstract String info();
	
	public void updateStatus()
	{
		
	}
	
	public void setCanDraw(boolean mathIsDone)
	{
		canDraw = mathIsDone;
	}
	
	public boolean canDraw()
	{
//		damage = 0;
//		bleedDamage = 0;
		return canDraw;
	}
	
	public Unit getOwner()
	{
		return owner;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public int damage()
	{
		return damage;
	}
	
	public int bleedDamage()
	{
		return bleedDamage;
	}
	
	public int getDamage()
	{
		int d = damage;
		return d;
	}
	
	public int getBleedDamage()
	{
		int bd = bleedDamage;
		return bd;
	}
	
	public int getPid()
	{
		return pid;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setMaxPenetrationDistance(int max)
	{
		maxPenetrationDistance = max;
	}
	
	public int getMaxPenetrationDistance()
	{
		return maxPenetrationDistance;
	}
	
	public void setCanPenetrate(boolean canPenetrate)
	{
		this.canPenetrate = canPenetrate;
	}
	
	public boolean getCanPenetrate()
	{
		return canPenetrate;
	}
	
	public void updateTime(float time)
	{
		deltaTime+=time;
		updateStatus();
	}
	
	public void updateProjectile(int ex, int ey)
	{
		int[] midP = Collider.getMidPoints(sx, sy, ex, ey); //
		mx = midP[0];
		my = midP[1];
	}
	
	public void setMaxRange(int range)
	{
		maxRange = range;
	}
	
	public int getMaxRange()
	{
		return maxRange;
	}
	
	public void setDistanceTraveled()
	{
		distanceTraveled = (int) Collider.getDistance(sx, sy, ex, ey);
	}
	
	public int getDistanceTraveled()
	{
		return distanceTraveled;
	}
	
	public void setCollisionPoint(int x, int y)
	{
		mx = x;
		ex = x;
		my = y;
		ey = y;
		setHasCollided();
	}
	
	public Projectile returnProjectile()
	{
		return this;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void setAlive(boolean alive)
	{
		this.isAlive = alive;
	}
	
	public boolean hasCollided()
	{
		return hasCollided;
	}
	
	public void setHasCollided()
	{
		hasCollided = true;
		setDistanceTraveled();
//		System.out.println(distanceTraveled);
	}
	
	public void setSx(int x)
	{
		this.sx = x;
	}
	
	public void getSy(int y)
	{
		this.sy = y;
	}
	
	public int getSx()
	{
		return sx;
	}
	
	public int getSy()
	{
		return sy;
	}
	
	public void setMx(int x)
	{
		this.mx = x;
	}
	
	public void setMy(int y)
	{
		this.my = y;
	}
	
	public int getMx()
	{
		return mx;
	}
	
	public int getMy()
	{
		return my;
	}
	
	public void setEx(int x)
	{
		this.ex = x;
	}
	
	public void setEy(int y)
	{
		this.ey = y;
	}
	
	public int getEx()
	{
		return ex;
	}
	
	public int getEy()
	{
		return ey;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public float getTimeFuse()
	{
		return this.timeFuse;
	}
	
	public void setTimerFuse(float time)
	{
		this.timeFuse = time;
	}
}
