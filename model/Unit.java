package wb.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import wb.controller.Collider;
import wb.controller.GameController;
import wb.controller.MovementController;
import wb.main.WarBallsConfig;

public abstract class Unit 
{
	GameController controller;
	MovementController movementController;
	protected int level;
	protected ArrayList<Weapon> weapons;
	protected Weapon currentWeapon;
	protected int currentWeaponIndex;
	protected int maxWeaponIndex;
	protected ArrayList<Item> items;
	protected Item currentItem;
	protected int currentItemIndex;
	protected int maxItemIndex;
	protected Body body;
	protected int pid;
	protected int tid;
	protected int health;
	protected double maxHealth;
	protected int bleedRate;
	protected int x;
	protected int y;
	protected int cx;
	protected int cy;
	protected int sizeX;
	protected int sizeY;
	protected boolean alive;
	protected float deltaTime;
	protected int tick;
	protected float tickTime;
	protected Color mainColor;
	protected Color currentColor;
	protected double hs;
	protected int hx;
	protected int hy;
	protected int damageSizeX;
	protected int damageSizeY;
	protected int speed;
	protected boolean hasBeenAttacked;
	protected float timeAlive = 0.0f;
	
	protected int bleedHealAmount = WarBallsConfig.defaultBleedHealAmount;
	protected int healthHealAmount = WarBallsConfig.defaultHealthHealAmount;
	
	protected float healTimer = WarBallsConfig.defaultHealTimer;
	protected float minHealTimer = WarBallsConfig.minHealTimer;;
	
	protected float timeSinceLastHeal;
	protected boolean nearItem;
	protected Item closestItem;
	protected boolean nearUnit;
	protected Unit closestUnit;
	protected Unit closestEnemyUnit;
	protected Unit closestFriendlyUnit;
	protected Unit attacker;
	protected boolean nearGoodUnit;
	protected boolean canPickUpItem;
	protected boolean isCollidable;
	protected boolean isShootable;
	protected int ammoArc;
	public abstract String info();
	public abstract void drawBody(Graphics g);
	
	public void levelUp()
	{
		level++;
//		if((level+1)%2==0)
			increaseHealthHealAmount();
	
//		if((level)%3==0 && level!=0)
			increaseBleedHealAmount();
		
		decreaseHealTimer();
		maxHealth+= WarBallsConfig.maxHealthIncrease;
		health = (int)maxHealth;
	}
	
	public void increaseHealthHealAmount()
	{
		healthHealAmount += WarBallsConfig.increaseHealthHealAmount;
		if(healthHealAmount>=WarBallsConfig.maxHealthHeal)
		{
			healthHealAmount=WarBallsConfig.maxHealthHeal;
		}
	}
	
	public void increaseBleedHealAmount()
	{
		bleedHealAmount += WarBallsConfig.increaseBleedHealAmount;
		if(bleedHealAmount>=WarBallsConfig.maxBleedHeal)
		{
			bleedHealAmount = WarBallsConfig.maxBleedHeal;
		}
	}
	
