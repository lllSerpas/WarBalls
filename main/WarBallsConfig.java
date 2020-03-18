package wb.main;

public class WarBallsConfig
{
	//Window View Display Size
	public static int width =  800;//1900;//1590;//500;//1450;//1200;//1300;//1900;//1580;//1590;//800;//1900;//1900;//900
	public static int height = 800;//900;//800;//800;//975;//775;//800;////1000;//1000;//1000;//750
	public static int tick = 3;
	
	public static int friendlyTeamCount = 13;
	public static int enemyTeamCount = 13;
	public static int sandBagCount = 3;
	public static int waveTimer = 1;
	public static int speed = 2;
	public static int diagnolMovementType = 3;
	
	public static boolean friendlyUnitCollision = false;
	public static boolean friendlyUnitDamage = false;
	public static boolean friendlyItemCollision = false;
	public static boolean friendlyItemDamage = false;
	public static boolean holeUnitCollision = false;
	public static boolean canPenetrateItems = false;
	
	public static int winCount = 4321;
	public static double aggresion = -1;
	
	//Walls and Environment Health
	public static int sandBagsCarried = 3;
	public static int sandBagHealth = 50;
	public static int brickHealth = 100;
	public static int maxDeadTime = 3;
	
	public static boolean AddBricks = false;
	public static boolean AddWalls = true;
	
	//Health and Bleed Options
	public static int defaultHealthHealAmount = 1;
	public static int increaseHealthHealAmount = 1;//++
	public static int defaultBleedHealAmount = 1;
	public static int increaseBleedHealAmount = 1;//++
	
	public static float defaultHealTimer = 0.25f;
	public static float decreaseHealTimer = .005f;
	public static float minHealTimer = 0.10f;

	public static int maxHealthHeal = 2;
	public static int maxBleedHeal = 8;//4;
	public static int maxHealthIncrease = 10;

	public static int friendlyHealth = 15;
	public static int enemyHealth = 15;
	
	//Team Identifiers
	public static int FriendlyId = 777;
	public static int FriendlyTeamId = 321;
	public static int EnemyId = 333;
	public static int EnemyTeamId = 555;
	public static int WallId = 111;
	public static int WallTeamId = 111;
	
	//Display Options
	public static boolean displaySandBagBarriers = true;
	public static boolean displayEnemyUnits = true;
	public static boolean displayFriendlyUnits = true;
	public static boolean displayPlayerUnits = true;
		
	//Machine Gun Stats
	public static int machineGunDamage = 2;
	public static int machineGunBleedDamage = 3;
	public static double machineGunFireRate = 14;
	public static double machineGunOffSet = 6f;
	public static float machineGunReloadTime = 3.5f;
	public static int machineGunRange = 1500;
	public static int machineGunMaxClip = 600;
	public static float machineGunTracerGhost = 0.046f;
	public static int machineGunTracerCount = 5;
	public static int machineGunBulletType = 1; // 0 = bullet / 1 = laser
	
	//Rifle Gun Stats
	public static int rifleGunDamage = 5;
	public static int rifleGunBleedDamage = 2;
	public static double rifleGunFireRate = 7;
	public static double rifleGunOffSet = 3f;
	public static float rifleGunReloadTime = 1.5f;
	public static int rifleGunRange = 1750;
	public static int rifleGunMaxClip = 10;
	public static float rifleGunTracerGhost =  0.05f;
	public static int rifleGunTracerCount = 5;
	public static int rifleGunBulletType = 1; // 0 = bullet / 1 = laser
	
	//Sniper Rifle Gun Stats
	public static int sniperGunDamage = 10;
	public static int sniperGunBleedDamage = 5;
	public static double sniperGunFireRate = 100;
	public static double sniperGunOffSet = 0;
	public static float sniperGunReloadTime = 1.15f;
	public static int sniperGunRange = 2000;
	public static int sniperGunMaxClip = 1;
	public static float sniperGunTracerGhost =  0.065f;
	public static int sniperGunTracerCount = 1;
	public static int sniperGunBulletType = 1; // 0 = bullet / 1 = laser
	
	//ShotGun Stats
	public static int shotGunDamage = 4;
	public static int shotGunBleedDamage = 2;
	public static double shotGunFireRate = 1.5;
	public static double shotGunOffSet = 10;
	public static float shotGunReloadTime = 2f;
	public static int shotGunRange = 210;
	public static int shotGunMaxClip = 5;
	public static float shotGunTracerGhost =  0.04f;
	public static int shotGunTracerCount = 3;
	public static int shotGunBulletType = 1; // 0 = bullet / 1 = laser
	
	//GrenadeLauncher Stats
	public static double grenadeLauncherFireRate = 1;
	public static int grenadeLauncherMaxClip = 3;
	public static float grenadeLauncherReloadTime = 2.75f;
	public static int grenadeLauncherRange = 225;
	public static double grenadeLauncherOffSet = 10;
	public static double grenadeLauncherDamage = 10;
	
	//Grenade Item Stats
	
		
}
