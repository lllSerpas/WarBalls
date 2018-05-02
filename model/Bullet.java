package wb.model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import wb.controller.Collider;

public class Bullet extends Projectile
{
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	int[][] tracer = new int[9][2];
	public Bullet(int sx, int sy,  int ex, int ey, int damage, int bleedDamage, float timeFuse, int pid, int tid, Unit owner, int maxPenetrationDistance, int tracerProbability)
	{
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.owner = owner;
		fex = ex;
		fey = ey;
		this.damage = damage;
		this.bleedDamage = bleedDamage;
		this.pid = pid;
		this.tid = tid;
		isAlive = true;
		int[] midP = Collider.getMidPoints(sx, sy, ex, ey); //
		mx = midP[0];
		my = midP[1];
		fmx = mx;
		fmy = my;
		this.timeFuse = timeFuse;
		this.canPenetrate = true;
		this.maxPenetrationDistance = maxPenetrationDistance;
		setMaxRange((int)Collider.getDistance(sx, sy, ex, ey));
		this.tracerProbability = tracerProbability;
	}

	public Bullet(int sx, int sy,  int ex, int ey, int damage, int bleedDamage, float timeFuse, int pid, int tid, Unit owner, int tracerProbability)
	{
		this.sx = sx;
		this.sy = sy;
		this.ex = ex;
		this.ey = ey;
		this.owner = owner;
		fex = ex;
		fey = ey;
		this.damage = damage;
		this.bleedDamage = bleedDamage;
		this.pid = pid;
		this.tid = tid;
		isAlive = true;
		int[] midP = Collider.getMidPoints(sx, sy, ex, ey); //
		mx = midP[0];
		my = midP[1];
		fmx = mx;
		fmy = my;
		this.timeFuse = timeFuse;
		this.canPenetrate = false;
		this.maxPenetrationDistance = 10;
		this.setMaxRange((int)Collider.getDistance(sx, sy, ex, ey));
		this.tracerProbability = tracerProbability;
	}
	
	public Bullet(Projectile bullet)
	{
		this.sx = bullet.sx;
		this.sy = bullet.sy;
		this.ex = bullet.ex;
		this.ey = bullet.ey;
		this.owner = bullet.owner;
		this.damage = bullet.damage;
		this.bleedDamage = bullet.bleedDamage;
		this.pid = bullet.pid;
		this.tid = bullet.tid;
		this.isAlive = bullet.isAlive;
		int[] midP = Collider.getMidPoints(bullet.sx, bullet.sy, bullet.ex, bullet.ey); //
		mx = midP[0];
		my = midP[1];
		timeFuse = bullet.timeFuse;
		this.canPenetrate = bullet.canPenetrate;
		this.maxPenetrationDistance = bullet.maxPenetrationDistance;
		this.maxRange = bullet.maxRange;
		this.tracerProbability = bullet.tracerProbability;
	}
	
	public void updateStatus()
	{
		if(deltaTime>=timeFuse)
			setAlive(false);
	}
	
	public String info()
	{
		return "I am a bullet!";
	}
	
	public void drawBody(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		Color startColor = Color.red;
		Color midColor = Color.yellow;
		Color endColor = Color.white;
		GradientPaint gradient = new GradientPaint(sx, sy, startColor, fmx, fmy, midColor);
		GradientPaint gradient2 = new GradientPaint(mx, my, midColor, fex, fey, endColor);

		computeTracer();
		for(int i=0; i<tracer.length-1; i++)
		{
			if(i>tracer.length/2)
			{
				g2d.setPaint(gradient2);
				if((int)(Math.random()*tracerProbability)==0)
					g2d.drawLine(tracer[i][0], tracer[i][1], tracer[i+1][0], tracer[i+1][1]);
			}
			else
			{
				g2d.setPaint(gradient);
				if((int)(Math.random()*tracerProbability)==0)
					g2d.drawLine(tracer[i][0], tracer[i][1], tracer[i+1][0], tracer[i+1][1]);
			}
		}
	}
	
	public void computeTracer()
	{	
		int tmx, tmy;
		int[] midP = Collider.getMidPoints(sx, sy, mx, my); 
		tmx = midP[0];
		tmy = midP[1];
		tracer[0][0] = sx;
		tracer[0][1] = sy;
		midP = Collider.getMidPoints(sx, sy, tmx, tmy); 
		tracer[1][0] = midP[0];
		tracer[1][1] = midP[1];
		tracer[2][0] = tmx;
		tracer[2][1] = tmy;
		midP = Collider.getMidPoints(tmx, tmy, mx, my); 
		tmx = midP[0];
		tmy = midP[1];
		tracer[3][0] = tmx;
		tracer[3][1] = tmy;
		tracer[4][0] = mx;
		tracer[4][1] = my;
		midP = Collider.getMidPoints(mx, my, ex, ey); 
		tmx = midP[0];
		tmy = midP[1];
		midP = Collider.getMidPoints(mx, my, tmx, tmy); 
		tracer[5][0] = midP[0];
		tracer[5][1] = midP[1];
		tracer[6][0] = tmx;
		tracer[6][1] = tmy;
		midP = Collider.getMidPoints(tmx, tmy, ex, ey);
		tmx = midP[0];
		tmy = midP[1];
		tracer[7][0] = tmx;
		tracer[7][1] = tmy;
		tracer[8][0] = ex;
		tracer[8][1] = ey;
	}
}
