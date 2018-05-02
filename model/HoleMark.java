package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.Collider;

public class HoleMark extends DisplayItem
{
	int size;
	int size2;
	Body body;
	int[] lines;
	int edge = 5;
	
	public HoleMark(int x, int y, int size)
	{
		this.x =x ;
		this.y =y;
		isAlive = true;
		this.size = size;
		size2 = size-edge;
		lines = new int[60];
		
		for(int i=0; i<lines.length/2; i++)
		{
			int deg = (int)(Math.random()*360);
			int[] points = Collider.getEndPoints(x+size/2, y+size/2, 0, 0, size/2, deg);
			lines[2*i] = points[0];
			lines[2*i+1] = points[1];
		}
		
		body = new CircleBody(x+edge, y+edge, size2-edge,  -1,-1);
		body.setCollidable(true);
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void drawBody(Graphics g)
	{
		g.setColor(Color.black);
		g.setColor(new Color(109, 95, 77));
		g.fillOval(x, y, size, size);
		
		for(int i=0; i<lines.length/4; i++)
		{
			g.setColor(Color.black);
			g.drawLine(x+size/2, y+size/2, lines[2*i], lines[2*i+1]);
		}
		
//		g.setColor(Color.black);
//		g.fillOval(x+edge, y+edge, size2-edge, size2-edge);
	}

	public void updateTime(float time)
	{
		//Do nothing
	}
	
	public String info()
	{
		return "hold to sit in";
	}

}
