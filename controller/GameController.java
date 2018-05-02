package wb.controller;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import wb.main.WarBallsConfig;
import wb.model.BattleModel;
import wb.model.BlackHole;
import wb.model.Brick;
import wb.model.DeathMark;
import wb.model.DisplayItem;
import wb.model.BotPlayer;
import wb.model.GhostMark;
import wb.model.GraveMark;
import wb.model.BulletCollisionMark;
import wb.model.HoleMark;
import wb.model.Item;
import wb.model.Player;
import wb.model.Projectile;
import wb.model.Sandbag;
import wb.model.Unit;
import wb.model.Weapon;
import wb.view.BattleView;

public class GameController
{
	protected int clickX;
	protected int clickY;
	protected float tick;
	protected boolean[] keys;
	protected int numKey = 7;
	protected int crossHairX;
	protected int crossHairY;
	protected int initialDirection =-1;
	protected float totalTime;
	protected int playerDeaths;
	protected int playerKills;
	protected int deadFriendlies= 0, deadEnemies= 0;
	
	private BattleModel field;
	private Unit player;
	private Unit unit;
	private BattleView renderer;
	private ArrayList<Unit> units;
	private ArrayList<Projectile>  projectiles;
	private ArrayList<Item>  items;
	private ArrayList<DisplayItem>  temporaryDisplayItems;
	private ArrayList<DisplayItem>  permanentDisplayItems;
	private ArrayList<HoleMark>  holes;
	private ArrayList<BlackHole>  blackHoles;
	private ArrayList<MovementController> movementControllers;
	protected float maxDeadTime = 2.25f;
	protected float deadTimer = 0.0f;
	protected int enemyTeamDeaths, friendlyTeamDeaths;
	protected float waveTimer;
	
	public GameController()
	{
		player = new Player(BattleModel.width/2, BattleModel.height/2+40, WarBallsConfig.friendlyHealth, 123, 321, this);
		field = new BattleModel(player, this);
		renderer = new BattleView(field);
		keys = new boolean[numKey];
		getData();
	}
	
	public void update(float deltaTime)
	{
		getData();
		tick += deltaTime;
		totalTime +=deltaTime;
		if(tick>=1)//At Every Second Tick
		{
			tick=0;
		}

		if(!player.isAlive())
		{
			deadTimer+=deltaTime;
		}
		
		updateModel(deltaTime);
		updatePlayer();
		updateBots(deltaTime);
	}
	
	public void getData()
	{
		blackHoles = field.getBlackHoles();
		holes = field.getHoles();
		units = field.getUnits();
		projectiles = field.getProjectiles();
		items = field.getItems();
		temporaryDisplayItems = field.getTemporaryDisplayItems();
		permanentDisplayItems = field.getPermanentDisplayItems();
		movementControllers = field.getMovementControllers();
	}
	
	public void keyPressed(KeyEvent e)
	{
		char keyPress = e.getKeyChar();
//		System.out.println(keyPress);
		if(keyPress=='w' || keyPress=='W')
			keys[0] = true;
		if(keyPress=='s' || keyPress=='S')
			keys[1] = true;
		if(keyPress=='a' || keyPress=='A')
			keys[2]= true;
		if(keyPress=='d' || keyPress=='D')
			keys[3] = true;
		if(keyPress=='q' || keyPress=='Q')
		{
			keys[6] = true;
			keys[4] = false;
		}
		if(keyPress=='r' || keyPress=='R')
		{
			if(player.getWeapon()!=null)
				player.getWeapon().reload();
		}
		if(keyPress=='e' || keyPress=='E')
		{
			player.addItem();
		}
		if(keyPress=='1')
			player.setWeapon(0);
		if(keyPress=='2')
			player.setWeapon(1);
		if(keyPress=='3')
			player.setWeapon(2);
		if(keyPress=='4')
			player.setItem(0);
	}

