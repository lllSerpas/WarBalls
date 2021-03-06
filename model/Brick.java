package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class Brick extends Item
{
	public Brick(int x, int y, int pid, int tid, Color mainColor, GameController controller, Unit owner, int count)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		isAlive = true;
		this.pid = pid;
		this.tid = tid;
		placeRate /= 2.00;// 1.75; ///divide per second Or *multiply for delay per second
		health = health = WarBallsConfig.brickHealth;
		deathRate = 0;
		armor = 1;
		this.count = count;
		this.maxCount = count;
		this.stopPenetration = false;
		deltaTime = 0;
		sizeX = 180;
		sizeY = 180;
		canPickupItem = true;
		this.mainColor = mainColor;
		body = new SquareBody(x, y, sizeX, sizeY, pid, tid);
	}
	
	public void useItem(int sx, int sy)
	{
		if((canUse(deltaTime, placeRate, count)))
		{
			count--;
			Brick brick = new Brick(sx, sy, pid, tid, mainColor, controller, owner, 1);
			
			controller.addItem(brick);
			deltaTime = 0;
		}
	}

	public void drawBody(Graphics g)
	{
		g.setColor(new Color(209, 182, 116));	
		g.fillRoundRect(x, y, sizeX, sizeY, 7, 7);

		g.setColor(mainColor);//new Color(136, 86, 0));	
		g.fillRect(x+3, y+3, sizeX-5, sizeY-5);
		
		g.setColor(Color.black);
		g.drawRoundRect(x, y, sizeX, sizeY, 7, 7);
//		g.setColor(mainColor);
		
		
	}
	
	public String info()
	{
		return "Sandbag: "+count;
	}
}
