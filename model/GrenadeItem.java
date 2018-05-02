package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.Collider;
import wb.controller.GameController;

public class GrenadeItem extends Item //Should be item
{
	int arc;
	int explosionRadius;
	int explosionRange;
	int explosionFragments;
	int ex, ey, mx, my;
	float timeFuse;
	int damage;
	int bleedDamage;
	boolean isDangerous, canPenetrate;
	int i = 33; //speed (inversely proportional)
	
	public  GrenadeItem(int x, int y, int ex, int ey, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.ex = ex;
		this.ey = ey;
		this.pid = pid;
		this.tid = tid;
		isAlive = true;
		int[] midP = Collider.getMidPoints(x, y, ex, ey); //
		this.mx = midP[0];
		this.my = midP[1];
		this.size = 20;
		this.timeFuse = 1.75f;
		this.damage = 20;
		this.bleedDamage = 9;
		isWeapon = true;
		
		int maxRange = 225;
		explosionRadius = 360;
		explosionRange = 125;
		explosionFragments = 36;
		
		placeRate /= 1.75; ///divide per second Or *multiply for delay per second
		health = 100;
		deathRate = 0;
		armor = 3;
		this.stopPenetration = true;
		
		canPickupItem = true;
		this.isDangerous = false; //Cannot damage while placed
		this.canPenetrate = false; //Cannot itself penetrate
		
		body = new CircleBody(x, y, size, pid, tid);
//		body.setCollidable(false);
		
		double theta = Collider.getTheta(x, y, ex, ey);
		this.ex = Collider.getPolarRangeOffSetX(x, maxRange, theta);
		this.ey = Collider.getPolarRangeOffSetY(y, maxRange, theta);
	}
	
	public String info()
	{
		return "time alive: "+deltaTime+" Fuse: "+timeFuse;
	}
	
	boolean hasCollided = false;
	public void updateStatus()
	{
		int a1 = x;
		int a2 = y;
		int b1 = ex;
		int b2 = ey;
		int nx = (int)(a1 +(b1-a1)* 1/i);
		int ny = (int)(a2 +(b2-a2)* 1/i);
		
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
			hasCollided = true;
		}
		
		arc = (int)((360.0/timeFuse)*deltaTime);
		if(deltaTime>=timeFuse)
		{
			for(int i=0; i<=explosionFragments; i++)
			{
				int randRange = (int)(Math.random()*(explosionRange/4)*3)+(explosionRange/2);
				int[] points = Collider.getEndPoints(x+size/2, y+size/2, ex, ey, randRange, explosionRadius);
				Bullet bullet = new Bullet(x+size/2, y+size/2, points[0], points[1], damage, bleedDamage, 0.065f, pid, tid, owner, (explosionRange/2), 1);
//				LaserBullet bullet = new LaserBullet(sx, sy, points[0], points[1], damage, bleedDamage, 0.065f, pid, tid, controller.getPlayer().getTid(), owner, (explosionRange/5)*2, 2);
				controller.addProjectile(bullet);
			}
			
			//Probability of hole
			if(Math.random()<0.35)
				controller.addHole( new HoleMark(x+size/2-30, y+size/2-30, 60));	
			controller.addTemporaryDisplayItem(new ExplosionMark(x+size/2, y+size/2, ((explosionRange))));	
			setAlive(false);
		}
	}
	
	public void drawBody2(Graphics g)
	{
		
		g.setColor(Color.red);
		g.drawRect(x-size/2, y-size/2, size, size);
		g.drawOval(x-size/2, y-size/2, size, size);
		
		g.setColor(Color.red);
		g.drawArc(x-size/2-5, y-size/2-6, size+10, size+10, 90, 360-arc);
		
		Color c = new Color(0, 93, 0);
		g.setColor(c);
		g.fillOval(x-size/2, y-size/2, size, size);
		
		g.setColor(Color.black);
		g.drawLine(x-8, y-5, x-8, y+5);
		g.drawLine(x-3, y-9, x-3, y+9);
		g.drawLine(x+3, y-9, x+3, y+9);
		g.drawLine(x+8, y-5, x+8, y+5);
		g.drawLine(x-8, y-5, x+8, y-5);
		g.drawLine(x-9, y, x+9, y);
		g.drawLine(x-8, y+5, x+8, y+5);
		
		g.setColor(Color.gray);
		g.fillRect(x-3, y-size/2-2, size/3, size/2+2);
		g.setColor(owner.getMainColor());
		g.fillRect(x-2, y-size/2-2, 3, size/2);
	}
	
	public void drawBody(Graphics g)
	{
		
//		g.setColor(Color.yellow);
//		g.drawRect(x, y, size, size);
//		g.drawOval(x, y, size, size);
		
		g.setColor(Color.red);
		g.drawArc(x-5, y-6, size+10, size+10, 90, 360-arc);
		
		Color c = new Color(0, 93, 0);
		g.setColor(c);
		g.fillOval(x, y, size, size);
		
		g.setColor(Color.black);
		g.drawLine(x+size/2-8, y+size/2-5, x+size/2-8, y+size/2+5);
		g.drawLine(x+size/2-3, y+size/2-9, x+size/2-3, y+size/2+9);
		g.drawLine(x+size/2+3, y+size/2-9, x+size/2+3, y+size/2+9);
		g.drawLine(x+size/2+8, y+size/2-5, x+size/2+8, y+size/2+5);
		g.drawLine(x+size/2-8, y+size/2-5, x+size/2+8, y+size/2-5);
		g.drawLine(x+size/2-9, y+size/2, x+size/2+9, y+size/2);
		g.drawLine(x+size/2-8, y+size/2+5, x+size/2+8, y+size/2+5);
		
		g.setColor(Color.gray);
		g.fillRect(x+size/2-3, y+size/2-size/2-2, size/3, size/2+2);
		g.setColor(owner.getMainColor());
		g.fillRect(x+size/2-2, y+size/2-size/2-2, 3, size/2);
	}
	
}
