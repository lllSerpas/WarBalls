package wb.model;

import java.awt.Color;
import java.awt.Graphics;

public class ExplosionMark extends DisplayItem
{
	int size;
	public ExplosionMark(int x, int y, int size)
	{
		this.x =x ;
		this.y =y;
		this.size = size;
		isAlive = true;
		alpha = 1.0f;
		fadeTimer = 0.55f;//25f;
	}
	
	public void drawBody(Graphics g)
	{
		g = alphaSet(g, alpha);
		g.setColor(Color.white);

		g.fillOval(x-size/2, y-size/2, size, size);
		size-=40;
	}
	
	public String info()
	{
		return "hole to sit in";
	}

}
