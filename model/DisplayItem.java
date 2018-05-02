package wb.model;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import wb.controller.GameController;

public abstract class DisplayItem
{
	GameController controller;
	protected int deltaTime;
	protected int x;
	protected int y;
	protected int cx;
	protected int cy;
	protected int r;
	protected boolean isAlive;
	protected float alpha;
	protected float fadeTimer;
	
	public abstract String info();
	
	public void drawBody(Graphics g)
	{
		
	}
	
	public  void drawBody(Graphics g, Graphics g2)
	{
		
	}
	
	public void updateTime(float time)
	{
		deltaTime += time;
		if(deltaTime%1==0)
		{
			alpha-= fadeTimer;
			if(alpha<=0)
			{
				alpha=0f;
				setIsAlive(false);
			}
		}
	}
	
	public Graphics alphaSet(Graphics g, float alpha)
	{
		Graphics2D g2d = (Graphics2D)g;
		//Set the opacity
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		return g;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void setIsAlive(boolean alive)
	{
		isAlive = alive;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public int getX()
	{
		return x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getCx()
	{
		return cx;
	}
	
	public int getCy()
	{
		return cy;
	}
}