	public void keyReleased(KeyEvent e)
	{ 
		char keyPress = e.getKeyChar();
		if(keyPress=='w'|| keyPress=='W')
			keys[0] = false;
		if(keyPress=='s'|| keyPress=='S')
			keys[1] = false;
		if(keyPress=='a'|| keyPress=='A')
			keys[2] = false;
		if(keyPress=='d'|| keyPress=='D')
			keys[3] = false;	
		if(keyPress=='q'|| keyPress=='Q')
			keys[6] = false;
	}
	
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1 )
		{
			keys[4] = true;
			clickX = e.getX();
			clickY = e.getY();
		}
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			keys[5] = true;
			crossHairX = e.getX();
			crossHairY = e.getY();
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		if(keys[4])
		{
			clickX = e.getX();
			clickY = e.getY();
		}
		if(keys[5])
		{
			crossHairX = e.getX();
			crossHairY = e.getY();
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1 )
			keys[4] = false;
		if(e.getButton() == MouseEvent.BUTTON3 )
			keys[5] = false;
	}

	public void addProjectile(Projectile projectile)
	{
		projectiles.add(projectile);
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}
	
	public void addSandbag(ArrayList<Item> items, ArrayList<Unit> allUnits, int numBag)
	{
		int sizeX = 0;
		int sizeY = 0;
		int maxWidth = 0;
		int maxHeight = 0;
		int fx = 0;
		int fy = 0;
//		Sandbag bag;
//		for(int i=0; i<numBag; i++)
//		{
//			bag = new Sandbag((int)(Math.random()*maxWidth),(int)(Math.random()*maxHeight), 555, 555, Color.black, this, null, 1);
//			
//			sizeY = bag.getBody().getSizeY();
//			sizeX = bag.getBody().getSizeX();
//			maxWidth = BattleModel.width-sizeX;
//			maxHeight = BattleModel.height-sizeY;
//			fx = (int)(Math.random()*maxWidth);
//			fy = (int)(Math.random()*maxHeight);
//			
//			while(!canPlace(fx, fy, allUnits, items))
//			{
//				fx = (int)(Math.random()*maxWidth);
//				fy = (int)(Math.random()*maxHeight);
//			}
//			
//			bag.setX(fx);
//			bag.setY(fy);
//			items.add(bag);		
//		}
		
		Brick brick;
		for(int i=0; i<numBag; i++)
		{
			brick = new Brick((int)(Math.random()*maxWidth),(int)(Math.random()*maxHeight), WarBallsConfig.WallId, WarBallsConfig.WallTeamId, Color.black, this, null, 1);
			
			sizeY = brick.getBody().getSizeY()+35;
			sizeX = brick.getBody().getSizeX()+35;
			maxWidth = BattleModel.width-sizeX+35;
			maxHeight = BattleModel.height-sizeY+35;
			fx = (int)(Math.random()*maxWidth);
			fy = (int)(Math.random()*maxHeight);
			
			while(!canPlace(fx, fy, allUnits, items))
			{
				fx = (int)(Math.random()*maxWidth);
				fy = (int)(Math.random()*maxHeight);
			}
			
			brick.setX(fx);
			brick.setY(fy);
			items.add(brick);		
		}
	}
	
	public void addSandbagBarrier(ArrayList<Item> sandBags)
	{
		Sandbag bag;
		for(int i=0; i<(BattleModel.width); i++)
		{
			bag = new Sandbag(i*45, BattleModel.height/2, WarBallsConfig.WallId, WarBallsConfig.WallTeamId, Color.black, this, null, 1);
		
			sandBags.add(bag);		
		}
		
		for(int i=0; i<(BattleModel.height/50); i++)
		{
			bag = new Sandbag(BattleModel.width/2, 4*i*25, WarBallsConfig.WallId, WarBallsConfig.WallTeamId, Color.black, this, null, 1);
		
			sandBags.add(bag);		
		}
	}
	
	public void spawnUnit(ArrayList<Unit> units, ArrayList<Item> items, Unit unit)
	{
		int fx = unit.getX();
		int fy = unit.getY();
		
		while(!canPlace(fx, fy, units, items))
		{
			fx = (int)(Math.random()*(BattleModel.width-player.getSizeX()));
			fy = (int)(Math.random()*(BattleModel.height/2-3*player.getSizeY()))+BattleModel.height/2+40;
		}
		
		unit.setX(fx);
		unit.setY(fy);
		units.add(unit);
	}
	
	public boolean canPlace(int x, int y, ArrayList<Unit> units, ArrayList<Item> items)
	{	
		int prox = 2;
		if(units.size()==0 && items.size()==0)
			return true;
		else
		{
			for(int i=0; i<units.size(); i++)
			{
				Unit u = units.get(i);
				if((x>=(u.getX()-u.getSizeX()-prox) && x<=u.getX()+u.getSizeX()+prox) && y>=(u.getY()-u.getSizeY()-prox) && y<=u.getY()+u.getSizeY()+prox)
				{
					return false;
				}
			}
			
			for(int i=0; i<items.size(); i++)
			{
				Item u = items.get(i);
				if((x>=(u.getX()-u.getSizeX()-prox) && x<=u.getX()+u.getSizeX()+prox) && y>=(u.getY()-u.getSizeY()-prox) && y<=u.getY()+u.getSizeY()+prox)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void addEnemy(ArrayList<Unit> enemies, ArrayList<Item> items, ArrayList<MovementController> mover, GameController controller, BattleModel field, int numEnemy)
	{
		Color enemyColor = Color.blue;//new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));//Color.blue;
		int sizeX = 0;
		int sizeY = 0;
		int maxWidth = 0;
		int maxHeight = 0;
		int fx = 0;
		int fy = 0;
		for(int i=0; i<numEnemy; i++)
		{
			unit = new BotPlayer(0, 0, WarBallsConfig.enemyHealth, enemyColor, WarBallsConfig.EnemyId+i+enemyTeamDeaths, WarBallsConfig.EnemyTeamId, this);
//			unit = new BotPlayer(0, 0, 100, 777+i, 9999, player.getTid(), this);
			sizeY = unit.getBody().getSizeY();
			sizeX = unit.getBody().getSizeX();
			maxWidth = BattleModel.width-sizeX;
			maxHeight = BattleModel.height/2-sizeY;
			fx = (int)(Math.random()*maxWidth);
			fy = (int)(Math.random()*maxHeight);
			
			while(!canPlace(fx, fy, enemies, items))
			{
				fx = (int)(Math.random()*maxWidth);
				fy = (int)(Math.random()*maxHeight);
			}
			
			unit.setX(fx);
			unit.setY(fy);
			enemies.add(unit);		
			mover.add(new MovementController(unit, controller, field));
			unit.setMovementController(mover.get(mover.size()-1));
		}
	}
	
	public void addFriendly(ArrayList<Unit> friendlies, ArrayList<Item> items, ArrayList<MovementController> mover, GameController controller, BattleModel field, int numFriendly)
	{
		//Color c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
		int sizeX = 0;
		int sizeY = 0;
		int maxWidth = 0;
		int maxHeight = 0;
		int fx = 0;
		int fy = 0;
		for(int i=0; i<numFriendly; i++)
		{
			unit = new BotPlayer(0, 0, WarBallsConfig.friendlyHealth, Color.lightGray, WarBallsConfig.FriendlyId+i+friendlyTeamDeaths,  WarBallsConfig.FriendlyTeamId,this);
//			unit = new BotPlayer(0, 0, 100, 111+i, player.getTid(), player.getTid(), this);
			sizeY = unit.getBody().getSizeY();
			sizeX = unit.getBody().getSizeX();
			maxWidth = BattleModel.width-sizeX;
			maxHeight = BattleModel.height/2-2*sizeY;
			fx = (int)(Math.random()*maxWidth);
			fy = (int)(Math.random()*maxHeight) +30+ BattleModel.height/2;
			
			while(!canPlace(fx, fy, friendlies, items))
			{
				fx = (int)(Math.random()*maxWidth);
				fy = (int)(Math.random()*maxHeight) +30+ BattleModel.height/2;
			}
			
			unit.setX(fx);
			unit.setY(fy);
			friendlies.add(unit);		
			mover.add(new MovementController(unit, controller, field));
			unit.setMovementController(mover.get(mover.size()-1));
		}

//		enemy = new Enemy(30*(max-i)+300,300, 100, 777+i, 9999, this);
//		enemy = new Enemy(30*i,500, 100, 777+i, 9999, this);
//		enemy = new Enemy(300,300, 100, 777+i, 9999, this);
	}
	
	public boolean canMoveTo(Item mover, int x, int y)
	{
		if(!mover.getBody().isCollidable())
			return true;
		
		for(int i=0; i<units.size(); i++)
		{	
			Unit u = units.get(i);
			if(Collider.circleBodyCollision(mover.getBody(), x, y, u.getBody()))
			{
				return false;
			}
		}		
	
		//Unit vs Item Collisions	
		for(int j=0; j<items.size(); j++)
		{
			Item it = items.get(j);
			if(Collider.squareBodyCollision(mover.getBody(), x, y, it.getBody()))
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean canMoveTo(Unit mover, int x, int y)
	{
		if(!mover.getBody().isCollidable())
			return true;
		
		int locX = mover.getX();
		int locY = mover.getY();
		int rx = mover.getBody().getSizeX();
		int ry = mover.getBody().getSizeY();
		int cx = x;
		int cy = y;
		
		
		if(cx==0)
			cx = mover.getBody().getCx();
		else
			cx += rx/2;
		if(cy==0)
			cy = mover.getBody().getCy();
		else
			cy += ry/2;
		
		
		//Unit vs Unit Collision
		for(int i=0; i<units.size(); i++)
		{	
			Unit u = units.get(i);
			int distanceToClosestUnit = 0;
			int distanceToCurrentUnit = (int) Collider.getDistance(mover.getCx(), mover.getCy(), u.getBody().getCx(), u.getBody().getCy());	
			
			//Closet Enemy Unit
			if(mover.getPid()!=u.getPid()  && mover.getTid()!=u.getTid())
			{
				if((mover.getClosestEnemyUnit()==null || !mover.getClosestEnemyUnit().isAlive()))
				{		
					mover.setClosestEnemyUnit(u);
				}
				else
				{
					Unit cu = mover.getClosestEnemyUnit();
					int distanceToClosestEnemyUnit = (int) Collider.getDistance(mover.getCx(), mover.getCy(), cu.getBody().getCx(), cu.getBody().getCy());
					
					if(distanceToCurrentUnit<distanceToClosestEnemyUnit)
					{
						mover.setClosestEnemyUnit(u);
					}
				}
			}
			
			//Closet Friendly Unit
			if(mover.getPid()!=u.getPid() && mover.getTid()==u.getTid())
			{
				if((mover.getClosestFriendlyUnit()==null || !mover.getClosestFriendlyUnit().isAlive()) )
				{		
					mover.setClosestFriendlyUnit(u);
				}
				else
				{
					Unit cu = mover.getClosestFriendlyUnit();
					int distanceToClosestFriendlyUnit = (int) Collider.getDistance(mover.getCx(), mover.getCy(), cu.getBody().getCx(), cu.getBody().getCy());
					
					if(distanceToCurrentUnit<distanceToClosestFriendlyUnit)
					{
						mover.setClosestFriendlyUnit(u);
					}
				}
			}
			
			//Closest Unit
			if(mover.getPid()!=u.getPid())
			{
				if(mover.getClosestUnit()==null || !mover.getClosestUnit().isAlive())
				{		
					mover.setClosestUnit(u);
				}
				else
				{
					Unit cu = mover.getClosestUnit();
					distanceToClosestUnit = (int) Collider.getDistance(mover.getCx(), mover.getCy(), cu.getBody().getCx(), cu.getBody().getCy());
					
					if(distanceToCurrentUnit<distanceToClosestUnit)
					{
						mover.setClosestUnit(u);
					}
				}
			}
			
			if(Collider.circleBodyCollision(mover.getBody(), x, y, u.getBody()))
			{
				if(mover.getTid() == u.getTid())
				{
					u.heal();
					u.heal();
				}
				mover.setNearUnit(true);
				return false;
			}
			
			mover.setNearUnit(false);
		}		
	
		//Unit vs Item Collisions	
//		int distanceTo
		for(int j=0; j<items.size(); j++)
		{
			Item it = items.get(j);
			if(!it.getBody().isCollidable())
				continue;
			
			int distanceToClosestItem = (int) Collider.getDistance(mover.getCx(), mover.getCy(), it.getBody().getCx(), it.getBody().getCy());
			if(mover.getClosestItem()==null)
			{		
				if(distanceToClosestItem<=mover.getSizeX() && (mover.getPid() == it.getPid() || mover.getTid()==it.getTid()))
				{
					mover.setClosestItem(it);
				}
			}
			else
			{
				Item cit = mover.getClosestItem();
				int distanceToCurrentClosestItem = (int) Collider.getDistance(mover.getCx(), mover.getCy(), cit.getBody().getCx(), cit.getBody().getCy());
				if(distanceToCurrentClosestItem>mover.getSizeX())
				{
					mover.setClosestItem(null);
				}
			}
			
			if(Collider.squareBodyCollision(mover.getBody(), x, y, it.getBody()))
			{
				mover.setNearItem(true);
				return false;
			}
			mover.setNearItem(false);
		}
	
		
		//Test to stay inside box with all edge cases
		if(locX<=0) // Too Far left
		{
			if(x>=0)
			{
				if(locY<=0)// To High
				{
					if(y>=0)
						return true;
					return false;
				}
				
				if(locY+ry>= BattleModel.height)// Too low
				{
					if(y+ry<=BattleModel.height)
					{
						
						return true;
					}
					return false;
				}
				return true;
			}
			return false;
		}
			
		if(locX+rx>= BattleModel.width)// Too far right
		{
			if(x+rx< BattleModel.width)
			{
				if(locY<=0)// To High
				{
					if(y>=0)
						return true;
					return false;
				}
				
				if(locY+ry>= BattleModel.height)// Too low
				{
					if(y+ry<=BattleModel.height)
						return true;
					return false;
				}
				
				return true;
			}
			return false;
		}	

		
		if(locY<=0)// To High
		{
			if(y>=0)
			{
				if(locX<=0) // Too Far left
				{
					if(x>=0)
						return true;
					return false;
				}
				
				if(locX+rx>= BattleModel.width)// Too far right
				{
					if(x+rx< BattleModel.width)
						return true;
					return false;
				}
				
				return true;
			}
			return false;
		}
		
		if(locY+ry>= BattleModel.height)// Too low
		{
			if(y+ry<=BattleModel.height)
			{
				if(locX<=0) // Too Far left
				{
					if(x>=0)
						return true;
					return false;
				}
				
				if(locX+rx>= BattleModel.width)// Too far right
				{
					if(x+rx< BattleModel.width)
						return true;
					return false;
				}
				
				return true;
			}
			return false;
		}
		
		
		return true;
	}
	
	public boolean moveTo(Unit mover, int x,  int y)
	{
		boolean unitMoved = false;
		if(canMoveTo(mover, x, y))
		{
			mover.setX(x);
			mover.setY(y);
			unitMoved = true;
		}
		return unitMoved;
	}

	public void addPermanentDisplayItem(DisplayItem displayItem)
	{
		 permanentDisplayItems.add(displayItem);
	}
	
	public void addHole(HoleMark hole)
	{
		holes.add(hole);
		blackHoles.add(new BlackHole(hole.getX(), hole.getY(), hole.getSize()));
	}
	
	public void addTemporaryDisplayItem(DisplayItem displayItem)
	{
		 temporaryDisplayItems.add(displayItem);
	}
	
	public void updateModel(float deltaTime)
	{	
		//Remove Dead projectile, items, and enemies and displayItems
		for(int i=0; i<temporaryDisplayItems.size(); i++)
		{
			DisplayItem d = temporaryDisplayItems.get(i);
			if(!d.isAlive())
			{
				if(DeathMark.class==d.getClass())
				{
					DeathMark temp = (DeathMark)d;
					permanentDisplayItems.add(new GraveMark(d.getCx(), d.getCy(), 20, temp.wasFriend()));				
				}
				
				temporaryDisplayItems.remove(i);
			}
			else
				d.updateTime(deltaTime);
		}
		
		
		for(int i=0; i<items.size(); i++)
		{
			Item t = items.get(i);
			if(!t.isAlive())
				items.remove(i);
			else
				t.updateTime(deltaTime);
		}
		
		for(int i=0; i<units.size(); i++)
		{
			Unit u = units.get(i);
			if(!u.isAlive())
			{
				if(u.getTid()==player.getTid())
				{
					friendlyTeamDeaths++;
					if(u.getPid()!=player.getPid())
						deadFriendlies++;
				}
				else
				{
					enemyTeamDeaths++;
					deadEnemies++;
				}
				
				temporaryDisplayItems.add(new DeathMark(u.getX(), u.getY(), u.getBody().getSizeX(), u.getTid()==player.getTid()));
				movementControllers.remove(u.getMovementController());
				
				units.remove(i);
			}
			else
			{
				boolean inHole = false;
				for(int j=0; j<holes.size(); j++)
				{
					HoleMark hm = holes.get(j);
//					if(Collider.circleBodyCollision(u.getBody(), u.getCx(), u.getCy(), hm.getBody()))
					if(Collider.holeBodyCollision(u.getBody(), hm.getBody()))
						inHole = true;
				}
				
//				if(u==player)
//					System.out.println(u.getPid()+" in hole?: "+u.getBody().isInHole());
				u.getBody().setInHole(inHole);
				u.updateTime(deltaTime);
			}
		}
			
		
		waveTimer+=deltaTime;
		if(waveTimer>=WarBallsConfig.waveTimer)
		{	
			waveTimer = 0;
			addEnemy(units, items, movementControllers, this, field, deadEnemies);
			addFriendly(units, items, movementControllers, this, field, deadFriendlies);
			deadFriendlies= 0;
			deadEnemies= 0;
		}
		
		//Test Collision
		updateProjectiles(deltaTime);
			
	}

	public void updateProjectiles(float deltaTime)
	{
		//Test collision for items and enemies vs projectiles
		Projectile p;
		for(int ip=0; ip<projectiles.size(); ip++)
		{
			int[] data;
			Item closestItemHit = null;
			double distanceToClosestItemHit = 1000000;
			double distanceToLastItemHit = 0;
			int itemCollisionPointX = 0;
			int itemCollisionPointY = 0;
			Unit closestUnitHit = null;
			double distanceToClosestUnitHit = 1000000;
			double distanceToLastUnitHit = 0;
			int unitCollisionPointX = 0;
			int unitCollisionPointY = 0;
			
			p = projectiles.get(ip);
			p.setCanDraw(true);
			if(!p.isAlive())
			{
				projectiles.remove(ip);
			}
			else
				p.updateTime(deltaTime);
		
			if(p.hasCollided())
				continue;
			
			/*************************************/
			Item t;
			for(int ii=0; ii<items.size(); ii++)
			{
				t = items.get(ii);
			
				if(t.isWeapon())
					continue;
				
				if(t.getTid() == p.getTid() && !WarBallsConfig.friendlyItemCollision)
					continue;
				/*******************************/
				data = Collider.projectileCollisionData(p, t.getBody());
				
				//Check if bullet belongs to same team item belongs to
				
				//Bullet origin is outside
				if(data[0]==1) 
				{
					distanceToLastItemHit = Collider.getDistance(p.getSx(), p.getSy(), data[1], data[2]);
					if(closestItemHit==null)
					{
						closestItemHit = t;
						distanceToClosestItemHit = distanceToLastItemHit;
						itemCollisionPointX = data[1];
						itemCollisionPointY = data[2];
					}
					if(!(closestItemHit==null))
					{	
						if(distanceToLastItemHit<distanceToClosestItemHit)
						{
							closestItemHit = t;
							distanceToClosestItemHit = distanceToLastItemHit;
							itemCollisionPointX = data[1];
							itemCollisionPointY = data[2];
						}
					}
					
					if(p.getCanPenetrate() && distanceToLastItemHit<=p.getMaxPenetrationDistance())
					{	
						temporaryDisplayItems.add(new BulletCollisionMark(itemCollisionPointX, itemCollisionPointY, p.getSx(), p.getSy(), p.damage(), Color.white));
						closestItemHit.hit(p.getDamage(), 0);
						/*************************************************************/
						if(WarBallsConfig.canPenetrateItems)
						{
							distanceToLastItemHit = 1000000;
							distanceToClosestItemHit = 100000;
							closestItemHit = null;
						}
					}
				}
//				if(data[0]==2) //Bullet origin is inside
//				{
//					if(p.getCanPenetrate())
//					{
//						closestItemHit = null;
//						t.hit(100, 0);
//					}
//				}
			}
			/**********************************************************************************************************************/
			Unit u;
			for(int iu=0; iu<units.size(); iu++)
			{
				u = units.get(iu);
				
				// projectiles ignore non collidables
				if(!u.getBody().isCollidable()) 
				{
					continue;
				}
				
				// projectiles ignore non team mates
				if(p.getOwner().getTid()==u.getTid() && !WarBallsConfig.friendlyUnitCollision)
					continue;
				
				// projectiles ignore units in holes
				if(u.getBody().isInHole() && !WarBallsConfig.holeUnitCollision)
				{
					//if(Math.random()<0.98) //Shots will miss, 2% - Hit
						continue;
				}
				/*******************************/
				
				data = Collider.projectileCollisionData(p, u.getBody());
				if(data[0]==1)
				{
					distanceToLastUnitHit = Collider.getDistance(p.getSx(), p.getSy(), data[1], data[2]);
					if(closestUnitHit==null)
					{
						closestUnitHit = u;
						distanceToClosestUnitHit = distanceToLastUnitHit;
						unitCollisionPointX = data[1];
						unitCollisionPointY = data[2];
					}
					if(!(closestUnitHit==null))
					{	
						if(distanceToLastUnitHit<distanceToClosestUnitHit)
						{
							closestUnitHit = u;
							distanceToClosestUnitHit = distanceToLastUnitHit;
							unitCollisionPointX = data[1];
							unitCollisionPointY = data[2];
						}
					}
					if(distanceToClosestUnitHit<distanceToClosestItemHit && p.getCanPenetrate() && distanceToLastUnitHit<=p.getMaxPenetrationDistance())
					{	
						//nothing
						//p.setCollisionPoint(itemCollisionPointX, itemCollisionPointY);
						temporaryDisplayItems.add(new BulletCollisionMark(unitCollisionPointX, unitCollisionPointY, p.getSx(), p.getSy(), p.damage(), Color.white));
						/*************************************************************/
						closestUnitHit.hit(p.getDamage(), p.getBleedDamage(), p);
						distanceToLastUnitHit = 1000000;
						distanceToClosestUnitHit = 1000000;
						closestUnitHit = null;
					}
				}
//				if(data[0]==2) //From within
//				{
//					closestUnitHit = null;
//					u.hit(100, 100, p);
//				}
			}
			
			if(distanceToClosestUnitHit<distanceToClosestItemHit)
			{
				if(closestUnitHit!=null)
				{
					p.setCollisionPoint(unitCollisionPointX, unitCollisionPointY);
					temporaryDisplayItems.add(new BulletCollisionMark(unitCollisionPointX, unitCollisionPointY, p.getSx(), p.getSy(), p.damage(), Color.red));
					
					if(closestUnitHit.getTid() == p.getTid() && !WarBallsConfig.friendlyUnitDamage)
						continue;
					/*************************************************************/
					boolean wasAlive = closestUnitHit.isAlive();
					closestUnitHit.hit(p.getDamage(), p.getBleedDamage(), p);
					// How to make bullet or bleed of previous hit be the owner of Level UP Ticket
					if(p.getTid()!=closestUnitHit.getTid())
					{
						//if((closestUnitHit.getHealth()>=0 || (closestUnitHit.getHealth()-p.getBleedDamage())<=0 && wasAlive )&& wasAlive)
						//if(closestUnitHit.getHealth()>0 && (closestUnitHit.getHealth()-p.getDamage())<=0)
						if(!closestUnitHit.isAlive() && wasAlive)
						{
							p.getOwner().levelUp();
						}
					}
				}
			}
			else 
			{	if(closestItemHit != null )
				{
					p.setCollisionPoint(itemCollisionPointX, itemCollisionPointY);
					temporaryDisplayItems.add(new BulletCollisionMark(itemCollisionPointX, itemCollisionPointY, p.getSx(), p.getSy(), p.damage(), Color.white));
					
					if(closestItemHit.getTid() == p.getTid() && !WarBallsConfig.friendlyItemDamage)
						continue;
					/*************************************************************/
					closestItemHit.hit(p.getDamage()/closestItemHit.getArmor(), 0);
				}
			}
		}
	}
	
	public Unit getPlayer()
	{
		return player;
	}
	
	public int enemyCount()
	{
		int enemyCount = 0;
		for(Unit i: units)
		{
			if(i.getTid()!=player.getTid())
				enemyCount++;
		}
		return enemyCount;
	}
	
	public void updatePlayer()
	{
		if(keys[6])
			player.heal();
		
		if(friendlyTeamDeaths==WarBallsConfig.winCount || enemyTeamDeaths==WarBallsConfig.winCount )//enemyCount()==0) //ReStart
		{
			friendlyTeamDeaths = 0;
			enemyTeamDeaths = 0;
			playerDeaths = 0;
			playerKills = 0;
			player = new Player((int)(Math.random()*(BattleModel.width-player.getSizeX())), (int)(Math.random()*(BattleModel.height/2-3*player.getSizeY()))+BattleModel.height/2+40, WarBallsConfig.friendlyHealth, WarBallsConfig.FriendlyId, WarBallsConfig.FriendlyTeamId, this);
			field = new BattleModel(player, this);
			renderer = new BattleView(field);
			keys = new boolean[numKey];
			getData();
		}
		
		if(!player.isAlive() && enemyCount()>0)
		{
			player.getBody().setCollidable(false);
			temporaryDisplayItems.add(new GhostMark(player.getX(), player.getY(), player.getBody().getSizeX(), player.getTid()==player.getTid()));
			
			if(deadTimer>=maxDeadTime)
			{
				playerDeaths++;
				deadTimer = 0;
				player = new Player((int)(Math.random()*(BattleModel.width-player.getSizeX())), (int)(Math.random()*(BattleModel.height/2-3*player.getSizeY()))+BattleModel.height/2+40, WarBallsConfig.friendlyHealth, WarBallsConfig.FriendlyId, WarBallsConfig.FriendlyTeamId, this);
				
				spawnUnit(units, items, player);
			}
		}
		
		int speed = player.getSpeed();
		updateUnit(player, speed, keys[0], keys[1], keys[2], keys[3],  keys[4], clickX, clickY);
	}
	
	public boolean diagnolUnitMovement(Unit mover, int speed, int initialDirection, int prevX, int prevY, boolean up, boolean down, boolean left, boolean right)
	{
//		System.out.println(initialDirection+" ");
		boolean unitMoved = false;
		int speedX = 0; 
		int speedY = 0;
		int locX = mover.getX();
		int locY = mover.getY();
		initialDirection = WarBallsConfig.diagnolMovementType;//1//2//3
		
		if(initialDirection==0)//Up or Down initially
		{	
			speedX = speed/2;
			speedY = speed;
		}
		else if(initialDirection==1)//Left or Right Initially
		{
			speedX = speed;
			speedY = speed/2;
		}
		else if(initialDirection==2)//Slower diagonal movement
		{
			speedX = 1; 
			speedY = 1;
		}
		else if(initialDirection==3)//Faster diagonal movement
		{
			speedX = speed; 
			speedY = speed;
		}
		
		if(up && !down && left && !right)
		{//up and left
			locX = locX-speedX;
			locY = locY-speedY;
		
			if(canMoveTo(mover, locX, 0))
			{
				mover.setX(locX);
				unitMoved = true;
			}
			if(canMoveTo(mover, 0, locY))
			{
				mover.setY(locY);
				unitMoved = true;
			}
		}
		if(up && !down && !left && right)
		{//up and right
			locX = locX+speedX;
			locY = locY-speedY;
			
			if(canMoveTo(mover, locX, 0))
			{	
				mover.setX(locX);
				unitMoved = true;
			}
			if(canMoveTo(mover, 0, locY))
			{
				mover.setY(locY);
				unitMoved = true;
			}
		}
		if(!up && down && left && !right)
		{//down and left
			locY = locY+speedY;			
			locX = locX-speedX;
			
			if(canMoveTo(mover, locX, 0))
			{
				mover.setX(locX);
				unitMoved = true;
			}
			if(canMoveTo(mover, 0, locY))
			{
				mover.setY(locY);
				unitMoved = true;
			}
		}
		if(!up && down && !left && right)
		{//down and right
			locY = locY+speedY;
			locX = locX+speedX;
			
			if(canMoveTo(mover, locX, 0))
			{
				mover.setX(locX);
				unitMoved = true;
			}
			if(canMoveTo(mover, 0, locY))
			{
				mover.setY(locY);
				unitMoved = true;
			}
		}
//		double range = Collider.getDistance(prevX, prevY, mover.getX(), mover.getY()); 
//		System.out.println("Range =="+range);
		return unitMoved;
	}
	
	public boolean updateUnit(Unit mover, int speed, boolean up, boolean down, boolean left, boolean right, boolean attack, int x, int y)
	{
		boolean unitMoved = false;
		int loc;
		int locX = mover.getX();
		int locY = mover.getY();
		
		if(up && !down && !left && !right) // up only
		{
			initialDirection = 0;
			loc = locY-speed;
			
			if(canMoveTo(mover, 0, loc))
			{
				mover.setY(loc);
				unitMoved = true;
			}
		}
		else if(!up && down && !left && !right)// down only
		{
			initialDirection = 0;
			loc = locY+speed;
			
			if(canMoveTo(mover, 0, loc))
			{
				mover.setY(loc);
				unitMoved = true;
			}
		}
		else if(!up && !down && left && !right)// left only
		{
			initialDirection = 1;
			loc = locX-speed;
			
			if(canMoveTo(mover, loc, 0))
			{
				mover.setX(loc);
				unitMoved = true;
			}
		}
		else if(!up && !down && !left && right)// right only
		{
			initialDirection = 1;
			loc = locX+speed;
			
			if(canMoveTo(mover, loc, 0))
			{
				mover.setX(loc);
				unitMoved = true;
			}
		}
		else if(up || down || left || right)
		{
			if(diagnolUnitMovement(mover, speed, initialDirection, locY, locY, up, down, left, right))
				unitMoved = true;
		}
		
		if(attack)
		{
			mover.attack(mover.getX()+mover.getBody().getSizeX()/2, mover.getY()+mover.getBody().getSizeY()/2, x, y);
		}
		
		return unitMoved;
	}

	public void updateBots(float deltaTime)
	{
		for(int i=0; i<movementControllers.size(); i++)
			movementControllers.get(i).updateMovement(deltaTime);
	}
	
	public void render(Graphics g, Graphics g2)
	{
		renderer.render(g, g2);
		renderHud(g);
//		renderer.timeLapse(g, tick);
	}
	
	public void renderHud(Graphics g)
	{
		int hudXe = 450;
		int hudYe = 70;
		int hudXs = 0;
		int hudYs = BattleModel.height-hudYe;
		
		//Hud Outline
		g.setColor(Color.green);
		g.drawRect(hudXs, hudYs, hudXe, hudYe);
		
//		//Hud Data
//		int px = player.getX();
//		int py = player.getY();
		
		g.setColor(Color.yellow);
		String data ="";
		g.drawString(" Wave: "+(WarBallsConfig.waveTimer-(int)waveTimer)+"'s", hudXs+80, hudYs+30);
		g.drawString("  Time: "+((int)totalTime)/60+":"+String.format("%02d", (int)totalTime%60), hudXs+180, hudYs+30);
		
		data = player.info();
		g.drawString(data, hudXs+10, hudYs+15);
		
		ArrayList<Item> itemInventory = player.getAllItems();
		ArrayList<Weapon> weaponInventory = player.getAllWeapons();
		
		data = "";
		int we =1;
		for(Weapon i: weaponInventory)
		{
			if(i.equals(player.getWeapon()))
			{
				data += "["+we+"]{{"+i.info()+"}}  ";
			}
			else
				data +="["+we+"]"+i.info()+" ";
			we++;
		}
		g.drawString(data, hudXs+10, hudYs+45);
		
		data = "";
		int it = 4;
		for(Item i: itemInventory)
		{
			if(i.equals(player.getItem()))
			{
				data += "["+it+"]{{"+i.info()+"}}  ";
			}
			else
				data += "["+it+"]"+i.info()+" ";
			it++;
		}
		g.drawString(data, hudXs+10, hudYs+60);
		
		g.drawString("  Q=Heal / E=Pickup / R=Reload / RightClick=Aim", hudXs+100, hudYs+60);
		
		g.drawString("  H-Heal: "+player.getHealAmount()+"   Bleed: "+player.getBleed()+"/"+player.getBleedHealAmount()+"  KDR: "+(playerKills+"/"+playerDeaths), hudXs+120, hudYs+15);
		
		g.drawString("  Heal Time: "+String.format("%.2f", player.getHealTimer()), hudXs+330, hudYs+15);
		
		
		//Targeting Line
		if(keys[5]==true)
		{	
			g.setColor(Color.red);
			int size = 20;
			g.drawOval(crossHairX-size/2, crossHairY-size/2, size, size);
			g.drawLine(crossHairX, crossHairY-size/2, crossHairX, crossHairY+size/2);
			g.drawLine(crossHairX-size/2, crossHairY, crossHairX+size/2, crossHairY);
			
			g.setColor(Color.white);
			g.drawLine(player.getBody().getCx(), player.getBody().getCy(), crossHairX, crossHairY);
//			g.drawLine(player.getBody().getCx()+player.getBody().getSizeX()/2, player.getBody().getCy(), crossHairX, crossHairY);
//			g.drawLine(player.getBody().getCx()-player.getBody().getSizeX()/2, player.getBody().getCy(), crossHairX, crossHairY);
//			g.drawLine(player.getBody().getCx(), player.getBody().getCy()+player.getBody().getSizeY()/2, crossHairX, crossHairY);
//			g.drawLine(player.getBody().getCx(), player.getBody().getCy()-player.getBody().getSizeY()/2, crossHairX, crossHairY);
		
			int distance = (int)Collider.getDistance(player.getBody().getCx(), player.getBody().getCy(), crossHairX, crossHairY);
			g.setColor(Color.green);
			g.drawString(" Range: "+distance, hudXs+280, hudYs+30);
		}	
		
		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
		g.drawString("Blue: "+friendlyTeamDeaths+" /"+WarBallsConfig.winCount, BattleModel.width/2, 0+30);
		g.drawString("White: "+enemyTeamDeaths+" /"+WarBallsConfig.winCount, BattleModel.width/2, BattleModel.height-10);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15)); 
	}
}
