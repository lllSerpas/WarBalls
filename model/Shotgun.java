package wb.model;

import wb.controller.Collider;
import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class Shotgun extends Weapon
{
	public Shotgun(int x, int y, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.tid = tid;
		
		damage = WarBallsConfig.shotGunDamage;
		bleedDamage = WarBallsConfig.shotGunBleedDamage;
		fireRate /= WarBallsConfig.shotGunFireRate; //shots per second //max 35 //25
		offSet = WarBallsConfig.shotGunOffSet; // in degrees
		reloadTime = WarBallsConfig.shotGunReloadTime;
		range = WarBallsConfig.shotGunRange;
		maxClip = WarBallsConfig.shotGunMaxClip;
		
		ammo = maxClip;
		deltaTime = 0;
	}
	
	public void useWeapon(int sx, int sy, int ex, int ey)
	{
		if((canUse(deltaTime, fireRate, ammo)))
		{
			ammo--;
			
			int pellets = 10;
			for(int i=0; i<=pellets; i++)
			{
				int maxRange = (int)(Math.random()*(50))+range;
				int[] points = Collider.getEndPoints(sx, sy, ex, ey, maxRange, offSet);
				Projectile bullet = null;
				switch(WarBallsConfig.shotGunBulletType)
				{
					case 0:
						bullet = new Bullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.shotGunTracerGhost, pid, tid, owner, WarBallsConfig.shotGunTracerCount);
					break;
					case 1:
						bullet = new LaserBullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.shotGunTracerGhost, pid, tid, owner, WarBallsConfig.shotGunTracerCount);
					break;
				}
				controller.addProjectile(bullet);
			}

			deltaTime = 0;
		}	
	}

	public String info()
	{
		return "Shotgun: "+ammo;
	}
}
