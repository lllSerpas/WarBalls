package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.Collider;
import wb.controller.GameController;

public class DamagingProjectile extends Item //Should be item
{
	int ex, ey, mx, my, speed;
	float timeFuse;
	int damage;
	int bleedDamage;
	double theta;
	boolean isDangerous, canPenetrate;
	
	public  DamagingProjectile(int sx, int sy, int ex, int ey, int pid, int tid, int damage, int bleedDamage, int range, int speed, float timeFuse, GameController controller, Unit owner, int count)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = sx;
		this.y = sy;
		this.ex = ex;
		this.ey = ey;
		this.pid = pid;
		this.tid = tid;
		isAlive = true;
		int[] midP = Collider.getMidPoints(x, y, ex, ey); //
		this.mx = midP[0];
		this.my = midP[1];
		this.size = 10;
		this.timeFuse = timeFuse;
		this.damage = damage;
		this.bleedDamage = bleedDamage;
		this.speed = speed;
		isWeapon = true;
		
		int maxRange = 500;
		
		placeRate /= 1.75; ///divide per second Or *multiply for delay per second
		health = 100;
		deathRate = 0;
		armor = 0;
		this.count = count;
		this.maxCount = count;
		this.stopPenetration = true;
		
		canPickupItem = true;
		this.isDangerous = false; //Cannot damage while placed
		this.canPenetrate = false; //Cannot itself penetrate
		
		body = new CircleBody(x, y, size, pid, tid);
//		body.setCollidable(false);
		
		theta = Collider.getTheta(x, y, ex, ey);
		this.ex = Collider.getPolarRangeOffSetX(x, maxRange, theta);
		this.ey = Collider.getPolarRangeOffSetY(y, maxRange, theta);
	}
	
	public String info()
	{
		return "time alive: "+deltaTime+" Fuse: "+timeFuse;
	}
	
	boolean hasCollided = false;
	int i = 10; //=this.speed;//speed of projectile?
	
	public void updateStatus()
	{
		int a1 = x;
		int a2 = y;
		int b1 = ex;
		int b2 = ey;
		int nx = (int)(a1 +(8*b1-a1)* 1/i);
		int ny = (int)(a2 +(8*b2-a2)* 1/i);
		
		if(i>1)
			i--;
		if(!hasCollided && controller.canMoveTo(this, nx, ny))
		{
			setX(nx);
			setY(ny);
		}
		else
		{
			body.setCollidable(false);
		
		}
		
		if(deltaTime>=timeFuse)
		{
			setAlive(false);
		}
	}
	
	public void drawBody(Graphics g)
	{
		Color c = new Color(0, 93, 0);
		g.setColor(Color.red);
		int size =10;
		//g.drawLine(this.x, this.y, this.x+2, this.y+2);
		g.fillOval(x, y, size, size);
		
	}
	
}
