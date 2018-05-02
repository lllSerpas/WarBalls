package wb.model;

import java.awt.Color;
import java.awt.Graphics;

public class GraveMark extends DisplayItem
{
	int size;
	boolean friendly;
	public GraveMark(int x, int y, int size, boolean isFriend)
	{
		friendly = isFriend;
		size = size/3;
		this.x = x-size/2;
		this.y = y-size/2;
		this.size = size;
		this.isAlive = true;
	}
	
	public String info()
	{
		return "This is a permanent mark for the location of deaths";
	}

	public void drawBody(Graphics g)
	{
		if(!friendly)
			g.setColor(Color.red);
		else 
			g.setColor(Color.green);
		
		size  = 2;
		g.drawLine(x, y, x+size, y+size);//top left to bottom right
		g.drawLine(x+size, y, x, y+size);// top right to bottom left
	}

	public void updateTime(float time)
	{
		//Do nothing
	}
}
