package wb.model;

import java.util.ArrayList;

import wb.controller.GameController;
import wb.controller.MovementController;
import wb.main.WarBallsConfig;

public class BattleModel
{
	public static final int width = WarBallsConfig.width;
	public static final int height = WarBallsConfig.height;
	
	private ArrayList<Unit> units = new ArrayList<Unit>();
	private ArrayList<Projectile>  projectiles =  new ArrayList<Projectile>();
	private ArrayList<Item>  items =  new ArrayList<Item>();
	private ArrayList<DisplayItem>  temporaryDisplayItems =  new ArrayList<DisplayItem>();
	private ArrayList<DisplayItem>  permanentDisplayItems =  new ArrayList<DisplayItem>();
	private ArrayList<HoleMark>  holes =  new ArrayList<HoleMark>();
	private ArrayList<BlackHole>  blackHoles =  new ArrayList<BlackHole>();
	private ArrayList<MovementController> movementControllers = new ArrayList<MovementController>();
	
	public BattleModel(Unit player, GameController controller)
	{
		if(WarBallsConfig.AddWalls)
			controller.addSandbagBarrier(items);
		if(WarBallsConfig.AddBricks)
			controller.addSandbag(items, units, WarBallsConfig.sandBagCount);
		
		controller.addEnemy(units, items, movementControllers, controller, this, WarBallsConfig.enemyTeamCount);//30);
		controller.addFriendly(units, items, movementControllers, controller, this, WarBallsConfig.friendlyTeamCount-1);
		
		controller.spawnUnit(units, items, player);
		
	}	
	
	public ArrayList<Unit> getUnits()
	{
		return units;
	}
	
	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}
	
	public ArrayList<Item> getItems()
	{
		return items;
	}
	
	public ArrayList<HoleMark> getHoles()
	{
		return holes;
	}
	
	public ArrayList<BlackHole> getBlackHoles()
	{
		return blackHoles;
	}
	
	public ArrayList<DisplayItem> getTemporaryDisplayItems()
	{
		return temporaryDisplayItems;
	}
	
	public ArrayList<DisplayItem> getPermanentDisplayItems()
	{
		return permanentDisplayItems;
	}
	
	public ArrayList<MovementController> getMovementControllers()
	{
		return movementControllers;
	}
	
	public Unit getPlayer()
	{
		if(units.size()>0)
			return units.get(0);
		else 
			return null;
	}
	
	public BattleModel getModel()
	{
		return this;
	}
}