	public void decreaseHealTimer()
	{
		healTimer-=WarBallsConfig.decreaseHealTimer;
		if(healTimer<= WarBallsConfig.minHealTimer)
		{
			healTimer = WarBallsConfig.minHealTimer;
		}
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setNearUnit(boolean collided)
	{
		nearUnit = collided;
	}
	
	public boolean isNearUnit()
	{
		boolean nU = nearUnit;
		nearUnit = false;
		return nU;
	}
	
	public boolean isNearGoodUnit()
	{
		boolean nU = nearGoodUnit;
		nearGoodUnit = false;
		return nU;
	}
	
	public void setNotNearAnyUnit()
	{
		nearGoodUnit = false;
		nearUnit = false;
	}
	
	public void setNearItem(boolean collided)
	{
		nearItem = collided;
	}
	
	public boolean isNearItem()
	{
		boolean nI = nearItem;
		nearItem = false;
		return nI;
	}
	
	public void setClosestItem(Item it)
	{
		closestItem=it;
		if(it!=null)
		{
			if(it.getPid()== this.pid || it.getTid()==this.tid)
			{
				if(it.canPickupItem)
					canPickUpItem = true;
				closestItem = it;
			}
			else
			{
				canPickUpItem = false;
				closestItem = null;
			}
		}
	}
	
	public Item getClosestItem()
	{
		if(closestItem!=null && closestItem.isAlive())
			return closestItem;
		else 
			return null;
	}
	
	public void setClosestUnit(Unit u)
	{
		closestUnit=u;
		if(u!=null)
		{
			if(u.getTid()==this.tid)
			{
				nearGoodUnit = true;
				closestUnit = u;
			}
			else
			{
				nearGoodUnit = false;
			}
		}
	}
	
	public Unit getClosestUnit()
	{
		return closestUnit;
	}
	
	public void setClosestEnemyUnit(Unit u)
	{
		closestEnemyUnit=u;
		if(u!=null)
		{
			if(u.getTid()!=this.tid)
			{
				closestEnemyUnit = u;
			}
		}
	}
	
	public Unit getClosestEnemyUnit()
	{
		return closestEnemyUnit;
	}
	
	public void setClosestFriendlyUnit(Unit u)
	{
		closestFriendlyUnit=u;
		if(u!=null)
		{
			if(u.getTid()==this.tid)
			{
				closestFriendlyUnit = u;
			}
		}
	}
	
	public Unit getClosestFriendlyUnit()
	{
		return closestFriendlyUnit;
	}
	
	public void setMovementController(MovementController movementController)
	{
		this.movementController = movementController;
	}
	
	public MovementController getMovementController()
	{
		return movementController;
	}
	
	public void heal()
	{	
		if(timeSinceLastHeal<healTimer)
			return;
		
		timeSinceLastHeal = 0;
		if(bleedRate>0)
		{
			bleedRate-=bleedHealAmount;
			if(bleedRate<0)
				bleedRate=0;
			return;
		}
		
		health+=healthHealAmount;
		if(health>maxHealth)
			health = (int) maxHealth;
	}
	
	public void setHasBeenAttack(boolean hasBeenAttacked)
	{
		this.hasBeenAttacked = hasBeenAttacked;
	}
	
	public boolean hasBeenAttacked()
	{
		return hasBeenAttacked;
	}
	
	public void setHealAmount(int heal)
	{
		healthHealAmount = heal;
	}
	
	public int getHealAmount()
	{
		return healthHealAmount;
	}
	
	public int getBleedHealAmount()
	{
		return bleedHealAmount;
	}
	
	public float getHealTimer()
	{
		return healTimer;
	}
	
	public void drawHealthAndStatus(Graphics g)
	{
		//Current Ammo Arc
		ammoArc = 90; //90 Degrees Represents North. The arc starts and ends at the top of the player.
		
		//Health Bar
		g.setColor(Color.red);
		g.fillOval(cx-damageSizeX/2, cy-damageSizeY/2, damageSizeX, damageSizeX);
		
		//Bleed Arc
		g.setColor(Color.black);
		if(bleedRate>0)
		{	
			int angle = 10;
			int bleedRateArc = 60;//45;//180;
			for(int i=0; i<bleedRate; i++)
			{
				int x = Collider.getPolarRangeOffSetX(cx, sizeX/2, bleedRateArc*(i+1)+i+angle);
				int y = Collider.getPolarRangeOffSetY(cy, sizeY/2, bleedRateArc*(i+1)+i+angle);
				g.drawLine(cx, cy, x, y);
			}
		}	
		
		//Weapon / Item Status
		g.setColor(Color.red);
		g.drawOval(x, y, sizeX, sizeY);
		
		//Weapon Reload Arc
		if(currentWeapon!=null && currentWeapon.isReloading)
		{	
			int arc = currentWeapon.getReloadArc();
			g.setColor(Color.green);
			g.drawArc(cx-sizeX/2, cy-sizeY/2, sizeX, sizeY, ammoArc, arc);
		}
		
		//Weapon Ammo Arc
		if(currentWeapon!=null && !currentWeapon.isReloading)
		{	
			int bulletArcInterval = 0;
			if(currentWeapon.maxClip!=0)
				bulletArcInterval = 360/currentWeapon.maxClip; // 360 Bullets max, each bullet is 1 degree of ammoarc
			int arc = bulletArcInterval*currentWeapon.ammo;
			ammoArc = arc;
			g.setColor(Color.green);
			g.drawArc(cx-sizeX/2, cy-sizeY/2, sizeX, sizeY, 90, arc);
		}
	}
	
	public double getMaxHealth()
	{
		return maxHealth;
	}
	
	public void setSpeed(int speed)
	{
		this.speed= speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getDamageSizeX()
	{
		return damageSizeX;
	}
	
	public int getDamageSizeY()
	{
		return damageSizeY;
	}
	
	public void attack(int sx, int sy, int ex, int ey)
	{
		if(currentWeapon!=null)
		{
//			System.out.println("using weapon!");
			currentWeapon.useWeapon(sx, sy, ex, ey);
		}
		if(currentItem!=null && !body.isInHole)
		{
//			System.out.println("using item!");
			currentItem.useItem(sx-currentItem.getBody().getSizeX()/2, sy-currentItem.getBody().getSizeY()/2);
		}
	}
	
	public void updateTime(float time)
	{
		timeAlive+=time;
		deltaTime += time;
		timeSinceLastHeal +=time;
		applyTickDamage(time);
		
		
//		System.out.println(health+" "+bleedRate);
		
		if(getWeapon()!=null)
		{
			getWeapon().updateTime(time);
			
			if(this.getWeapon().isEmpty)
			{	
				setCurrentColor(Color.red);
			}
			else
				setCurrentColor(Color.green);
		}
		
		if(getItem()!=null)
		{
			getItem().updateTime(time);
			
			if(this.getItem().isEmpty)
			{
				setCurrentColor(Color.red);
//				removeItem(currentItemIndex);
			}
			else
				setCurrentColor(Color.green);
		}
	}

	public int getPid()
	{
		return pid;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setWeaponX(int x)
	{
		if(weapons!=null)
			for(Weapon w: weapons)
				w.setX(x);
	}
	
	public void setWeaponY(int y)
	{
		if(weapons!=null)
			for(Weapon w: weapons)
				w.setY(y);
	}
	
	public void setX(int x)
	{
		this.x = x;
		cx = x+sizeX/2;
		body.setX(x);
		setWeaponX(x);
		setItemsX(x);
	}

	public void setY(int y)
	{
		this.y = y;
		cy = y+sizeY/2;
		body.setY(y);
		setWeaponY(y);
		setItemsY(y);
	}
	
	public void setItemsX(int x)
	{
		if(items!=null)
			for(Item t: items)
				t.setX(x);
	}
	
	public void setItemsY(int y)
	{
		if(items!=null)
			for(Item t: items)
				t.setY(y);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	public void applyTickDamage(float time)
	{
		hs = (health/(maxHealth));
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
		
		if(bleedRate>0)
			tickTime += time;
		if(tickTime>=tick)
		{
			health-=bleedRate;
			tickTime=0;
			updateIsAlive();
		}
	}
	
	public void updateIsAlive()
	{
		if(health<=0)
		{
			setAlive(false);
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
	
	public void setBody(Body body)
	{
		this.body = body;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void setWeapon(int i)
	{
		currentItem = null;
		if(weapons.size()<=0)
		{
			setCurrentColor(Color.red);
			return;
		}
		currentWeapon = weapons.get(i);
		currentWeaponIndex = i;
	}
	
	public Weapon getWeapon()
	{
		return currentWeapon;
	}
	
	public ArrayList<Weapon> getAllWeapons()
	{
		return weapons;
	}
	
	public int getWeaponIndex()
	{
		return currentWeaponIndex;
	}
	
	public void setItem(int i)
	{
		currentWeapon = null;
		if(items.size()<=0)
		{
			setCurrentColor(Color.red);
			return;
		}
		currentItem = items.get(i);
		currentItemIndex = i;
	}
	
	public Item getItem()
	{
		return currentItem;
	}
	
	public ArrayList<Item> getAllItems()
	{
		return items;
	}
	
	public int getItemIndex()
	{
		return currentItemIndex;
	}
	
	public void addItem()
	{
//		ArrayList<Item> reloadItems = new ArrayList<Item>();
//		reloadItems = getAllItems();
		if(closestItem==null || !canPickUpItem)
			return;
		
		for(Item i: items)
		{
			if(closestItem.getClass() == i.getClass())
			{
				if(i.getCount()+1<=i.maxCount)
				{
					i.setCount(i.getCount()+1);
				}
				else
					return;
				
				closestItem.setAlive(false);
				
				if(i.isEmpty)
					i.setIsEmpty(false);
			}
		}
	}
	
	public void setCanPickUpItem(boolean canPickUp)
	{
		canPickUpItem = canPickUp;
	}
	
	public void addWeapon(Weapon weapon)
	{
		weapons.add(weapon);
	}
	
	public void dropItem(int i)
	{
		items.remove(i);
	}
	
	public void dropWeapon(int i)
	{
		weapons.remove(i);
	}
	
	public void removeItem(int i)
	{
		items.remove(currentItem);
		currentItem = null;
	}
	
	public void removeWeapon(int i)
	{
		weapons.remove(currentWeapon);
		currentWeapon = null;
	}
	
	public void setBleed(int bleed)
	{
		bleedRate = bleed;
	}
	
	public int getBleed()
	{
		return bleedRate;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public void setAlive(boolean alive)
	{
		this.alive = alive;
	}
	
	public void hit(int damage, int bleedDamage, Projectile projectile)
	{
		//if(!WarBallsConfig.friendlyUnitDamage && projectile.getTid()==this.tid)
			//return;
		
//		if(damage>=0)
//		{
//			System.out.println(health+" -"+damage+" = "+(health-damage)+" + bleed "+bleedDamage+" Current BleedRate: "+bleedRate);
//		}
		
		//if(projectile.getTid()!=this.tid)
		attacker = projectile.getOwner();
		hasBeenAttacked = true;
		health-=damage;
		bleedRate += bleedDamage;
		updateIsAlive();
	}
	
	public Unit getAttacker()
	{
		return attacker;
	}
	
	public Color getMainColor()
	{
		return mainColor;
	}
	
	public Color getCurrentColor()
	{
		return currentColor;
	}
	
	public void setMainColor(Color color)
	{
		mainColor = color;
	}
	
	public void setCurrentColor(Color color)
	{
		currentColor = color;
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
		sizeX = size;
		sizeY = size;
		cx = x + size/2;
		cy = y + size/2;
	}
	
	public void setSize(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		cx = x + sizeX/2;
		cy = y + sizeY/2;
	}
	
	public void setCx(int x)
	{
		cx = x;
	}

	public void setCy(int y)
	{
		cy = y;
	}
	
	public int getCy()
	{
		return cy;
	}
	
	public int getCx()
	{
		return cx;
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
