package wb.model;

import wb.controller.GameController;
import wb.main.WarBallsConfig;

public class GrenadeLauncher extends Weapon //Should be item
{
	public GrenadeLauncher(int x, int y, int pid, int tid, GameController controller, Unit owner)
	{
		this.controller = controller;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.pid = pid;
		this.tid = tid;
		this.damage = 0; //Shoots Grenades Only
		this.fireRate /= WarBallsConfig.grenadeLauncherFireRate; //per second 
		this.offSet = 0; // in degrees
		this.range = WarBallsConfig.grenadeLauncherRange;
		this.maxClip = WarBallsConfig.grenadeLauncherMaxClip;
		this.ammo = maxClip;
		reloadTime = WarBallsConfig.grenadeLauncherMaxClip;
	}
	
	public void useWeapon(int sx, int sy, int ex, int ey)
	{
		if(canUse(deltaTime, fireRate, ammo))
		{
			ammo--;

			GrenadeItem grenade = new GrenadeItem(sx, sy, ex, ey, pid, tid, controller, owner);
			controller.addItem(grenade);
			
			deltaTime = 0;
		}	
	}

	public String info()
	{
		return "Grenades: "+ammo;
	}

}
