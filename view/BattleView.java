package wb.view;

import java.awt.Color;
import java.awt.Graphics;

import wb.model.BattleModel;
import wb.model.BlackHole;
import wb.model.DisplayItem;
import wb.model.HoleMark;
import wb.model.Item;
import wb.model.Projectile;
import wb.model.Unit;

public class BattleView implements Renderer
{
	private BattleModel field;
	Color c;
	
	public BattleView(BattleModel field)
	{
		this.field = field;
//		genC();
	}
	
	public void render(Graphics g, Graphics g2)
	{
		//Closest to ground, lowest layer
		renderHoles(g);
		renderPermanentDisplayItems(g);
		renderItems(g);
		renderBotUnits(g);
		renderPlayer(g);
		renderBullets(g);
		renderTemporaryDisplayItems(g2);
		//Highest Elevation, highest layer
	}

	public void renderPlayer(Graphics g)
	{
		Unit player = field.getPlayer();
		g.setColor(player.getCurrentColor());
		player.drawBody(g);
	}
	
	public void renderHoles(Graphics g)
	{
		for(HoleMark hm: field.getHoles())
		{	
			hm.drawBody(g);
		}
		
		for(BlackHole hm: field.getBlackHoles())
		{	
			hm.drawBody(g);
		}
	}
	
	public void renderBullets(Graphics g)
	{
		for(Projectile b: field.getProjectiles())
		{
			if(b.canDraw())
			{	
				b.drawBody(g);
			}
		}	
	}
	
	public void renderBotUnits(Graphics g)
	{
		for(Unit u: field.getUnits())
		{
//			if(u.isAlive())
			{
				g.setColor(u.getCurrentColor());
				u.drawBody(g);
			}
		}
	}
	
	public void renderItems(Graphics g)
	{
		for(Item t: field.getItems())
		{
//			if(t.isAlive())
			{
				t.drawBody(g);
			}
		}
	}
	
	public void renderTemporaryDisplayItems(Graphics g)
	{
		for(DisplayItem dp: field.getTemporaryDisplayItems())
		{
//			if(dp.isAlive())
			{
				dp.drawBody(g);
			}
		}
	}
	
	public void renderPermanentDisplayItems(Graphics g)
	{
		for(DisplayItem dp: field.getPermanentDisplayItems())
		{
//			if(dp.isAlive())
			{
				dp.drawBody(g);
			}
		}
	}
	
	public Color genC()
	{
		int red = (int)(Math.random()*256);
		int green = (int)(Math.random()*256);
		int blue = (int)(Math.random()*256);
		c = new Color(red, green, blue);
		return c;
	}
	
	public void timeLapse(Graphics g, float tick)
	{
		int a1, b1, a2, b2;
		int x, y;
		int radius = 10;

		tick *= 3;
		tick%=1;
		for(Projectile b: field.getProjectiles())
		{
			if(b.isAlive())
			{
				a1 = b.getSx();
				a2 = b.getSy();
				b1 = b.getEx();
				b2 = b.getEy();
	
				x = (int)(a1 +(b1-a1)*tick);
				y = (int)(a2 +(b2-a2)*tick);
				
				g.setColor(Color.red);
				g.fillOval(x-(radius/2), y-(radius/2), radius, radius);
				
//				b.setAlive(false);
			}
		}
	}



}
