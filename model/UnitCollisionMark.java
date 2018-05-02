package wb.model;

import java.awt.Graphics;

public class UnitCollisionMark extends DisplayItem
{
	int[] specks;
	public UnitCollisionMark(int x, int y, int damage)
	{
		this.x = x;
		this.y = y;
		isAlive = true;
		alpha = 1.0f;
		fadeTimer = 0.005f;
		
		
	}
	
	public String info()
	{
		return "Blood Splatter";
	}

	public void drawBody(Graphics g)
	{
		// TODO Auto-generated method stub
		
	}

	public void updateTime(float time)
	{
		// TODO Auto-generated method stub
		
	}

}
