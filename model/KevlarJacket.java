package wb.model;

import java.awt.Color;
import java.awt.Graphics;

import wb.controller.GameController;

public class KevlarJacket extends Item
{
	public KevlarJacket(int x, int y, int pid, int tid, Color mainColor, GameController controller, Unit owner, int count, boolean stopPenetration)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		isAlive = true;
		this.pid = pid;
		this.tid = tid;
		placeRate /= 1.85; ///divide per second Or *multiply for delay per second
		health = 500;
		deathRate = 0;
		armor = 1;
		this.count = count;
		this.maxCount = count;
		this.stopPenetration = stopPenetration;
		deltaTime = 0;
		sizeX = 45;
		sizeY = 25;
		canPickupItem = true;
		this.mainColor = mainColor;
		body = new SquareBody(x, y, sizeX, sizeY, pid, tid);
	}
	
	public void useItem(int sx, int sy)
	{
		if((canUse(deltaTime, placeRate, count)))
		{
			count--;
			Sandbag bag = new Sandbag(sx, sy, pid, tid, mainColor, controller, owner, 1);
			
			controller.addItem(bag);
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
		g.drawLine(x+sizeX-1, y, x+1, y+sizeY);
		g.drawLine(x+1, y, x+sizeX-1, y+sizeY);
		
		g.drawLine(x+sizeX/2, y+1, x+sizeX/2, y-1+sizeY);
		g.drawLine(x+1, y+sizeY/2, x-1+sizeX, y+sizeY/2);
	}
	
	public String info()
	{
		return "Sandbag: "+count;
	}
}
