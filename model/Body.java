package wb.model;

public abstract class Body
{
	protected Unit owner;
	protected int x;
	protected int y;
	protected int cx;
	protected int cy;
	protected int sizeX;
	protected int sizeY;
	protected int pid;
	protected int tid;
	protected boolean isCircle;
	protected boolean isCollidable;
	protected boolean isInHole;
	
	public abstract String info();
	
	public Unit getOwner()
	{
		return owner;
	}
	
	public void setInHole(boolean inHole)
	{
		isInHole = inHole;
	}
	
	public boolean isInHole()
	{
		return isInHole;
	}
	
	public void setCollidable(boolean canCollide)
	{
		isCollidable = canCollide;
	}
	
	public boolean isCollidable()
	{
		return isCollidable;
	}
	
	public boolean isCircle()
	{
		return isCircle;
	}
	
	public int getPid()
	{
		return pid;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setX(int x)
	{
		this.x = x;
		cx = x+sizeX/2;
	}
	
	public void setY(int y)
	{
		this.y = y;
		cy = y+sizeY/2;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setCx(int x)
	{
		cx = x;
	}

	public void setCy(int y)
	{
		cy = y;
	}
	
	public int getCy()
	{
		return cy;
	}
	
	public int getCx()
	{
		return cx;
	}

	public int getSizeX()
	{
		return sizeX;
	}

	public int getSizeY()
	{
		return sizeY;
	}
}
