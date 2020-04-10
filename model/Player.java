package wb.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class Player extends Unit
{
	public Player(int x, int y, int maxHealth, int pid, int tid, GameController controller)
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
		
		Color c = new Color(215, 167, 86); //211, 126
//		Color c = Color.lightGray;
		setMainColor(c);
		setCurrentColor(c);
		
		weapons = new ArrayList<Weapon>();
		items = new ArrayList<Item>();
		
		weapons.add(new Rifle(x, y, pid, tid, controller, this)); //5
		weapons.add(new MachineGun(x, y, pid, tid, controller, this));
		weapons.add(new Shotgun(x, y, pid, tid, controller, this));
		//weapons.add(new Sniper(x, y, pid, tid, controller, this));
		currentWeapon = weapons.get(0);
		items.add(new Sandbag(x, y, pid, tid, mainColor, controller, this, WarBallsConfig.sandBagsCarried));
		currentItem = null;

		setSize(35);
		body = new CircleBody(x, y, sizeX, pid, tid);
		body.setCollidable(true);
	}

	public void drawBody(Graphics g)
	{
		g.setColor(mainColor);
		//head
		g.fillOval(x, y, sizeX, sizeY);
		g.setColor(Color.green);
		g.drawString("Player : "+getLevel(), x, y+getSizeY()+10);
		
		drawHealthAndStatus(g);
		
//		if(getClosestEnemyUnit()!=null)
//			System.out.println(getClosestEnemyUnit().getPid()+" Enemy of "+getPid());
//		if(getClosestFriendlyUnit()!=null)
//			System.out.println(" "+getClosestFriendlyUnit().getPid()+" Friendly of "+getPid());
//		if(getClosestUnit()!=null)
//			System.out.println(" "+getClosestUnit().getPid()+" Cloest Unit of "+getPid());
	}
	
	public String info()
	{
		return "Health: "+health+"/"+maxHealth;
	}	
}
