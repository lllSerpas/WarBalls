package wb.model;

import wb.controller.Collider;
import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class Rifle extends Weapon
{
	public Rifle(int x, int y, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.tid = tid;
		
		damage = WarBallsConfig.rifleGunDamage;
		bleedDamage = WarBallsConfig.rifleGunBleedDamage;
		fireRate /= WarBallsConfig.rifleGunFireRate; //shots per second //max 35 //25
		offSet = WarBallsConfig.rifleGunOffSet; // in degrees
		reloadTime = WarBallsConfig.rifleGunReloadTime;
		range = WarBallsConfig.rifleGunRange;
		maxClip = WarBallsConfig.rifleGunMaxClip;
		
		ammo = maxClip;
		deltaTime = 0;
		
//		damage = 50;
	}
	
	public void useWeapon(int sx, int sy, int ex, int ey)
	{
		if((canUse(deltaTime, fireRate, ammo)))
		{
			ammo--;
			int maxRange = (int)(Math.random()*(75))+range;
			int[] points = Collider.getEndPoints(sx, sy, ex, ey, maxRange, offSet);
			Projectile bullet = null;
			switch(WarBallsConfig.rifleGunBulletType)
			{
				case 0:
					bullet = new Bullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.rifleGunTracerGhost, pid, tid, owner, WarBallsConfig.rifleGunTracerCount);
				break;
				case 1:
					bullet = new LaserBullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.rifleGunTracerGhost, pid, tid, owner, WarBallsConfig.rifleGunTracerCount);
				break;
			}
			controller.addProjectile(bullet);
			deltaTime = 0;
		}	
	}

	public String info()
	{
		return "Rifle: "+ammo;
	}
}
