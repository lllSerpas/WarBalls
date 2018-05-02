package wb.model;

import java.awt.Color;
import java.awt.Graphics;

public class DeathMark extends DisplayItem
{
	boolean isFriend;
	public DeathMark(int x, int y, int r, boolean isFriend)
	{
		this.isFriend = isFriend;
		this.x = x;
		this.y = y;
		this.cx = x+r/2;
		this.cy = y+r/2;
		this.r = r;
		isAlive = true;
		alpha = 1.0f;
		fadeTimer = 0.015f;
	}
	
	public boolean wasFriend()
	{
		return isFriend;
	}
	
	public void drawBody(Graphics g)
	{
		g = alphaSet(g, alpha);
		int size = r;
		int x = cx+r -((r/size)*size) - size/2;
		int y = cy+r -((r/size)*size) - size/2;
		
		g.setColor(Color.white);
		//Head
		g.fillOval(x, y, size, size);
		g.fillRoundRect(x+5, y+25, 25, 15, 10, 10);
		g.fillRoundRect(x+10, y+30, 15, 15, 30, 30);
		
		//Rest Of features
		g.setColor(Color.black);
		//Eyes
		g.fillOval(x+5, y+7, 10, 13);
		g.fillOval(x+20, y+7, 10, 13);
		
		//Nose
		g.fillOval(x+14, y+20, 3, 5);
		g.fillOval(x+18, y+20, 3, 5);
		
		//Teeth
		g.fillRoundRect(x+9, y+27, 3, 10, 0, 0);
		g.fillRoundRect(x+14, y+27, 3, 12, 0, 0);
		g.fillRoundRect(x+19, y+27, 3, 12, 0, 0);
		g.fillRoundRect(x+24, y+27, 3, 10, 0, 0);
		
		if(!isFriend)
			g.setColor(Color.red);
		else
			g.setColor(Color.green);
		g.drawLine(x, y, x+35, y+35);
		g.drawLine(x+35, y, x, y+35);
	}
	
	public String info()
	{
		return "Displayed when Unit is Destroyed";
	}
}


