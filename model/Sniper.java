package wb.model;

import wb.controller.Collider;
import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class Sniper extends Weapon
{
	public Sniper(int x, int y, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.tid = tid;
		
		damage = WarBallsConfig.sniperGunDamage;
		bleedDamage = WarBallsConfig.sniperGunBleedDamage;
		fireRate /= WarBallsConfig.sniperGunFireRate; //shots per second //max 35 //25
		offSet = WarBallsConfig.sniperGunOffSet; // in degrees
		reloadTime = WarBallsConfig.sniperGunReloadTime;
		range = WarBallsConfig.sniperGunRange;
		maxClip = WarBallsConfig.sniperGunMaxClip;
		
		ammo = maxClip;
		deltaTime = 0;
		
//		damage = 80;
	}
	
	public void useWeapon(int sx, int sy, int ex, int ey)
	{
		if((canUse(deltaTime, fireRate, ammo)))
		{
			ammo--;
			int maxRange = (int)(Math.random()*(75))+range;
			int[] points = Collider.getEndPoints(sx, sy, ex, ey, maxRange, offSet);
			Projectile bullet = null;
			switch(WarBallsConfig.sniperGunBulletType)
			{
				case 0:
					bullet = new Bullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.sniperGunTracerGhost, pid, tid, owner, WarBallsConfig.sniperGunTracerCount);
				break;
				case 1:
					bullet = new LaserBullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.sniperGunTracerGhost, pid, tid, owner, WarBallsConfig.sniperGunTracerCount);
				break;
			}
			controller.addProjectile(bullet);
			deltaTime = 0;
		}	
	}

	public String info()
	{
		return "Sniper: "+ammo;
	}
}
