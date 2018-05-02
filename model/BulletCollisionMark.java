package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.Collider;

public class BulletCollisionMark extends DisplayItem
{
	double damage;
	int ex, ey;
	int numberOfFragments = 0;
	int minFragLength = 20;
	int minFragments = 5;
	int[] ep;
	int[] fragments;
	Color color;
	int spotSize = 5;
	
	public BulletCollisionMark(int x, int y, int ex, int ey, int damage, Color color)
	{	//The higher the damage the bigger the mark
		this.x = x;
		this.y = y;
		this.damage = damage;
		isAlive = true;
		alpha = 1.0f;
		fadeTimer = 0.10f;
		this.color = color;
		
		if(damage == 0)
			damage = 1;
		
		int maxFragLength = ((int)damage)/4 + minFragLength;
		numberOfFragments = ((int)damage)/25 + minFragments;
		fragments = new int[numberOfFragments*2];
		
		for(int i=0; i<numberOfFragments; i++)
		{
			int fragLength = (int) (Math.random()*maxFragLength);
			ep = Collider.getEndPoints(x, y, ex, ey, fragLength, 360);
			fragments[i*2] = ep[0];
			fragments[i*2+1] = ep[1];
//			System.out.println(fragLength+" "+numberOfFragments);
		}
	}
	
	public String info()
	{
		return "Hit Marker";
	}

	public void drawBody(Graphics g)
	{	
		g = alphaSet(g, alpha);
		g.setColor(color);
		
		
		for(int i=0; i<numberOfFragments; i++)
		{
			g.drawLine(x, y, fragments[i*2],fragments[i*2+1]);
		}
		
		g.fillOval(x-spotSize/2, y-spotSize/2, spotSize, spotSize);
	}
}
