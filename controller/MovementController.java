package wb.controller;

import java.util.ArrayList;

import wb.main.WarBallsConfig;
import wb.model.BattleModel;
import wb.model.Unit;

public class MovementController
{
	GameController controller;
	BattleModel field;
	protected boolean up,down,left,right, canAttack;
	protected boolean[] moves = 
			{true, false, false,false, 	//0 up	
			false, true, false, false,	//1	down
			false, false, true, false,	//2	left	
			false, false, false, true,	//3	right
			true, false, true, false,	//4	up, left
			true, false, false, true,	//5	up, right
			false, true, true, false,	//6	down, left
			false, true, false, true,	//7	down, right
			false, false, false, false,	//8	away from player
			true, true, true, true};	//9	towards player

	protected int choice;
	protected Unit thisUnit, victim;
	protected double attackTime;
	protected double timeLastAttack;
	protected double movementTime;
	protected double timeLastMove;
	protected double attackDuration;
	protected double moveDuration;
	protected double maxHealTime;
	protected double timeLastHeal;
	protected double timeAttacking;
	protected double timeSinceAttacked;
	protected boolean isStuck;
	protected boolean noEnemies;
	protected double stuckTimer;
	protected boolean to;
	protected boolean from;
	protected ArrayList<Unit> units;
	protected ArrayList<Unit> enemies = new ArrayList<Unit>();
	
	public Unit getMovementUnit()
	{
		return this.thisUnit;
	}
	
	public MovementController(Unit u, GameController controller, BattleModel field)
	{
		this.controller = controller;
		this.field = field;
		units = field.getUnits();	
		thisUnit = u;
		victim = controller.getPlayer();
		chooseRandomVictim();
		
		movementTime = 	 (Math.random()*1)+0.50; //Time to switch direction
		moveDuration =	 (Math.random()*2)+0.75; //How long to continue in chosen direction
		attackTime = 	 (Math.random()*2)+0.10; //Time until Can Attack
		attackDuration = (Math.random()*1)+0.30; //How long to continue attack
		maxHealTime = 0;//(Math.random()*0.75);
	}

	public void chooseNewMovement(int range)
	{
		if(noEnemies)
			range = 8;
		
		choice = (int)(Math.random()*range)*4;// *4
		up = moves[choice];
		down = moves[choice+1];
		left = moves[choice+2];
		right = moves[choice+3];
		timeLastMove = 0;
//		System.out.println("Choice: "+choice/4);
	}
	
	public void newMovement(int choice)
	{
		choice *= 4;
		up = moves[choice];
		down = moves[choice+1];
		left = moves[choice+2];
		right = moves[choice+3];
		timeLastMove = 0;
//		System.out.println("Choice: "+choice/4);
	}
	
	public void getEnemies()
	{
		units = field.getUnits();	
		enemies = new ArrayList<Unit>();
		for(int i=0; i< units.size(); i++)
		{
			Unit u = units.get(i);
			if(u.getTid()!=thisUnit.getTid())
				enemies.add(u);
		}	
		if(enemies.size()==0)
			noEnemies=true;
		else
			noEnemies=false;
	}
	
	public void chooseRandomVictim()
	{
		getEnemies();	
		
		if(!noEnemies)
		{	
			int vic = (int)(Math.random()*enemies.size());
			
	//		System.out.println(units.size());
			while(!enemies.get(vic).isAlive())
			{
				vic = (int)(Math.random()*enemies.size());
			}
			victim = enemies.get(vic);
		}
	}
	
	public void chooseClosestVictim()
	{
		if(thisUnit.getClosestEnemyUnit()!=null && thisUnit.getClosestEnemyUnit().isAlive())
			victim = thisUnit.getClosestEnemyUnit();
	}
		
	public void chooseHighestLevelVictim()
	{
		int highestLevel = 0;
		getEnemies();
		for(int i=0; i<enemies.size(); i++)
		{
			if(enemies.get(i).getLevel()>highestLevel)
				victim = enemies.get(i);
		}
	}
	
