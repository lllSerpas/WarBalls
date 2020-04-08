package wb.model;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import wb.controller.Collider;

public class LaserBullet extends Projectile
{
	int[][] tracer = new int[9][2];
	public LaserBullet(int sx, int sy,  int ex, int ey, int damage, int bleedDamage, float timeFuse, int pid, int tid, Unit owner, int maxPenetrationDistance, int tracerProbability)
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

	public LaserBullet(int sx, int sy,  int ex, int ey, int damage, int bleedDamage, float timeFuse, int pid, int tid, Unit owner, int tracerProbability)
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
		canPenetrate = false;
		maxPenetrationDistance = 10;
		setMaxRange((int)Collider.getDistance(sx, sy, ex, ey));
		this.tracerProbability = tracerProbability;
	}
	
	public void updateStatus()
	{
		if(deltaTime>=timeFuse)
			setAlive(false);
		if(damage==0 && bleedDamage==0)
			setHasCollided();
	}
	
	public String info()
	{
		return "I am a Laser bullet!";
	}
	
	public void drawBody(Graphics g)
	{
		g.setColor(Color.red);
//		if(this.tid != playerTid)
		//	g.setColor(Color.cyan);

//		g.setColor(owner.mainColor);
//		g.drawLine(sx, sy, ex, ey);
	
		computeTracer();
		for(int i=0; i<tracer.length-1; i++)
		{
			if(i>tracer.length/2)
			{
				if((int)(Math.random()*tracerProbability)==0)
					g.drawLine(tracer[i][0], tracer[i][1], tracer[i+1][0], tracer[i+1][1]);
			}
			else
			{
				if((int)(Math.random()*tracerProbability)==0)
					g.drawLine(tracer[i][0], tracer[i][1], tracer[i+1][0], tracer[i+1][1]);
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
