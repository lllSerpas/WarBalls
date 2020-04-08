package wb.model;

import wb.controller.Collider;
import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class MachineGun extends Weapon
{
	public MachineGun(int x, int y, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.tid = tid;
		
		damage = WarBallsConfig.machineGunDamage;
		bleedDamage = WarBallsConfig.machineGunBleedDamage;
		fireRate /= WarBallsConfig.machineGunFireRate; //shots per second //max 35 //25
		offSet = WarBallsConfig.machineGunOffSet; // in degrees
		reloadTime = WarBallsConfig.machineGunReloadTime;
		range = WarBallsConfig.machineGunRange;
		maxClip = WarBallsConfig.machineGunMaxClip;
		
		ammo = maxClip;
		deltaTime = 0;
	}
	
	public void useWeapon(int sx, int sy, int ex, int ey)
	{
		if((canUse(deltaTime, fireRate, ammo)))
		{
			ammo--;
			int maxRange = (int)(Math.random()*(75))+range;
			int[] points = Collider.getEndPoints(sx, sy, ex, ey, maxRange, offSet);
			Projectile bullet = null;
			switch(WarBallsConfig.machineGunBulletType)
			{
				case 0:
					bullet = new Bullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.machineGunTracerGhost, pid, tid, owner, WarBallsConfig.machineGunTracerCount);
				break;
				case 1:
					bullet = new LaserBullet(sx, sy, points[0], points[1], damage, bleedDamage, WarBallsConfig.machineGunTracerGhost, pid, tid, owner, WarBallsConfig.machineGunTracerCount);
				break;
			}
			controller.addProjectile(bullet);
			deltaTime = 0;
		}	
	}

	public String info()
	{
		return "M-Gun: "+ammo;
	}
}