	public void updateMovement(float deltaTime)
	{	timeLastMove += deltaTime;
		timeLastAttack += deltaTime;
		timeLastHeal +=deltaTime;
	
		
		
		if(thisUnit.getWeapon()!=null && thisUnit.getWeapon().getIsEmpty())
			thisUnit.getWeapon().reload();
		
		if(timeLastHeal>=maxHealTime)
		{
			thisUnit.heal();
			timeLastHeal = 0;
		}
		
		if(timeLastMove>=movementTime)
		{
			canAttack = true;
			victim = thisUnit.getClosestEnemyUnit();
			
			chooseNewMovement(10);
			if(Math.random()<WarBallsConfig.aggresion)
			{	
				chooseHighestLevelVictim();
			}
		}
	
		if(choice== 32)
		{
////			System.out.println("Move away from player");
//			int[] ep = {thisUnit.getX(), thisUnit.getY()};
//			if(victim!=null)
//				ep = Collider.getEndPoints(thisUnit.getX(), thisUnit.getY(), victim.getX(), victim.getY(), -thisUnit.getSpeed(), 0);
//			thisUnit.setWeapon(0);
//			from = true;
//			if(victim!=null)
//			if(!controller.moveTo(thisUnit, ep[0], ep[1])) //Did not move (stuck)
//			{
////				System.out.println("stuck! while running");
//				isStuck = true;
//			}
			
			//System.out.println("Move towards player");
			int[] ep = {thisUnit.getX(), thisUnit.getY()};
			if(victim!=null)
				ep = Collider.getEndPoints(thisUnit.getX(), thisUnit.getY(), victim.getX(), victim.getY(), thisUnit.getSpeed(), 0);
			thisUnit.setWeapon(1);
			to = true;
			if(!controller.moveTo(thisUnit, ep[0], ep[1])) //Did not move (stuck)
			{
//				System.out.println("stuck! while attacking");
				isStuck = true;
			}
			
		}
		else if(choice == 36)
		{
//			System.out.println("Move towards player");
			int[] ep = {thisUnit.getX(), thisUnit.getY()};
			if(victim!=null)
				ep = Collider.getEndPoints(thisUnit.getX(), thisUnit.getY(), victim.getX(), victim.getY(), thisUnit.getSpeed(), 0);
			thisUnit.setWeapon(1);
			to = true;
			if(!controller.moveTo(thisUnit, ep[0], ep[1])) //Did not move (stuck)
			{
//				System.out.println("stuck! while attacking");
				isStuck = true;
			}
		}
		else
		{
			from = false;
			to = false;
		}
		
		if(timeLastAttack>=attackTime)
		{
			canAttack = true;
			timeAttacking+=deltaTime;
			if(timeAttacking>=attackDuration)
			{
				timeAttacking = 0;
				timeLastAttack = 0;
				canAttack = false;
			}
		}
//		else
//			canAttack = false;
		
	
		if(!to && !from)//Actually attack
		{	
			if(victim!=null && victim.isAlive())
			if(!to && !from && !controller.updateUnit(thisUnit, thisUnit.getSpeed(), up, down, left, right, canAttack, victim.getCx(), victim.getCy()))
			{
//				System.out.println("stuck! during move");
				isStuck = true;
				chooseNewMovement(10);
			}
			else if(!victim.isAlive()) // Keep Same Victim until Dead or?
			{
				
				chooseRandomVictim();
			}
		}
		

		//Stuck Logic
		if(isStuck && thisUnit.isNearItem()) //Stuck at Enemy Item
		{	
			chooseNewMovement(8);
			if(thisUnit.getAllWeapons().size()>2)
				thisUnit.setWeapon(2);
			canAttack = true;
			victim = thisUnit.getClosestEnemyUnit();
			controller.updateUnit(thisUnit, thisUnit.getSpeed(), up, down, left, right, canAttack, victim.getCx(), victim.getCy());
			
//			thisUnit.setWeapon(0);
			isStuck = false;
		}
		//Stuck at Enemy
		if(isStuck && thisUnit.isNearUnit() && thisUnit.getClosestUnit() == (thisUnit.getClosestEnemyUnit()))
		{
			chooseNewMovement(8);
			thisUnit.setWeapon(0);
			canAttack = true;
			victim = thisUnit.getClosestEnemyUnit();
			controller.updateUnit(thisUnit, thisUnit.getSpeed(), up, down, left, right, canAttack, victim.getCx(), victim.getCy());
			isStuck = false;
		}
		 //Stuck near friendly
		if(isStuck && thisUnit.isNearUnit() && thisUnit.getClosestUnit() == (thisUnit.getClosestFriendlyUnit()))
		{
			chooseNewMovement(8);
			thisUnit.heal();
			thisUnit.getClosestFriendlyUnit().heal();
			
			victim = thisUnit.getClosestFriendlyUnit().getAttacker();
			isStuck = false;
		}
		//Stuck at edge of screen
		if(isStuck && !thisUnit.isNearItem() && !thisUnit.isNearUnit()) 
		{
			chooseNewMovement(10);
			isStuck = false;
		}
		
		
		if(thisUnit.hasBeenAttacked())
		{
			timeSinceAttacked+=deltaTime;
			//thisUnit.setWeapon(0);
			thisUnit.setItem(0);
			canAttack = true;
			
			if(thisUnit.getItem().getCount()<=0)
			{
				thisUnit.setWeapon(0);
			}
			
			victim = thisUnit.getAttacker();
//			if(victim!=null)
//			controller.updateUnit(thisUnit, thisUnit.getSpeed(), up, down, left, right, canAttack, victim.getCx(), victim.getCy());
//			canAttack = false;
			//chooseNewMovement(8);	
			//timeLastMove += deltaTime;
			//thisUnit.setHasBeenAttack(false);
		}
	}
}
