package wb.model;

import java.awt.Color;
import java.awt.Graphics;

public class BlackHole extends DisplayItem
{
	int size;
	int size2;
	Body body;
	int edge = 5;
	
	public BlackHole(int x, int y, int size)
	{
		this.x =x ;
		this.y =y;
		isAlive = true;
		this.size = size;
		size2 = size-edge;
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void drawBody(Graphics g)
	{	
		g.setColor(Color.black);
		g.fillOval(x+edge, y+edge, size2-edge, size2-edge);
	}

	public void updateTime(float time)
	{
		//Do nothing
	}
	
	public String info()
	{
		return "deeper hole";
	}

}
