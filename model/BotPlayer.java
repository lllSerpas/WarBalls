package wb.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class BotPlayer extends Unit
{
	int playerTid;
	
	public BotPlayer(int x, int y, int maxHealth, Color mainColor, int pid, int tid, GameController controller)
	{
		this.controller = controller;
		this.pid = pid;
		this.tid = tid;
		setHealth(maxHealth);
		this.x = x;
		this.y = y;
		alive = true;
		speed = WarBallsConfig.speed;
		tick = WarBallsConfig.tick;
		
		setMainColor(mainColor);
		setCurrentColor(mainColor);
		
		weapons = new ArrayList<Weapon>();
		items = new ArrayList<Item>();
		int mainWeapon = (int)(Math.random()*5);
		//System.out.println(mainWeapon);
		switch(mainWeapon)
		{
			case 0: 
				weapons.add(new MachineGun(x, y, pid, tid, controller, this));
			break;
			case 1:
				weapons.add(new Rifle(x, y, pid, tid, controller, this));
			break;
			case 2:
				weapons.add(new Shotgun(x, y, pid, tid, controller, this));
			break;
			case 3: 
				weapons.add(new Sniper(x, y, pid, tid, controller, this));
			break;
			case 4:
				weapons.add(new GrenadeLauncher(x, y, pid, tid, controller, this));
			break;
		}
		weapons.add(new MachineGun(x, y, pid, tid, controller, this));
		//weapons.add(new Shotgun(x, y, pid, tid, controller, this));
		//weapons.add(new Sniper(x, y, pid, tid, controller, this));
		//weapons.add(new GrenadeLauncher(x, y, pid, tid, controller, this));
		//weapons.add(new Rifle(x, y, pid, tid, controller, this));
		currentWeapon = weapons.get(0);
		items.add(new Sandbag(x, y, pid, tid, mainColor, controller, this, WarBallsConfig.sandBagsCarried));
		currentItem = null;
		
		setSize(35); //Multiples of 35 allow for centered death Mark
		body = new CircleBody(x, y, sizeX, pid, tid);
		body.setCollidable(true);
	}
	
	public void drawBody(Graphics g)
	{
		if(this.currentWeapon!=null && this.currentWeapon.isEmpty)
			this.currentWeapon.reload();
		
		//Growing Damage
		g.setColor(mainColor);
		//head
		g.fillOval(x, y, sizeX, sizeY);

		
		g.setColor(Color.green);
			
		if(this.tid!=playerTid)
			g.setColor(Color.red);
		g.drawString(""+this.health+"/-"+this.bleedRate+": "+getLevel(), x, y+getSizeY()+10);
//		if(this.getWeapon()!=null)
//			g.drawString(this.getWeapon().info()+"  "+this.getPid(), x, y+getSizeY()+10);
//		else
//			g.drawString("  "+this.getPid(), x, y+getSizeY()+10);
//		
		drawHealthAndStatus(g);
	}
	
	public String info()
	{
		return "Health: "+health;
	}
}
